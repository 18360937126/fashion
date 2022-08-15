package cn.hysf.web;

import cn.hysf.Page.PageRequest;
import cn.hysf.Page.PageResult;
import cn.hysf.model.Adminuser;
import cn.hysf.model.ResultInfo;
import cn.hysf.model.User;
import cn.hysf.service.AdminuserService;
import cn.hysf.service.UserService;
import cn.hysf.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserController {
    @Autowired
    private AdminuserService adminuserService;
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    @ResponseBody
    public ResultInfo adminLogin(HttpServletRequest request, String username, String password) throws Exception {
        ResultInfo info = new ResultInfo();
        if (username == null && "".equals(username) || password == null && "".equals(password)) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码不能为空");
        } else {
            //先查找用户是否存在
            Adminuser adminuser = adminuserService.login(username, password);
            if (adminuser == null) {
                info.setFlag(false);
                info.setErrorMsg("用户尚未注册");
            }
            if (adminuser != null){
                request.getSession().setAttribute("adminuser",adminuser);
                info.setData(adminuser);
                info.setFlag(true);
            }
        }
        return info;
    }

    @RequestMapping("/findAdmin")
    @ResponseBody
    public Adminuser findAdmin(HttpServletRequest request){
        Adminuser adminuser = null;
        adminuser = (Adminuser) request.getSession().getAttribute("adminuser");
        return adminuser;
    }

    @RequestMapping("/deleteUserByUid")
    public ModelAndView deleteUserByUid(String uid){
        userService.deleteUserByUid(uid);
        ModelAndView mv = new ModelAndView();

        PageRequest pageQuery = new PageRequest();
        pageQuery.setPageSize(0);
        pageQuery.setPageNum(5);
        PageResult page = userService.findPage(pageQuery);
        List<User> content = (List<User>) page.getContent();
        page.setContent(content);
        mv.addObject("users",content);
        mv.addObject("page",page);

        mv.setViewName("/Declare/index");
        return mv;
    }
}
