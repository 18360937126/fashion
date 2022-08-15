package cn.hysf.mapper;

import cn.hysf.model.Adminuser;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminuserMapper {
    Adminuser findUserByUsernameAndPassword(String username, String password);
}
