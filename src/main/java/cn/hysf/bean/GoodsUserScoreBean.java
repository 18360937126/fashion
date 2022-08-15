package cn.hysf.bean;

import cn.hysf.model.User;

public class GoodsUserScoreBean implements Comparable<GoodsUserScoreBean>{
    private Integer gid;
    private User user;
    private Integer score;

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public int compareTo(GoodsUserScoreBean o) {
        return o.getScore()-this.getScore();
    }
}
