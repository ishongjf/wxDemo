package com.hongjf.wxpaydemo.result;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * Copyright 2019 ZhiLiao, Inc. All rights reserved.
 *
 * @Author: Hongjf
 * @Date: 2020/1/9
 * @Time: 17:53
 * @Description:微信授权返回数据vo
 */
@Data
public class WxAuthorizationVo {
    /**
     * 接口调用凭证
     */
    private String accessToken;
    /**
     * 用户刷新 access_token
     */
    private String refreshToken;
    /**
     * 接口调用凭证超时时间，单位（秒）
     */
    private int expiresIn;
    /**
     * 授权用户唯一标识
     */
    private String openId;
    /**
     * 用户授权的作用域，使用逗号（,）分隔
     */
    private String scope;
    /**
     * 当且仅当该移动应用已获得该用户的 userinfo 授权时，才会出现该字段
     */
    private String unionid;

    public static WxAuthorizationVo getWxAuthorizationVo(String wxAuthorization) {
        WxAuthorizationVo wxAuthorizationVo = new WxAuthorizationVo();
        JSONObject jsonObject = JSONObject.parseObject(wxAuthorization);
        wxAuthorizationVo.setAccessToken(jsonObject.getString("session_key"));
        wxAuthorizationVo.setOpenId(jsonObject.getString("openid"));
        return wxAuthorizationVo;
    }
}
