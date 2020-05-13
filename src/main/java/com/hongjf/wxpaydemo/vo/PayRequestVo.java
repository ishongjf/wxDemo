package com.hongjf.wxpaydemo.vo;

import lombok.Data;

/**
 * Copyright 2019 ZhiLiao, Inc. All rights reserved.
 *
 * @Author: hongjf
 * @Date: 2020/2/13
 * @Time: 17:56
 * @Description:支付请求返回类
 */
@Data
public class PayRequestVo {

    /**
     * appId
     */
    private String appId;
    /**
     * 商户号id
     */
    private String mchId;
    /**
     * 预支付交易会话标识
     */
    private String prepayId;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 时间戳
     */
    private String timeStamp;
    /**
     * 签名加密类型
     */
    private String signType;
    /**
     * 签名
     */
    private String sign;
    /**
     * 支付类型
     */
    private String tradeType;
    /**
     * 签名package
     */
    private String payPackage;
}
