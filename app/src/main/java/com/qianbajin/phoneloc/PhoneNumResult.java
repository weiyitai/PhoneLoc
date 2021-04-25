package com.qianbajin.phoneloc;

/**
 * @author qianbajin
 * @date at 2021/4/18 0018  14:06
 */
public class PhoneNumResult {

    /**
     * code : 0
     * data : {"province":"山西","city":"太原","sp":"移动"}
     */

    private int code;
    private PhoneLoc data;
    private long phone;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public PhoneLoc getData() {
        return data;
    }

    public void setData(PhoneLoc data) {
        this.data = data;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public long getPhone() {
        return phone;
    }


    @Override
    public String toString() {
        return "{" +
            "\"code\":" + code +
            ", \"data\":" + data +
            ", \"phone\":" + phone +
            '}';
    }
}
