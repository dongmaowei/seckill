package com.seckill.dao;

import org.apache.ibatis.annotations.Param;

import com.seckill.pojo.SuccessKilled;

public interface SucessKilledDao {
	int insertSucessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
