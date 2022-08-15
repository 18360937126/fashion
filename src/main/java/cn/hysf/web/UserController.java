package cn.hysf.web;

import cn.hysf.model.ResultInfo;
import cn.hysf.model.User;
import cn.hysf.service.UserService;
import cn.hysf.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    //本地端上传
    @RequestMapping("/uploadTouxiang")
    public String uploadTouxiang(MultipartFile file,HttpServletRequest request,Model model) throws IOException {
        //保存图片的路径
        String filePath = "H:\\360MoveData\\Users\\LENOVO\\Desktop\\fashionShop\\src\\main\\resources\\static\\img\\touxiang\\";
        //获取原始图片的拓展名
        String originalFilename = file.getOriginalFilename();
        //新的文件名字
        String newFileName = UUID.randomUUID() + originalFilename;
        //封装上传文件位置的全路径
        File targetFile = new File(filePath, newFileName);
        //把本地文件上传到封装上传文件位置的全路径
        file.transferTo(targetFile);

        User user = null;
        user = (User) request.getSession().getAttribute("user");

        String newFileName1 = "img/touxiang/" + newFileName;
        userService.uploadTouxiang(newFileName1,String.valueOf(user.getUid()));



        if(user!=null){
            model.addAttribute("user",user);
            return "personal";
        }else{
            return "sign-in";
        }
    }

    //服务器端上传
    @RequestMapping("/uploadTouxiangFuWuQi")
    public String uploadTouxiangFuWuQi(MultipartFile file,HttpServletRequest request,Model model) throws IOException {
        //保存图片的路径
        String filePath = "home\\uploadImg\\touxiang\\";
        //获取原始图片的拓展名
        String originalFilename = file.getOriginalFilename();
        //新的文件名字
        String newFileName = UUID.randomUUID() + originalFilename;
        //封装上传文件位置的全路径
        File targetFile = new File(filePath, newFileName);
        //把本地文件上传到封装上传文件位置的全路径
        file.transferTo(targetFile);

        User user = null;
        user = (User) request.getSession().getAttribute("user");

        String newFileName1 = "/home/uploadImg/touxiang/" + newFileName;
        userService.uploadTouxiang(newFileName1,String.valueOf(user.getUid()));



        if(user!=null){
            model.addAttribute("user",user);
            return "personal";
        }else{
            return "sign-in";
        }
    }



    @RequestMapping("/sign-in")
    public String function(Model model){
        return "sign-in";
    }

    @RequestMapping("/sign-up")
    public String signup(Model model){
        return "sign-up";
    }

    @RequestMapping("/personal")
    public ModelAndView personalPage(HttpServletRequest request){
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        ModelAndView mv = new ModelAndView();
        if(user!=null){
            mv.addObject("user",user);
            mv.setViewName("personal");
        }else{
            mv.setViewName("sign-in");
        }

        return mv;
    }


    @RequestMapping("/")
    public String homePage(Model model){
        return "home";
    }

    @RequestMapping("/login")
    @ResponseBody
    public ResultInfo login(HttpServletRequest request, String username, String password) throws Exception {
        ResultInfo info = new ResultInfo();
        if (username == null && "".equals(username) || password == null && "".equals(password)) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码不能为空");
        } else {
            //先查找用户是否存在
            String passwordMD5 = Md5Util.encodeByMd5(password);
            User user = userService.login(username, passwordMD5);
            if (user == null) {
                info.setFlag(false);
                info.setErrorMsg("用户尚未注册");
            }
            if (user != null){
                request.getSession().setAttribute("user",user);
                info.setData(user);
                info.setFlag(true);
            }
        }
        return info;
    }

    @RequestMapping("/regist")
    @ResponseBody
    public ResultInfo regist(String username, String email, String password1, String password2,String birthday,String address) throws Exception {

        //根据username查询是否有重复的
        User user1 = null;
        user1 = userService.findUserByUsername(username);
        if(user1==null){
            ResultInfo info = new ResultInfo();
            if(password1==password2 && password1.equals(password2)){
                info.setFlag(false);
                info.setErrorMsg("两次输入的密码不一致，请重新输入");
            }else{
                User user = new User();
                user.setUsername(username);
                String passwordMD5 = Md5Util.encodeByMd5(password1);
                user.setPassword(passwordMD5);
                user.setEmail(email);
                user.setTouxiang("img/touxiang/qq1.jpg");
                user.setBirthday(birthday);
                user.setAccount(0.0);
                user.setAddress(address);
                int i = userService.regist(user);
                if(i>0){
                    info.setFlag(true);
                }else{
                    info.setFlag(false);
                    info.setErrorMsg("注册失败");
                }
            }
            return info;
        }else{
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("用户名已被占用");
            return info;
        }
    }



    /**
     * 查找用户姓名
     */
    @RequestMapping("/findUserName")
    @ResponseBody
    public User findName(HttpServletRequest request){
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        return user;
    }


    @RequestMapping("/test")
    public String testPage(){
        return "test";
    }
}
