package cn.hysf.bean;

import cn.hysf.model.Ordersinfo;

public class OrdersInfoBean extends Ordersinfo {
    private String gname1;

    private String gname2;

    private String gimg1;

    private Double gprice;

    private String gsize1;

    public String getGname1() {
        return gname1;
    }

    public void setGname1(String gname1) {
        this.gname1 = gname1;
    }

    public String getGname2() {
        return gname2;
    }

    public void setGname2(String gname2) {
        this.gname2 = gname2;
    }

    public String getGimg1() {
        return gimg1;
    }

    public void setGimg1(String gimg1) {
        this.gimg1 = gimg1;
    }

    public Double getGprice() {
        return gprice;
    }

    public void setGprice(Double gprice) {
        this.gprice = gprice;
    }

    public String getGsize1() {
        return gsize1;
    }

    public void setGsize1(String gsize1) {
        this.gsize1 = gsize1;
    }
}
