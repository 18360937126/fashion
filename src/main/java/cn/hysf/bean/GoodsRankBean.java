package cn.hysf.bean;

public class GoodsRankBean implements Comparable<GoodsRankBean>{
    private Integer gid;
    private Integer count;

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public int compareTo(GoodsRankBean o) {
        return o.getCount()-this.getCount();
    }
}
