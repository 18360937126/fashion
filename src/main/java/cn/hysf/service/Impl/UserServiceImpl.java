package cn.hysf.service.Impl;

import cn.hysf.Page.PageRequest;
import cn.hysf.Page.PageResult;
import cn.hysf.Page.PageUtils;
import cn.hysf.mapper.UserMapper;
import cn.hysf.model.Comment;
import cn.hysf.model.User;
import cn.hysf.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User login(String username, String password) {
        User user = null;
        user = userMapper.findUserByUsernameAndPassword(username, password);
        return user;
    }

    @Override
    public int regist(User user) {
        return userMapper.regist(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public void uploadTouxiang(String newFileName,String uid) {
        userMapper.uploadTouxiang(newFileName,uid);
    }

    @Override
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }

    private PageInfo<User> getPageInfo(PageRequest pageRequest) {
        /**
         * 调用分页插件完成分页
         * @param pageQuery
         * @return
         */
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectPage();
        return new PageInfo<User>(users);
    }

    @Override
    public PageResult findPage(PageRequest pageQuery) {
        return PageUtils.getPageResult(pageQuery, getPageInfo(pageQuery));
    }

    @Override
    public void deleteUserByUid(String uid) {
        userMapper.deleteUserByUid(uid);
    }

}
