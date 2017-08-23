package com.seckill.service.impl;

import com.seckill.dao.SeckillDao;
import com.seckill.pojo.Seckill;
import com.seckill.service.SeckillService;
import com.seckill.service.dto.Exposer;
import com.seckill.service.dto.SeckillExecution;
import com.seckill.service.exception.RepeatKillException;
import com.seckill.service.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                       "classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1000L);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long seckillId = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        logger.info("exposer = {}",exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        long seckillId = 1000L;
        long userPhone = 18676628L;
        try{
            SeckillExecution seckillExecution= seckillService.executeSeckill(seckillId,userPhone,"8315407da9063280c642a1ee56f5d3e3");
            logger.info("seckillExecution ={} ",seckillExecution);
        }catch (RepeatKillException e1){
            logger.info(e1.getMessage());
        }catch(SeckillCloseException e2){
            logger.info(e2.getMessage());
        }

    }

}