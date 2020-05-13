package com.hongjf.wxpaydemo.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: hongjf
 * @Date: 2020/1/6
 * @Time: 22:32
 * @Description:请求工具类
 */
@Slf4j
public class RequestUtil {

    private static final String SPLIT = ",";
    private static final int IP_LENGTH = 15;
    private static final String LOCAL_HOST = "0:0:0:0:0:0:0:1";
    private static final String LOCAL = "127.0.0.1";
    private static final String UNKNOWN = "unknown";

    public static String getIp(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (LOCAL_HOST.equals(ipAddress) || LOCAL.equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > IP_LENGTH) {
                if (ipAddress.indexOf(SPLIT) > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(SPLIT));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

    /**
     * 获取request中的参数，并将参数转为xml
     *
     * @param request
     * @return
     */
    public static String readData(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder result = new StringBuilder();
            br = request.getReader();
            for (String line; (line = br.readLine()) != null; ) {
                if (result.length() > 0) {
                    result.append("\n");
                }
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 将map转为xml
     *
     * @param params map参数
     * @return
     */
    public static String toXml(Map<String, String> params) {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        Iterator var2 = params.entrySet().iterator();

        while (var2.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) var2.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (ToolUtil.isNotEmpty(value)) {
                xml.append("<").append(key).append(">");
                xml.append((String) entry.getValue());
                xml.append("</").append(key).append(">");
            }
        }
        xml.append("</xml>");
        return xml.toString();
    }

}
