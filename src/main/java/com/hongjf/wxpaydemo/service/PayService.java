package com.hongjf.wxpaydemo.service;

import com.hongjf.wxpaydemo.config.WxConfig;
import com.hongjf.wxpaydemo.enums.exception.GlobalExceptionEnum;
import com.hongjf.wxpaydemo.enums.pay.PayResultEnum;
import com.hongjf.wxpaydemo.exception.GlobalException;
import com.hongjf.wxpaydemo.result.Result;
import com.hongjf.wxpaydemo.util.RequestUtil;
import com.hongjf.wxpaydemo.util.ToolUtil;
import com.hongjf.wxpaydemo.util.WxPayHttpUtil;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: hongjf
 * @Date: 2020/5/13
 * @Time: 21:55
 * @Description:支付service
 */
@Slf4j
@Service
public class PayService {

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private WxPayHttpUtil wxPayHttpUtil;

    public Result wxPay(Long money, String openId, String title) throws Exception {
        //设置请求参数
        Map<String, String> params = new HashMap<>(16);
        String currentTime = System.currentTimeMillis() + "";
        params.put("mch_appid", wxConfig.getAppId());
        params.put("mchid", wxConfig.getMchId());
        params.put("openid", openId);
        params.put("amount", money.intValue() + "");
        params.put("desc", title);
        params.put("spbill_create_ip", "47.97.254.63");
        params.put("check_name", "NO_CHECK");
        params.put("partner_trade_no", "gj" + System.currentTimeMillis());
        params.put("nonce_str", currentTime);
        params.put("sign", wxConfig.getSign(params, wxConfig.getMchKey()));
        String param = RequestUtil.toXml(params);
        String resultXml = wxPayHttpUtil.httpsRequest(wxConfig.getWxPayUrl(), param, wxConfig.getMchId(), wxConfig.getPayFileUrl());
        //支付结果
        Map<String, String> result = PaymentKit.xmlToMap(resultXml);
        if (ToolUtil.isEmpty(result.get("return_code")) || !PayResultEnum.SUCCESS.getCode().equals(result.get("return_code"))) {
            throw new GlobalException(GlobalExceptionEnum.PAY_ERROR.getCode(), result.get("err_code_des"));
        }
        if (ToolUtil.isEmpty(result.get("result_code")) || !PayResultEnum.SUCCESS.getCode().equals(result.get("result_code"))) {
            throw new GlobalException(GlobalExceptionEnum.PAY_ERROR.getCode(), result.get("err_code_des"));
        }
        //请根据业务需求保存支付信息
        //partner_trade_no:自己定义的业务支付编号
        //transaction_id:微信订单编号
        return Result.ok();
    }
}
