package cn.hysf.web;

import cn.hysf.Page.PageRequest;
import cn.hysf.Page.PageResult;
import cn.hysf.bean.CommentBean;
import cn.hysf.bean.OrdersInfoBean;
import cn.hysf.model.Comment;
import cn.hysf.model.Goods;
import cn.hysf.model.Ordersinfo;
import cn.hysf.model.User;
import cn.hysf.service.CommentService;
import cn.hysf.service.GoodsService;
import cn.hysf.service.OrdersService;
import cn.hysf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/declarePage")
public class DeclarePageController {
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private CommentService commentService;

    @RequestMapping("/index")
    public ModelAndView declareHome(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        //管理页面主页是用户管理页面
        //所以要给他用户信息 . 查询所有用户信息
        ModelAndView mv = new ModelAndView();
        PageRequest pageQuery = new PageRequest();
        pageQuery.setPageSize(pageSize);
        pageQuery.setPageNum(pageNum);
        PageResult page = userService.findPage(pageQuery);
        List<User> content = (List<User>) page.getContent();
        page.setContent(content);
        mv.addObject("users",content);
        mv.addObject("page",page);
        mv.setViewName("Declare/index");
        return mv;
    }

    @RequestMapping("/loginPage")
    public ModelAndView loginPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Declare/loginPage");
        return mv;
    }

    @RequestMapping("/changePasswordPage")
    public ModelAndView changePasswordPage(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Declare/changePasswordPage");
        return mv;
    }

    @RequestMapping("/goodsPage")
    public ModelAndView goodsPage(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        //管理页面主页是用户管理页面
        //所以要给他用户信息 . 查询所有用户信息
        ModelAndView mv = new ModelAndView();
        PageRequest pageQuery = new PageRequest();
        pageQuery.setPageSize(pageSize);
        pageQuery.setPageNum(pageNum);
        PageResult page = goodsService.findPage(pageQuery);
        List<Goods> content = (List<Goods>) page.getContent();
        page.setContent(content);
        mv.addObject("goods",content);
        mv.addObject("page",page);
        mv.setViewName("Declare/goods");
        return mv;
    }

    @RequestMapping("/ordersPage")
    public ModelAndView ordersPage(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        //管理页面主页是用户管理页面
        //所以要给他用户信息 . 查询所有用户信息
        ModelAndView mv = new ModelAndView();
        PageRequest pageQuery = new PageRequest();
        pageQuery.setPageSize(pageSize);
        pageQuery.setPageNum(pageNum);
        PageResult page = ordersService.findPage(pageQuery);
        List<Ordersinfo> content = (List<Ordersinfo>) page.getContent();
        List<OrdersInfoBean> content1 = ordersService.findOrdersinfoDetailByOrdersinfoList(content);
        page.setContent(content1);
        mv.addObject("orders",content1);
        mv.addObject("page",page);
        mv.setViewName("Declare/orders");
        return mv;
    }

    @RequestMapping("/commentsPage")
    public ModelAndView commentsPage(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){
        ModelAndView mv = new ModelAndView();
        PageRequest pageQuery = new PageRequest();
        pageQuery.setPageSize(pageSize);
        pageQuery.setPageNum(pageNum);
        PageResult page = commentService.findPage(pageQuery);
        List<Comment> content = (List<Comment>) page.getContent();
        List<CommentBean> beans = commentService.changeCommentForCommentBean(content);
        page.setContent(beans);
        mv.addObject("comments",beans);
        mv.addObject("page",page);
        mv.setViewName("Declare/comments");
        return mv;
    }
}
