package com.bs.service.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class WeiXinData {
    @Value("${wx.appid}")
    private String appId;
    @Value("${wx.appsecret}")
    private String appSecret;

}
