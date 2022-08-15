package cn.hysf.web.WishlistController;

import cn.hysf.bean.WishlistBean;
import cn.hysf.model.User;
import cn.hysf.model.Wishlist;
import cn.hysf.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;
    @RequestMapping("/addMyWishlist")
    @ResponseBody
    public Integer addMyWishlist(String gid, HttpServletRequest request) {
        int gid1 = Integer.parseInt(gid);
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        if (user == null) return 0;
        Integer i = wishlistService.addGoodsInMyWishList(user.getUid(), gid1);
        return i;
    }
    @RequestMapping("/findMyWishListByGid")
    @ResponseBody
    public Integer findMyWishListByGid(String gid,HttpServletRequest request){
        int gid1 = Integer.parseInt(gid);
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        if (user == null) return 0;
        Wishlist wishlist = wishlistService.findMyWishListByGidAndUid(gid1,user.getUid());
        if(wishlist!=null) return 1;
        return 0;
    }

    @RequestMapping("/findMyWishlistByUid")
    @ResponseBody
    public List<WishlistBean> findMyWishlistByUid(String uid){
        return wishlistService.findMyWishListByUid(uid);
    }

    @RequestMapping("/deleteMyWishlistByWid")
    public String deleteMyWishlistByWid(String wid){
        wishlistService.deleteMyWishlistByWid(wid);
        return "wishlist";
    }

    @RequestMapping("/findWishListNumbersByUid")
    @ResponseBody
    public Integer findWishListNumbersByUid(String uid){
        return wishlistService.findWishListNumbersByUid(uid);
    }
}
