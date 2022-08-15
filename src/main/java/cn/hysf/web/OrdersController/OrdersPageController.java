package cn.hysf.web.OrdersController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ordersPage")
public class OrdersPageController {

    @RequestMapping("/placeorderPage")
    public String placeorderPage(){
        return "place-an-order";
    }

    @RequestMapping("/mainPage")
    public String mainPage(){
        return "orders";
    }

    @RequestMapping("/ordersInfo")
    public ModelAndView ordersInfo(String oid){
        ModelAndView mv = new ModelAndView();
        mv.addObject("oid",oid);
        mv.setViewName("ordersInfo");
        return mv;
    }
}
