package com.seckill.service.dto;

public class SeckillResult<T> {
    private boolean sucess;
    private T data;
    private String message;

    public SeckillResult(boolean sucess, String message) {
        this.sucess = sucess;
        this.message = message;
    }

    public SeckillResult(boolean sucess, T data) {
        this.sucess = sucess;
        this.data = data;
    }
}
