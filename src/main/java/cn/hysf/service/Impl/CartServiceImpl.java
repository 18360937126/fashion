package cn.hysf.service.Impl;

import cn.hysf.bean.CartBean;
import cn.hysf.mapper.CartMapper;
import cn.hysf.mapper.GoodsMapper;
import cn.hysf.model.Cart;
import cn.hysf.model.Goods;
import cn.hysf.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Resource
    private CartMapper cartMapper;
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public Integer addGoodsInMyCart(Integer uid, int gid) {
        Date date = new Date();
        return cartMapper.addGoodsInMyCart(uid,gid,date);
    }

    @Override
    public List<CartBean> findMyCartsGoodsByUid(Integer uid) {
        //先查询cart表找到uid对应的所有gid
        List<Cart> carts = cartMapper.findGartsByUid(uid);
        //根据gid找到goods商品对象封装成list集合返回给controller
        List<CartBean> cartBeans = new ArrayList<>();
        for (int i = 0; i < carts.size(); i++) {
            Cart cart = carts.get(i);
            CartBean cartBean = new CartBean();
            cartBean.setCid(cart.getCid());
            //根据gid找到goods
            Goods good = goodsMapper.findGoodByGid2(cart.getGid());
            cartBean.setDate(cart.getDate());
            cartBean.setGimg1(good.getGimg1());
            cartBean.setGimg2(good.getGimg2());
            cartBean.setGinfo(good.getGinfo());
            cartBean.setGkind(good.getGkind());
            cartBean.setGname1(good.getGname1());
            cartBean.setGname2(good.getGname2());
            cartBean.setGprice(good.getGprice());
            cartBean.setGsize1(good.getGsize1());
            cartBean.setGsize2(good.getGsize2());
            cartBean.setUid(uid);
            cartBean.setGid(good.getGid());
            cartBeans.add(cartBean);
        }
        return cartBeans;
    }

    @Override
    public void deleteGoodByCid(String cid) {
        cartMapper.deleteCartByCid(cid);
    }

    @Override
    public Integer findCartNumbersByUid(String uid) {
        return cartMapper.findCartNumbersByUid(uid);
    }
}
