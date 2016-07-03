package com.runzii.model.qiniu;

/**
 * Created by runzii on 16-4-13.
 */
public class QiNiuStrategy {

    private String scope = "paintmooc";
    private long deadline = System.currentTimeMillis() / 1000L + 3600L;
    private int insertOnly = 0;
    private String endUser = "Runzii";
    private String returnUrl;
    private String returnBody;
    private String callbackUrl = "http://101.75.141.163:8080/video/handleqiniu";
    private String callbackHost = "101.75.141.163";
    private String callbackBody = "key=$(key)&hash=$(etag)&uuid=$(uuid)";
    private String callbackBodyType = "application/json";
    private int callbackFetchKey = 0;
    private String persistentOps;
    private String persistentNotifyUrl;
    private String persistentPipeline;
    private String saveKey;
    private long fsizeMin = 1024 * 256;
    private long fsizeLimit = 1024 * 1024 * 256;
    private int detectMime;
    private String mimeLimit;
    private int deleteAfterDays = 3;
}
