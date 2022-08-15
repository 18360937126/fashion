package cn.hysf.service;

import cn.hysf.Page.PageRequest;
import cn.hysf.Page.PageResult;
import cn.hysf.model.Goods;
import cn.hysf.model.User;

import java.util.List;
import java.util.Set;

public interface GoodsService {

    List<Goods> findAllGoods();

    Goods findGoodByGid(String gid);

    List<Goods> findGoodsByKind(String gkind);

    List<Goods> findManTypeGoods();

    List<Goods> findWomanTypeGoods();

    List<Goods> findOtherTypeGoods();

    List<Goods> findUnder100Goods();

    PageResult findPage(PageRequest pageRequest);

    void changeGoodStatusByGid(String gid);

    void addGood(Goods good);

    Set<Goods> findHotGoods(String gid);

    Set<Goods> findUserRecommendsGoods(String gid, User user);
}
