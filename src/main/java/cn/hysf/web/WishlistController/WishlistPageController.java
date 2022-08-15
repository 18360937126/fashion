package cn.hysf.web.WishlistController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishlistPage")
public class WishlistPageController {
    @RequestMapping("/mainPage")
    public String mainPage(){
        return "wishlist";
    }
}
