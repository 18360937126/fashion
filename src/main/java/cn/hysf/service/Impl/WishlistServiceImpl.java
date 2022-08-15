package cn.hysf.service.Impl;

import cn.hysf.bean.WishlistBean;
import cn.hysf.mapper.GoodsMapper;
import cn.hysf.mapper.WishlistMapper;
import cn.hysf.model.Goods;
import cn.hysf.model.Wishlist;
import cn.hysf.service.WishlistService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {
    @Resource
    private WishlistMapper wishlistMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Override
    public Integer addGoodsInMyWishList(Integer uid, int gid) {
        //先查询是存在，如果存在则删除，如果不存在则添加
        Wishlist wishlist = wishlistMapper.findListByUidAndGid(uid,gid);
        if(wishlist==null){
            //添加
            Integer i = wishlistMapper.addMyWishList(uid,gid,new Date());
            return i;
        }else{
            //反之则删除
            Integer i =wishlistMapper.deleteMyWishListByUidAndGid(uid,gid);
            return i;
        }

    }

    @Override
    public Wishlist findMyWishListByGidAndUid(int gid1, Integer uid) {
        return wishlistMapper.findListByUidAndGid(uid,gid1);
    }

    @Override
    public List<WishlistBean> findMyWishListByUid(String uid) {
        List<Wishlist> list = wishlistMapper.findMyWishListByUid(uid);
        List<WishlistBean> beans = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Wishlist wishlist = list.get(i);
            WishlistBean bean = new WishlistBean();
            bean.setWid(wishlist.getWid());
            bean.setUid(Integer.parseInt(uid));
            bean.setGid(wishlist.getGid());
            bean.setDate(wishlist.getDate());
            //根据gid查找goods商品对象
            Goods goodByGid2 = goodsMapper.findGoodByGid2(wishlist.getGid());
            bean.setGimg1(goodByGid2.getGimg1());
            bean.setGname1(goodByGid2.getGname1());
            bean.setGname2(goodByGid2.getGname2());
            bean.setGprice(goodByGid2.getGprice());
            beans.add(bean);
        }
        return beans;
    }

    @Override
    public void deleteMyWishlistByWid(String wid) {
        wishlistMapper.deleteMyWishlistByWid(wid);
    }

    @Override
    public Integer findWishListNumbersByUid(String uid) {
        return wishlistMapper.findWishListNumbersByUid(uid);
    }
}
