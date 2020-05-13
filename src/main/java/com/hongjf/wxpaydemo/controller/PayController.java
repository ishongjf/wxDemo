package com.hongjf.wxpaydemo.controller;

import com.hongjf.wxpaydemo.result.Result;
import com.hongjf.wxpaydemo.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: hongjf
 * @Date: 2020/5/13
 * @Time: 21:54
 * @Description:支付controller
 */
@RestController
@RequestMapping("/api/pay")
public class PayController {

    @Autowired
    private PayService payService;

    /**
     * 微信企业付款到个人
     *
     * @param money  付款金额 单位分
     * @param openId 付款用户微信openId
     * @param title  支付标题
     * @return
     */
    @GetMapping(value = "/wxPay")
    public Result wxPay(@RequestParam(value = "money") Long money,
                        @RequestParam(value = "openId") String openId,
                        @RequestParam(value = "title") String title) throws Exception {
        return payService.wxPay(money, openId, title);
    }

    @GetMapping(value = "/pushOrder")
    public Result pushOrder(@RequestParam(value = "money") Long money,
                            @RequestParam(value = "openId") String openId,
                            HttpServletRequest request) {
        return payService.pushOrder(money, openId, request);
    }

    /**
     * 微信回调接口 该接口不能有token校验和必传参数
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/payNotify")
    public String payNotify(HttpServletRequest request) {
        return payService.payNotify(request);
    }
}
