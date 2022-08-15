package cn.hysf.mapper;

import cn.hysf.model.Orders;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrdersMapper {
 
    void addAnBigOrders(Orders orders);

    List<Orders> findOrdersByUid(String uid);

    Integer deleteByOid(String oid);
}