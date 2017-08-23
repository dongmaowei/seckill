package com.seckill.service.enums;

/**
 * 使用枚举类型表示数据字典
 */
public enum SeckillStateEnum {
    SUCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT(2,"秒杀重复"),
    REWRITE(3,"数据异常"),
    INNER_ERROR(-1,"系统异常");



    private int state;
    private String stateInfo;
    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
    public static SeckillStateEnum getState(int state){
        for(SeckillStateEnum value:values()){
            if (value.getState()==state) return value;
        }
        return null;
    }
}
