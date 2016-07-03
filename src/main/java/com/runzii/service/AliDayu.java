package com.runzii.service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by runzii on 16-4-19.
 */
@Service
public class AliDayu {

    private static final Logger logger = Logger.getLogger(AliDayu.class);

    @Value("${messagesdk.alidayu.appkey}")
    private String appkey;

    @Value("${messagesdk.alidayu.appsecret}")
    private String appsecret;

    @Autowired
    private TaobaoClient taobaoClient;

    @Bean
    public TaobaoClient taobaoClient() {
        return new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest"
                , appkey, appsecret);
    }

    public String sendMessage(String phone, String randNum, int type) throws ApiException {
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        switch (type) {
            case 1:
                req.setSmsFreeSignName("注册验证");
                req.setSmsTemplateCode("SMS_7496352");
                break;
            case 2:
                req.setSmsFreeSignName("身份验证");
                req.setSmsTemplateCode("SMS_7496350");
                break;
        }
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("注册验证");
        req.setSmsParamString("{\"code\":\"" + randNum + "\",\"product\":\"画吧\"}");
        req.setRecNum(phone);
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        rsp = taobaoClient.execute(req);
        return rsp.getBody();
    }

    public String getRandNum(int charCount) {
        StringBuilder charValue = new StringBuilder();
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue.append(c);
        }
        return charValue.toString();
    }

    private int randomInt(int from, int to) {
        return from + new Random().nextInt(to - from);
    }
}
