package com.seckill.dao;

import com.seckill.pojo.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/spring-dao.xml"})
public class SeckillDaoTest {
    @Resource
    private SeckillDao seckillDao;
    @Test
    public void reduceNumber() throws Exception {
        int number=seckillDao.reduceNumber(1000L, new Date(116,0,1));
        System.out.println(number);
    }

    @Test
    public void queryById() throws Exception {
        Seckill seckill=seckillDao.queryById(1000L);
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> list= seckillDao.queryAll(0,4);
        for(Seckill seckill:list) System.out.println(seckill);
    }

}