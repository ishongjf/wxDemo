package com.hongjf.wxpaydemo.exception;

import com.hongjf.wxpaydemo.enums.exception.GlobalExceptionEnum;

/**
 * @Author: Hongjf
 * @Date: 2020/1/3
 * @Time: 15:14
 * @Description:自定义全局异常类
 */
public class GlobalException extends RuntimeException {
    private Integer code;
    private String msg;

    public GlobalException(GlobalExceptionEnum e) {
        super(e.getMsg());
        this.code = e.getCode();
        this.msg = e.getMsg();
    }

    public GlobalException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public GlobalException(String message, Integer code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public GlobalException(String message, Throwable cause, Integer code, String msg) {
        super(message, cause);
        this.code = code;
        this.msg = msg;
    }

    public GlobalException(Throwable cause, Integer code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public GlobalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
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
