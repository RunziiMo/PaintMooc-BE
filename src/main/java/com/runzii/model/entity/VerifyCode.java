package com.runzii.model.entity;

import javax.persistence.*;

/**
 * Created by runzii on 16-4-10.
 */
@Entity
@Table(name = "verifies", schema = "PaintMooc", catalog = "")
public class VerifyCode extends BaseModel{

    @Column(unique = true)
    private String phone;
    private String verifyCode;

    public VerifyCode(String phone, String varifyCode) {
        this.phone = phone;
        this.verifyCode = varifyCode;
    }

    public VerifyCode() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
