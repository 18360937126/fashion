package cn.hysf.service.Impl;

import cn.hysf.mapper.AdminuserMapper;
import cn.hysf.model.Adminuser;
import cn.hysf.model.User;
import cn.hysf.service.AdminuserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminuserServiceImpl implements AdminuserService {
    @Resource
    private AdminuserMapper adminuserMapper;
    @Override
    public Adminuser login(String username, String password) {
        Adminuser user = null;
        user = adminuserMapper.findUserByUsernameAndPassword(username, password);
        return user;
    }
}
