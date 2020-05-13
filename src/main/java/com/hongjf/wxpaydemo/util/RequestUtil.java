package com.hongjf.wxpaydemo.util;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
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

    private static XPath path;
    private static Document doc;
    private static boolean preventedXXE = false;

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
