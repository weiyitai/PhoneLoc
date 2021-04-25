package com.qianbajin.phoneloc;

/**
 * @author qianbajin
 * @date at 2021/4/24 0024  22:04
 */
public class PhoneLoc {

    /**
     * province : 山西
     * city : 太原
     * sp : 移动
     */

    private String province;
    private String city;
    private String sp;
    private long phone;

    public PhoneLoc() {
    }

    public PhoneLoc(String city, String sp) {
        this.city = city;
        this.sp = sp;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "{" +
            "\"province\":\"" + province + "\"" +
            ", \"city\":\"" + city + "\"" +
            ", \"sp\":\"" + sp + "\"" +
            '}';
    }
}
