package com.runzii.service;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * Created by runzii on 16-4-22.
 */
@Service
public class QiNiu {

    @Value("${videosdk.qiniu.accesskey}")
    private String accessKey;

    @Value("${videosdk.qiniu.secretkey}")
    private String secretKey;

    @Value("${videosdk.qiniu.bucket-domain}")
    private String bucketDomain;

    @Autowired
    private Auth auth;

    @Autowired
    private BucketManager bucketManager;

    @Bean
    public Auth auth() {
        return Auth.create(accessKey, secretKey);
    }

    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth);
    }

    public String getUpToken(String phone) {
        return auth.uploadToken("paintmooc", null, 3600, new StringMap()
                .put("endUser", phone)
                .put("callbackUrl", "http://120.27.54.84:8081/courses/handleqiniu")
                .put("callbackBody", "{\"bucket\":\"$(bucket)\",\n" +
                        "\"key\":\"$(key)\",\n" +
                        "\"hash\":\"$(etag)\",\n" +
                        "\"cid\":$(x:cid),\n" +
                        "\"videoname\":\"$(x:videoname)\"}")
                .put("callbackBodyType", "application/json")
                .put("fsizeMin", 1024 * 256)
                .put("fsizeLimit", 1024 * 1024 * 128)
                .putNotEmpty("persistentOps", "avthumb/mp4/s/640x360/autoscale/1/vb/1.25m")
                .putNotEmpty("persistentPipeline", "paintmooc_teach_0233")
                .put("persistentNotifyUrl", "http://120.27.54.84:8081/courses/handleready"), true);
    }

    public String generateURL(String key) {
        String URL = "http://" + bucketDomain + "/" + key;
        return auth.privateDownloadUrl(URL, 3600);
    }

    public void deleteVideo(String bucket, String key) {
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException e) {
            e.printStackTrace();
            throw new RuntimeException("删除视频失败");
        }
    }

}
