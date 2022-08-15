package cn.hysf.model;

public class Recharge {
    private Integer rid;

    private Integer uid;

    private Double account;

    private Integer ifsuccess;

    private Double money;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Double getAccount() {
        return account;
    }

    public void setAccount(Double account) {
        this.account = account;
    }

    public Integer getIfsuccess() {
        return ifsuccess;
    }

    public void setIfsuccess(Integer ifsuccess) {
        this.ifsuccess = ifsuccess;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}