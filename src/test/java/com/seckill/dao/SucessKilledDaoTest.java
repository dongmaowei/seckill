package com.seckill.dao;

import com.seckill.pojo.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/spring/spring-dao.xml")
public class SucessKilledDaoTest {
    @Resource
    private SucessKilledDao sucessKilledDao;
    @Test
    public void insertSucessKilled() throws Exception {
        int returncount=sucessKilledDao.insertSucessKilled(1000L,185520870215L);
        System.out.println(returncount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        SuccessKilled successKilled=sucessKilledDao.queryByIdWithSeckill(1000L,18662087215l);
        System.out.println(successKilled);
    }

}