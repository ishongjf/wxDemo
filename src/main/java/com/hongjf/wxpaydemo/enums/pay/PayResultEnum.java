package com.hongjf.wxpaydemo.enums.pay;

/**
 * @Author: hongjf
 * @Date: 2020/2/13
 * @Time: 17:39
 * @Description:支付回调枚举
 */
public enum PayResultEnum {
    /**
     * 支付回调枚举
     */
    SUCCESS("SUCCESS", "成功"),

    FAIL("FAIL", "失败");

    private String code;

    private String msg;

    PayResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
