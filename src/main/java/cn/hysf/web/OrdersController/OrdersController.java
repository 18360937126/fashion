package cn.hysf.web.OrdersController;

import cn.hysf.bean.CartBean;
import cn.hysf.bean.OrdersBean;
import cn.hysf.bean.OrdersInfoBean;
import cn.hysf.model.Orders;
import cn.hysf.model.Ordersinfo;
import cn.hysf.model.User;
import cn.hysf.service.CartService;
import cn.hysf.service.OrdersService;
import cn.hysf.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private CartService cartService;

    @RequestMapping("/pleaseAnOrder")
    @ResponseBody
    public void pleaseAnOrder(String email, String username, String city, String phone, String postal, String address, HttpServletRequest request){
        //根据uid查询所有的购物车中的gid
        Date date = new Date();
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        List<CartBean> cartBeans = cartService.findMyCartsGoodsByUid(user.getUid());

        //生成唯一订单编号
        SnowFlake worker = new SnowFlake(1,1,1);
        long l = worker.nextId();

        //先添加生成一个大订单
        Orders orders = new Orders();
        orders.setDate(date);
        orders.setOid(l);
        orders.setStatus(0);
        orders.setUid(user.getUid());
        ordersService.makeAnBigOrders(orders);

        for (int i = 0; i < cartBeans.size(); i++) {
            CartBean cartBean = cartBeans.get(i);
            Ordersinfo ordersinfo = new Ordersinfo();
            ordersinfo.setOid(l);
            ordersinfo.setUid(user.getUid());
            ordersinfo.setDate(date);
            ordersinfo.setAddress(address);
            ordersinfo.setCity(city);
            ordersinfo.setEmail(email);
            ordersinfo.setPhone(phone);
            ordersinfo.setUsername(username);
            ordersinfo.setPostal(postal);
            ordersinfo.setGid(cartBean.getGid());
            //生成订单
            ordersService.pleaseAnOrder(ordersinfo);
            //删除购物车 根据cid
            cartService.deleteGoodByCid(String.valueOf(cartBean.getCid()));
        }

    }

    @RequestMapping("/findOrdersByUid")
    @ResponseBody
    public List<OrdersBean> findOrdersByUid(String uid){
        return ordersService.findOrdersByUid(uid);
    }

    @RequestMapping("/findOrdersInfoByOid")
    @ResponseBody
    public List<Ordersinfo> findOrdersInfoByOid(String oid){
        return ordersService.findOrdersInfoByOid(oid);
    }

    @RequestMapping("/findOrdersInfoDetailList")
    @ResponseBody
    public List<OrdersInfoBean> findOrdersInfoDetailList(String oid){
        return ordersService.findOrdersInfoDetailList(oid);
    }

    @RequestMapping("/deleteBigOrdersByOid")
    @ResponseBody
    public Integer deleteBigOrdersByOid(String oid){
        return ordersService.deleteBigOrdersByOid(oid);
    }

    @RequestMapping("/deleteOrdersInfoById")
    @ResponseBody
    public Integer deleteOrdersInfoById(String id){
        return ordersService.deleteOrdersInfoById(id);
    }
}
