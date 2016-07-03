package com.runzii.model.qiniu;

/**
 * Created by runzii on 16-5-4.
 */
public class CallBackFetchKey {

    /**
     * key : sunflowerc.jpg
     * payload : {"success":"true","name":"sunflowerc.jpg"}
     */

    private String key;
    /**
     * success : true
     * name : sunflowerc.jpg
     */

    private PayloadBean payload;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public PayloadBean getPayload() {
        return payload;
    }

    public void setPayload(PayloadBean payload) {
        this.payload = payload;
    }

    public static class PayloadBean {
        private String success;
        private String name;

        public PayloadBean(String success, String name) {
            this.success = success;
            this.name = name;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
