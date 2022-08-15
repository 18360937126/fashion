package cn.hysf.web.CartController;

import cn.hysf.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cartPage")
public class CartPageController {
    @RequestMapping("/myCart")
    public ModelAndView myCartPage(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        User user = (User) request.getSession().getAttribute("user");
        mv.addObject("user",user);
        mv.setViewName("cart");
        return mv;
    }
}
