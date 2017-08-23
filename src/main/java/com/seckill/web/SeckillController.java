package com.seckill.web;

import com.seckill.pojo.Seckill;
import com.seckill.service.SeckillService;
import com.seckill.service.dto.Exposer;
import com.seckill.service.dto.SeckillExecution;
import com.seckill.service.dto.SeckillResult;
import com.seckill.service.enums.SeckillStateEnum;
import com.seckill.service.exception.RepeatKillException;
import com.seckill.service.exception.SeckillCloseException;
import com.seckill.service.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/seckill")
public class SeckillController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Seckill> list(Model model){
        List<Seckill> list=seckillService.getSeckillList();
        model.addAttribute("list",list);
        return list;
    }

    @RequestMapping(value ="/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId")Long seckillId, Model model){
        if(seckillId==null) return "redirct:/seckill/list";
        Seckill seckill=seckillService.getById(seckillId);
        if(seckill==null) return "/seckill/list";
        model.addAttribute("seckill",seckill);
        return  "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST)
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer=seckillService.exportSeckillUrl(seckillId);
            result=new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result=new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{userPhone}/{md5}/excuteSeckill",method = RequestMethod.POST)
    @ResponseBody
    public SeckillResult<SeckillExecution> excuteSeckill(@PathVariable("seckillId") Long seckillId,
                                                         @CookieValue(value = "userPhone",required = false) Long userPhone,
                                                         @PathVariable("md5") String md5){
        SeckillResult<SeckillExecution> result;
        try {
            SeckillExecution seckillExecution=seckillService.executeSeckill(seckillId,userPhone,md5);
            result=new SeckillResult<SeckillExecution>(true,seckillExecution);
        }catch (RepeatKillException e1){
            logger.error(e1.getMessage(),e1);
            SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStateEnum.REPEAT);
            result=new SeckillResult<SeckillExecution>(false,seckillExecution);
        }catch (SeckillCloseException e2){
            logger.error(e2.getMessage(),e2);
            SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStateEnum.END);
            result=new SeckillResult<SeckillExecution>(false,seckillExecution);
        }catch (SeckillException e3){
            logger.error(e3.getMessage(),e3);
            SeckillExecution seckillExecution=new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
            result=new SeckillResult<SeckillExecution>(false,seckillExecution);
        }
        return result;
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult(true,now);
    }
}
