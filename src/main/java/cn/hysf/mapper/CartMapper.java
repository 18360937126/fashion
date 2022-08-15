package cn.hysf.mapper;

import cn.hysf.model.Cart;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CartMapper {
    Integer addGoodsInMyCart(Integer uid, int gid, Date date);

    List<Cart> findGartsByUid(Integer uid);

    List<Integer> findGidsByUid(Integer uid);

    void deleteCartByCid(String cid);

    Integer findCartNumbersByUid(String uid);

    List<Integer> findAllGids();
}