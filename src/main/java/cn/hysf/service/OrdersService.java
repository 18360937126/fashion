package cn.hysf.service;

import cn.hysf.Page.PageRequest;
import cn.hysf.Page.PageResult;
import cn.hysf.bean.OrdersBean;
import cn.hysf.bean.OrdersInfoBean;
import cn.hysf.model.Orders;
import cn.hysf.model.Ordersinfo;

import java.util.List;

public interface OrdersService {
    void pleaseAnOrder(Ordersinfo ordersinfo);

    void makeAnBigOrders(Orders orders);

    List<OrdersBean> findOrdersByUid(String uid);

    List<Ordersinfo> findOrdersInfoByOid(String oid);

    List<OrdersInfoBean> findOrdersInfoDetailList(String oid);

    Integer deleteBigOrdersByOid(String oid);

    Integer deleteOrdersInfoById(String id);

    public PageResult findPage(PageRequest pageRequest);

    List<OrdersInfoBean> findOrdersinfoDetailByOrdersinfoList(List<Ordersinfo> content);
}
