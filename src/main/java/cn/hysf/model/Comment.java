package cn.hysf.model;

import java.util.Date;

public class Comment extends CommentKey {
    private String comment;

    private Date recordtime;

    private Integer uid;

    private Integer level;

    private Integer gid;

    private String ordersinfoId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(Date recordtime) {
        this.recordtime = recordtime;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getOrdersinfoId() {
        return ordersinfoId;
    }

    public void setOrdersinfoId(String ordersinfoId) {
        this.ordersinfoId = ordersinfoId;
    }
}