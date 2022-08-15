package cn.hysf.mapper;

import cn.hysf.model.Wishlist;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface WishlistMapper {
    Wishlist findListByUidAndGid(Integer uid, int gid);

    Integer addMyWishList(Integer uid, int gid, Date date);

    Integer deleteMyWishListByUidAndGid(Integer uid, int gid);

    List<Wishlist> findMyWishListByUid(String uid);

    void deleteMyWishlistByWid(String wid);

    Integer findWishListNumbersByUid(String uid);

    List<Integer> findAllGids();

    List<Integer> findGidsByUid(Integer uid);
}
