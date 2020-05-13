package com.hongjf.wxpaydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hongjf.wxpaydemo")
public class WxpaydemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxpaydemoApplication.class, args);
    }

}
