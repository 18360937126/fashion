package cn.hysf.service;

import cn.hysf.model.Adminuser;
import cn.hysf.model.User;

public interface AdminuserService {
    Adminuser login(String username, String password);
}
