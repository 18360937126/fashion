package cn.hysf.mapper;

import cn.hysf.model.Ordersinfo;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrdersinfoMapper {
    void makeAnOrderInfo(Ordersinfo ordersinfo);

    List<Ordersinfo> findOrdersInfoByOid(String oid);

    Integer deleteByOid(String oid);

    Integer deleteOrdersInfoById(String id);

    Ordersinfo findOrdersInfoById(String id);

    List<Ordersinfo> selectPage();

    List<Integer> findAllGids();

    List<Integer> findGidsByUid(Integer uid);
}
