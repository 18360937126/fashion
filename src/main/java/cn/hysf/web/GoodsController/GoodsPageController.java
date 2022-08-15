package cn.hysf.web.GoodsController;

import cn.hysf.Page.PageRequest;
import cn.hysf.Page.PageResult;
import cn.hysf.bean.CommentBean;
import cn.hysf.model.Comment;
import cn.hysf.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/goodsPage")
public class GoodsPageController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/detail")
    public ModelAndView detail(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String gid){
        ModelAndView mv = new ModelAndView();
        mv.addObject("gid",gid);
        mv.setViewName("goods-info");

        PageRequest pageQuery = new PageRequest();
        pageQuery.setPageSize(pageSize);
        pageQuery.setPageNum(pageNum);
        int i = Integer.parseInt(gid);
        PageResult page = commentService.findPageByGid(pageQuery,i);
        List<Comment> content = (List<Comment>) page.getContent();
        List<CommentBean> beans = commentService.changeCommentForCommentBean(content);
        page.setContent(beans);
        mv.addObject("comments",beans);
        mv.addObject("page",page);


        return mv;
    }

    @RequestMapping("/goods-info")
    public String goodsinfoPage(){
        return "goods-info";
    }
}
