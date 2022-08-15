package cn.hysf.service;

import cn.hysf.bean.WishlistBean;
import cn.hysf.model.Wishlist;

import java.util.List;

public interface WishlistService {

    Integer addGoodsInMyWishList(Integer uid, int gid);

    Wishlist findMyWishListByGidAndUid(int gid1, Integer uid);

    List<WishlistBean> findMyWishListByUid(String uid);

    void deleteMyWishlistByWid(String wid);

    Integer findWishListNumbersByUid(String uid);
}
