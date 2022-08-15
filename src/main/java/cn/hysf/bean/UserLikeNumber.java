package cn.hysf.bean;

import cn.hysf.model.User;

import java.util.Comparator;

public class UserLikeNumber implements Comparable<UserLikeNumber> {
    private User user;
    private Double userLikeNumber;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getUserLikeNumber() {
        return userLikeNumber;
    }

    public void setUserLikeNumber(Double userLikeNumber) {
        this.userLikeNumber = userLikeNumber;
    }

    @Override
    public String toString() {
        return "UserLikeNumber{" +
                "user=" + user +
                ", userLikeNumber=" + userLikeNumber +
                '}';
    }

    @Override
    public int compareTo(UserLikeNumber o) {
        return new Double(o.getUserLikeNumber()).compareTo(new Double(this.getUserLikeNumber()));
    }
}
