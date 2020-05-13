package com.hongjf.wxpaydemo.service;

import cn.hutool.http.HttpRequest;
import com.hongjf.wxpaydemo.config.WxConfig;
import com.hongjf.wxpaydemo.enums.exception.GlobalExceptionEnum;
import com.hongjf.wxpaydemo.enums.pay.PayResultEnum;
import com.hongjf.wxpaydemo.exception.GlobalException;
import com.hongjf.wxpaydemo.result.Result;
import com.hongjf.wxpaydemo.util.RequestUtil;
import com.hongjf.wxpaydemo.util.ToolUtil;
import com.hongjf.wxpaydemo.util.WxPayHttpUtil;
import com.hongjf.wxpaydemo.vo.PayRequestVo;
import com.jfinal.weixin.sdk.kit.PaymentKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    public Result pushOrder(Long money, String openId, HttpServletRequest request) {
        String outTradeNo = "hjf" + System.currentTimeMillis();
        //1.微信订单
        Map<String, String> params = new HashMap<>(16);
        String nonceString = System.currentTimeMillis() / 1000 + "";

        params.put("appid", wxConfig.getAppId());
        params.put("openid", openId);
        params.put("mch_id", wxConfig.getMchId());
        params.put("nonce_str", nonceString);
        params.put("body", "工单支付");
        params.put("attach", "custom json");
        params.put("out_trade_no", outTradeNo);
        params.put("total_fee", money + "");
        params.put("spbill_create_ip", RequestUtil.getIp(request));
        params.put("notify_url", wxConfig.getNotifyUrl());
        params.put("trade_type", "JSAPI");
        //2.签名
        String sign = wxConfig.getSign(params, wxConfig.getMchKey());
        params.put("sign", sign);

        String param = RequestUtil.toXml(params);
        String xmlResult = HttpRequest.post(wxConfig.getPayUrl()).body(param).execute().body();

        //3.调用支付接口
        log.info("-----------------------支付调用接口返回:" + xmlResult);
        //4.检查结果
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        log.info("------------------------支付返回结果:" + result.toString());
        String returnCode = result.get("return_code");
        String returnMsg = result.get("return_msg");
        if (ToolUtil.isEmpty(returnCode) || !(PayResultEnum.SUCCESS.getCode().equals(returnCode))) {
            return null;
        }
        String resultCode = result.get("result_code");
        String errCodeDes = result.get("err_code_des");
        if (ToolUtil.isEmpty(resultCode) || !(PayResultEnum.SUCCESS.getCode().equals(resultCode))) {
            return null;
        }

        //请根据业务需求保存支付信息

        //生成返回参数签名
        String prepayId = result.get("prepay_id");
        Map<String, String> packageParams = new HashMap<>(16);
        String timeStamp = System.currentTimeMillis() / 1000 + "";
        String nonceStr1 = System.currentTimeMillis() + "";
        packageParams.put("appId", wxConfig.getAppId());
        packageParams.put("package", "prepay_id=" + prepayId);
        packageParams.put("nonceStr", nonceStr1);
        packageParams.put("timeStamp", timeStamp);
        packageParams.put("signType", "MD5");
        String packageSign = wxConfig.getSign(packageParams, wxConfig.getMchKey());
        //设置返回参数
        PayRequestVo payRequestVo = new PayRequestVo();
        payRequestVo.setAppId(wxConfig.getAppId());
        payRequestVo.setNonceStr(nonceStr1);
        payRequestVo.setPrepayId(prepayId);
        payRequestVo.setSignType("MD5");
        payRequestVo.setSign(packageSign);
        payRequestVo.setMchId(wxConfig.getMchId());
        payRequestVo.setTimeStamp(timeStamp);
        payRequestVo.setPayPackage("prepay_id=" + prepayId);
        payRequestVo.setTradeType("JSAPI");
        return Result.ok(payRequestVo);
    }

    public String payNotify(HttpServletRequest request) {
        String xmlMsg = RequestUtil.readData(request);
        Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
        log.info("------------------------------------支付回调返回参数:" + params);
        String appId = params.get("appid");
        // 商户号
        String mchId = params.get("mch_id");
        String resultCode = params.get("result_code");
        String openId = params.get("openid");
        // 交易类型
        String tradeType = params.get("trade_type");
        // 付款银行
        String bankType = params.get("bank_type");
        // 总金额
        String totalFee = params.get("total_fee");
        // 现金支付金额
        String cashFee = params.get("cash_fee");
        // 微信支付订单号
        String transactionId = params.get("transaction_id");
        // 商户订单号
        String outTradeNo = params.get("out_trade_no");
        // 支付完成时间，格式为yyyyMMddHHmmss
        String timeEnd = params.get("time_end");

        ///////////////////////////// 以下是附加参数///////////////////////////////////

        String attach = params.get("attach");
        String feeType = params.get("fee_type");
        String isSubscribe = params.get("is_subscribe");
        String errCode = params.get("err_code");
        String errCodeDes = params.get("err_code_des");

        Map<String, String> result = new HashMap<>(16);

        //此处判断数据库的支付信息是否存在
        /*PayDetailEntity payDetailEntity = payDetailManager.getPayDetailByTradeNo(outTradeNo);
        if (ToolUtil.isEmpty(payDetailEntity)) {
            log.error("------------------------------------支付失败原因：payDetailEntity为空");
            orderEntity.setPayStatus(PayStatusEnum.FAIL.getCode());
            orderManager.update(orderEntity);
            result.put("return_code", PayResultEnum.FAIL.getCode());
            String resultXML = RequestUtil.toXml(result);
            log.info("---------------------------------------------返回支付结果result:" + resultXML);
            return resultXML;
        }*/

        //判断支付结果 失败直接返回
        if (ToolUtil.isEmpty(resultCode) || !resultCode.equals(PayResultEnum.SUCCESS.getCode())) {
            log.error("------------------------------------支付失败原因：resultCode为空或者为FAIL");
            result.put("return_code", PayResultEnum.FAIL.getCode());
            String resultXML = RequestUtil.toXml(result);
            log.info("---------------------------------------------返回支付结果result:" + resultXML);
            return resultXML;
        }
        //状态不为待支付直接返回
        //判断状态为已支付时，直接返回
        /*if (PayStatusEnum.SUCCEED.getCode().equals(payDetailEntity.getStatus())) {
            result.put("return_code", PayResultEnum.SUCCESS.getCode());
            result.put("return_msg", "已完成支付");
            String resultXML = RequestUtil.toXml(result);
            log.info("---------------------------------------------返回支付结果result:" + resultXML);
            return resultXML;
        }*/
        result.put("return_code", PayResultEnum.SUCCESS.getCode());
        String resultXML = RequestUtil.toXml(result);
        log.info("---------------------------------------------返回成功支付结果result:" + resultXML);

        //根据业务修改数据库中支付详情的支付状态

        return resultXML;
    }
}
