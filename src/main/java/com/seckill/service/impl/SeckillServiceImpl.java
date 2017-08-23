package com.seckill.service.impl;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.SucessKilledDao;
import com.seckill.pojo.Seckill;
import com.seckill.pojo.SuccessKilled;
import com.seckill.service.SeckillService;
import com.seckill.service.dto.Exposer;
import com.seckill.service.dto.SeckillExecution;
import com.seckill.service.enums.SeckillStateEnum;
import com.seckill.service.exception.RepeatKillException;
import com.seckill.service.exception.SeckillCloseException;
import com.seckill.service.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SucessKilledDao sucessKilledDao;

    private final String salt = "shiuxiuhe8u928@!!##@#EC__CwCWWcW";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill=seckillDao.queryById(seckillId);
        if(seckill==null){
            return new Exposer(false,seckillId);
        }
        Date startTime=seckill.getStartTime();
        Date endTime=seckill.getEndTime();
        Date now = new Date();
        if (now.getTime()<startTime.getTime()||now.getTime()>endTime.getTime()){
            return  new Exposer(false,seckillId
                    ,now.getTime(),startTime.getTime(),endTime.getTime());
        }
        String md5 = getMd5(seckillId);
        return new Exposer(true,md5,seckillId);

    }

    /**
     * 获取md5
     * @param seckillId
     * @return
     */
    private String getMd5(long seckillId){
        String base = seckillId+"/"+salt;
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return  md5;
    }


    /**
     * 含有修改数据库的多条操作要加事物声明注解
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException {
        if(md5==null||!md5.equals(getMd5(seckillId))){
            throw new SeckillException("seckill data rewrite!");
        }
        Date now = new Date();
        try{
            int count = seckillDao.reduceNumber(seckillId,now);
            if(count==0){
                throw new SeckillCloseException("seckill is not available!");
            }
            count=sucessKilledDao.insertSucessKilled(seckillId,userPhone);
            if (count==0){
                throw new RepeatKillException("RepeatSeckill");
            }
            SuccessKilled successKilled=sucessKilledDao.queryByIdWithSeckill(seckillId,userPhone);
            System.out.println(new SeckillExecution(seckillId, SeckillStateEnum.SUCESS,successKilled));
            return  new SeckillExecution(seckillId, SeckillStateEnum.SUCESS,successKilled);
        }catch (SeckillCloseException e1){
            throw e1;
        }catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            /**
             * dao 层操作可能有异常
             * 编译期异常转化为运行异常
             */
            throw new SeckillException("inner error:" + e.getMessage());
        }
    }
}
