package com.hongjf.wxpaydemo.service;

import cn.hutool.http.HttpUtil;
import com.hongjf.wxpaydemo.config.WxConfig;
import com.hongjf.wxpaydemo.enums.exception.GlobalExceptionEnum;
import com.hongjf.wxpaydemo.exception.GlobalException;
import com.hongjf.wxpaydemo.result.Result;
import com.hongjf.wxpaydemo.result.WxAuthorizationVo;
import com.hongjf.wxpaydemo.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: hongjf
 * @Date: 2020/5/13
 * @Time: 20:28
 * @Description:登录service
 */
@Slf4j
@Service
public class LoginService {

    @Autowired
    private WxConfig wxConfig;

    public Result wxLogin(String code) {
        String wxAuthorizationUrl = wxConfig.getAuthorizationUrl(code, wxConfig.getAppId());
        String wxAuthorization = HttpUtil.get(wxAuthorizationUrl);
        WxAuthorizationVo wxAuthorizationVo;
        try {
            wxAuthorizationVo = WxAuthorizationVo.getWxAuthorizationVo(wxAuthorization);
            if (ToolUtil.isEmpty(wxAuthorizationVo) || ToolUtil.isEmpty(wxAuthorizationVo.getOpenId())) {
                throw new GlobalException(GlobalExceptionEnum.WX_LOGIN_ERROR.getCode(), GlobalExceptionEnum.WX_LOGIN_ERROR.getMsg());
            }
        } catch (Exception e) {
            log.error(GlobalExceptionEnum.WX_LOGIN_ERROR.getMsg(), e);
            throw new GlobalException(GlobalExceptionEnum.WX_LOGIN_ERROR.getCode(), GlobalExceptionEnum.WX_LOGIN_ERROR.getMsg());
        }
        //以下请根据业务进行用户信息的操作
        return Result.ok();
    }
}
