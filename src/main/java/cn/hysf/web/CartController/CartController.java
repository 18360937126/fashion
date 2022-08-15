package cn.hysf.web.CartController;

import cn.hysf.bean.CartBean;
import cn.hysf.model.Goods;
import cn.hysf.model.User;
import cn.hysf.model.Wishlist;
import cn.hysf.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/cart")
@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @RequestMapping("/addMyCarts")
    @ResponseBody
    public Integer addMyCarts(String gid, HttpServletRequest request){
        int gid1 = Integer.parseInt(gid);
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        if(user==null) return 0;
        Integer i = cartService.addGoodsInMyCart(user.getUid(),gid1);
        return i;
    }

    @RequestMapping("/findMyCartsByUid")
    @ResponseBody
    public List<CartBean> findMyCartsByUid(HttpServletRequest request){
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        return cartService.findMyCartsGoodsByUid(user.getUid());
    }

    @RequestMapping("/findMyCartsByUid2")
    @ResponseBody
    public List<CartBean> findMyCartsByUid2(HttpServletRequest request){
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        return cartService.findMyCartsGoodsByUid(user.getUid());
    }

    @RequestMapping("/deleteGoodInMyCart")
    public String deleteGoodInMyCart(String cid){
        //根据cid删除购物车中的某个商品
        cartService.deleteGoodByCid(cid);
        return "cart";
    }

    @RequestMapping("/findCartNumbersByUid")
    @ResponseBody
    public Integer findCartNumbersByUid(String uid){
        Integer count = cartService.findCartNumbersByUid(uid);
        return count;
    }


}
