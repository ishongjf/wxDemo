package com.hongjf.wxpaydemo.result;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

/**
 * @Author: Hongjf
 * @Date: 2020/1/2
 * @Time: 14:24
 * @Description:返回结果封装类
 */
public class Result<T> implements Serializable {

    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 响应业务状态
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private T data;

    public Result() {
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(T data) {
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
        this.data = data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Result build(Integer code, String msg, T data) {
        return new Result(code, msg, data);
    }

    public static Result ok(Object data) {
        return new Result(data);
    }

    public static Result ok() {
        return new Result(null);
    }

    public static Result fail() {
        return build(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMsg(), null);
    }

    public static Result failMsg(String msg) {
        return build(ResultCode.FAIL.getCode(), msg);
    }

    public static Result fail(Object obj) {
        return build(ResultCode.FAIL.getCode(), obj.toString(), null);
    }

    public static Result fail(int code, String msg) {
        return build(code, msg);
    }


    public static Result fail(int code, Object obj) {
        return build(code, obj.toString(), null);
    }

    public static Result build(Integer code, String msg) {
        return new Result(code, msg, null);
    }


    public enum ResultCode {
        /**
         * 成功
         */
        SUCCESS(200, "成功"),
        /**
         * 失败
         */
        FAIL(201, "失败");

        private Integer code;
        private String msg;

        ResultCode(Integer code, String msg) {
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

    @Override
    public String toString() {
        String result = "";
        try {
            result = MAPPER.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
