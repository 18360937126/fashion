package cn.hysf.service;

import cn.hysf.Page.PageRequest;
import cn.hysf.Page.PageResult;
import cn.hysf.model.User;

import java.util.List;

public interface UserService {
    //登录验证
    User login(String username, String password);

    int regist(User user);

    User findUserByUsername(String username);

    void uploadTouxiang(String newFileName,String uid);

    List<User> findAllUser();

    PageResult findPage(PageRequest pageQuery);

    void deleteUserByUid(String uid);
}
