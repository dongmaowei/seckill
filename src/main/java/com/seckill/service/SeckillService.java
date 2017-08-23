package com.seckill.service;

import java.util.List;

import com.seckill.pojo.Seckill;
import com.seckill.service.dto.Exposer;
import com.seckill.service.dto.SeckillExecution;
import com.seckill.service.exception.RepeatKillException;
import com.seckill.service.exception.SeckillCloseException;
import com.seckill.service.exception.SeckillException;

/**
 * 业务接口：站在“使用者”角度设计接口
 * 三个方面：方法定义力度，参数，返回类型（）异常
 */
public interface SeckillService {
	/**
	 * 查询所有秒杀记录
	 */
	List<Seckill> getSeckillList();

	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getById(long seckillId);

	/**
	 * 输出所想秒杀的地址，否则输出系统时间，以及秒杀时间段
	 * @param seckillId
	 * @return
	 */
	Exposer exportSeckillUrl(long seckillId);

	/**
	 * 执行秒杀
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)throws SeckillCloseException,RepeatKillException,SeckillException;
}
