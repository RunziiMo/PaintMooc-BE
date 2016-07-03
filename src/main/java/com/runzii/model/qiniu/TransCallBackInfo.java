package com.runzii.model.qiniu;

import java.util.List;

/**
 * Created by runzii on 16-4-13.
 */
public class TransCallBackInfo {


    /**
     * id : 16864pauo1vc9nhp12
     * code : 0
     * desc : The fop was completed successfully
     * inputKey : sample.mp4
     * inputBucket : dutest
     * items : [{"cmd":"avthumb/mp4/r/30/vb/256k/vcodec/libx264/ar/22061/ab/64k/acodec/libmp3lame","code":0,"desc":"The fop was completed successfully","error":"","hash":"FrPNF2qz66Bt14JMdgU8Ya7axZx-","key":"v-PtT-DzpyCcqv6xNU25neTMkcc=/FjgJQXuH7OresQL4zgRqYG5bZ64x","returnOld":0},{"cmd":"avthumb/iphone_low","code":0,"desc":"The fop was completed successfully","error":"","hash":"FmZ5PbHMYD5uuP1-kHaLjKbrv-75","key":"tZ-w8jHlQ0__PYJdiisskrK5h3k=/FjgJQXuH7OresQL4zgRqYG5bZ64x","returnOld":0},{"cmd":"avthumb/m3u8/r/30/vb/256k/vcodec/libx264/ar/22071/ab/64k/acodec/libmp3lame","code":0,"desc":"The fop was completed successfully","error":"","hash":"Fi4gMX0SvKVvptxfvoiuDfFkCuEG","key":"8ehryqviSaMIjkVQDGeDcKRZ6qc=/FjgJQXuH7OresQL4zgRqYG5bZ64x","returnOld":0},{"cmd":"avthumb/m3u8/vb/440k","code":0,"desc":"The fop was completed successfully","error":"","hash":"FtuxnwAY9NVBxAZLcxNUuToR9y97","key":"s2_PQlcIOz1uP6VVBXk5O9dXYLY=/FjgJQXuH7OresQL4zgRqYG5bZ64x","returnOld":0}]
     * pipeline : 0.default
     * reqid : ffmpeg.3hMAAH3p5Gupb6oT
     */

    private String id;
    private int code;
    private String desc;
    private String inputKey;
    private String inputBucket;
    private String pipeline;
    private String reqid;

    /**
     * cmd : avthumb/mp4/r/30/vb/256k/vcodec/libx264/ar/22061/ab/64k/acodec/libmp3lame
     * code : 0
     * desc : The fop was completed successfully
     * error :
     * hash : FrPNF2qz66Bt14JMdgU8Ya7axZx-
     * key : v-PtT-DzpyCcqv6xNU25neTMkcc=/FjgJQXuH7OresQL4zgRqYG5bZ64x
     * returnOld : 0
     */

    private List<ItemsBean> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInputKey() {
        return inputKey;
    }

    public void setInputKey(String inputKey) {
        this.inputKey = inputKey;
    }

    public String getInputBucket() {
        return inputBucket;
    }

    public void setInputBucket(String inputBucket) {
        this.inputBucket = inputBucket;
    }

    public String getPipeline() {
        return pipeline;
    }

    public void setPipeline(String pipeline) {
        this.pipeline = pipeline;
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        private String cmd;
        private int code;
        private String desc;
        private String error;
        private String hash;
        private String key;
        private int returnOld;

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getReturnOld() {
            return returnOld;
        }

        public void setReturnOld(int returnOld) {
            this.returnOld = returnOld;
        }

        @Override
        public String toString() {
            return "ItemsBean{" +
                    "cmd='" + cmd + '\'' +
                    ", code=" + code +
                    ", desc='" + desc + '\'' +
                    ", error='" + error + '\'' +
                    ", hash='" + hash + '\'' +
                    ", key='" + key + '\'' +
                    ", returnOld=" + returnOld +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TransCallBackInfo{" +
                "id='" + id + '\'' +
                ", code=" + code +
                ", desc='" + desc + '\'' +
                ", inputKey='" + inputKey + '\'' +
                ", inputBucket='" + inputBucket + '\'' +
                ", pipeline='" + pipeline + '\'' +
                ", reqid='" + reqid + '\'' +
                ", items=" + items +
                '}';
    }
}
