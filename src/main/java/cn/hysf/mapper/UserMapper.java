package cn.hysf.mapper;

import cn.hysf.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserMapper {
    

    List<User> selectPage();

    void deleteUserByUid(String uid);

    List<User> findAllUser();

    void uploadTouxiang(String newFileName, String uid);

    User findUserByUsername(String username);

    int regist(User user);

    User findUserByUsernameAndPassword(String username, String password);

    User findUserByUid(Integer uid);
}