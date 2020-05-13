package com.hongjf.wxpaydemo.enums.exception;

/**
 *
 * @Author: Hongjf
 * @Date: 2020/1/2
 * @Time: 14:53
 * @Description:全局异常枚举类
 */
public enum GlobalExceptionEnum {
    /**
     * 异常编码信息
     */
    REQUEST_PARAM_ERROR(300, "请求参数异常"),

    LOGIN_ERROR(400, "登录信息异常"),

    SERVER_ERROR(500, "服务器运行异常"),

    NOT_SUPPORTED_ERROR(600, "请求方法错误"),

    DATA_INTEGRITY_ERROR(700, "请求参数中有字段超过数据长度限制"),

    WX_LOGIN_ERROR(800, "微信登录失败"),

    PAY_ERROR(1600, "支付异常"),

    ;


    private Integer code;

    private String msg;

    GlobalExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
