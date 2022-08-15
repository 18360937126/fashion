package cn.hysf.web.CommentController;

import cn.hysf.Page.PageRequest;
import cn.hysf.Page.PageResult;
import cn.hysf.bean.CommentBean;
import cn.hysf.model.Comment;
import cn.hysf.model.User;
import cn.hysf.service.CommentService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/comment")
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/makeAnComment")
    @ResponseBody
    public Integer makeAnComment(String id,String level,String comment){
       return commentService.makeAnComment(id,level,comment);
    }

    @RequestMapping(value="/findPage")
    public ModelAndView findPage(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, int gid) {
        PageRequest pageQuery = new PageRequest();
        pageQuery.setPageSize(pageSize);
        pageQuery.setPageNum(pageNum);
        PageResult page = commentService.findPageByGid(pageQuery,gid);
        List<Comment> content = (List<Comment>) page.getContent();
        List<CommentBean> beans = commentService.changeCommentForCommentBean(content);
        page.setContent(beans);
        ModelAndView mv = new ModelAndView();
        mv.addObject("page",page);
        mv.setViewName("goods-info");
        return mv;
    }

    @RequestMapping("/makeAnReply")
    @ResponseBody
    public Integer makeAnReply(String cid, String reply, HttpServletRequest request){
        //未登录无法回复
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        if(user==null) {
            return 0;
        }else{
            Integer count = commentService.makeAnReply(cid,reply,user);
            return count;
        }
    }

    @RequestMapping("/DeleteCommentByCid")
    public ModelAndView deleteUserByUid(String cid){
        commentService.deleteCommentByCid(cid);
        ModelAndView mv = new ModelAndView();

        PageRequest pageQuery = new PageRequest();
        pageQuery.setPageSize(0);
        pageQuery.setPageNum(5);
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
