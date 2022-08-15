package cn.hysf.bean;

import java.util.Date;

public class OrdersBean {
    private Long oid;

    private Integer uid;

    /**
     *     0 Payment 付款
     *     1 Delivery 发货
     *     2 In transit 运送中
     *     3 Arrival 到货
     *     4 Sign for 签收
     */
    private String status;

    private String date;

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
