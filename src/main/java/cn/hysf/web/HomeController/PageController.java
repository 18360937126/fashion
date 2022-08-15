package cn.hysf.web.HomeController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/homePage")
public class PageController {
    @RequestMapping("/mankind")
    public ModelAndView manKind(ModelAndView mv){
        mv.setViewName("Kind/manKind");
        return mv;
    }

    @RequestMapping("/womanKind")
    public ModelAndView womenKind(ModelAndView mv){
        mv.setViewName("Kind/womanKind");
        return mv;
    }

    @RequestMapping("/underKind")
    public ModelAndView underKind(ModelAndView mv){
        mv.setViewName("Kind/underKind");
        return mv;
    }

    @RequestMapping("/otherKind")
    public ModelAndView otherKind(ModelAndView mv){
        mv.setViewName("Kind/otherKind");
        return mv;
    }
}
