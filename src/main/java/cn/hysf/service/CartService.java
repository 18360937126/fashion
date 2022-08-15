package cn.hysf.service;

import cn.hysf.bean.CartBean;
import cn.hysf.model.Goods;

import java.util.List;

public interface CartService {
    Integer addGoodsInMyCart(Integer uid, int gid);

    List<CartBean> findMyCartsGoodsByUid(Integer uid);

    void deleteGoodByCid(String cid);

    Integer findCartNumbersByUid(String uid);
}
