package com.hongjf.wxpaydemo.controller;

import com.hongjf.wxpaydemo.result.Result;
import com.hongjf.wxpaydemo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hongjf
 * @Date: 2020/5/13
 * @Time: 20:26
 * @Description:微信登录controller
 */
@RestController
@RequestMapping("/api.login")
public class LoginContrller {

    @Autowired
    private LoginService loginService;

    @GetMapping(value = "/login")
    public Result login(@RequestParam(value = "code") String code) {
        return loginService.wxLogin(code);
    }
}
