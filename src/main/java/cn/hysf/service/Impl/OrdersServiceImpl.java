package cn.hysf.service.Impl;

import cn.hysf.Page.PageRequest;
import cn.hysf.Page.PageResult;
import cn.hysf.Page.PageUtils;
import cn.hysf.bean.OrdersBean;
import cn.hysf.bean.OrdersInfoBean;
import cn.hysf.mapper.CartMapper;
import cn.hysf.mapper.GoodsMapper;
import cn.hysf.mapper.OrdersMapper;
import cn.hysf.mapper.OrdersinfoMapper;
import cn.hysf.model.Goods;
import cn.hysf.model.Orders;
import cn.hysf.model.Ordersinfo;
import cn.hysf.service.OrdersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private OrdersinfoMapper ordersinfoMapper;
    @Resource
    private CartMapper cartMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Override
    public void pleaseAnOrder(Ordersinfo ordersinfo) {
        ordersinfoMapper.makeAnOrderInfo(ordersinfo);
    }

    @Override
    public void makeAnBigOrders(Orders orders) {
        ordersMapper.addAnBigOrders(orders);
    }

    @Override
    public List<OrdersBean> findOrdersByUid(String uid) {
        List<Orders> orders = ordersMapper.findOrdersByUid(uid);
        List<OrdersBean> beans = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            Orders orders1 = orders.get(i);
            OrdersBean bean = new OrdersBean();
            //转换时间格式
            String strDateFormat = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            String format = sdf.format(orders1.getDate());
            bean.setDate(format);
            bean.setOid(orders1.getOid());
            bean.setUid(orders1.getUid());
            Integer status = orders1.getStatus();
            if(status==0){
                bean.setStatus("Payment");
            }
            if(status==1){
                bean.setStatus("Delivery");
            }
            if(status==2){
                bean.setStatus("In transit");
            }
            if(status==3){
                bean.setStatus("Arrival");
            }
            if(status==4){
                bean.setStatus("Sign for");
            }
            beans.add(bean);
        }
        return beans;
    }

    @Override
    public List<Ordersinfo> findOrdersInfoByOid(String oid) {
        return ordersinfoMapper.findOrdersInfoByOid(oid);
    }

    @Override
    public List<OrdersInfoBean> findOrdersInfoDetailList(String oid) {
        //根据oid查找orderinfo
        List<Ordersinfo> ordersinfos = ordersinfoMapper.findOrdersInfoByOid(oid);
        List<OrdersInfoBean> beans = new ArrayList<>();
        for (int i = 0; i < ordersinfos.size(); i++) {
            Ordersinfo ordersinfo = ordersinfos.get(i);
            Integer gid = ordersinfo.getGid();
            //根据gid查找goods商品信息
            Goods goods = goodsMapper.findGoodByGid2(gid);
            OrdersInfoBean bean = new OrdersInfoBean();
            bean.setGimg1(goods.getGimg1());
            bean.setGname1(goods.getGname1());
            bean.setGname2(goods.getGname2());
            bean.setGprice(goods.getGprice());
            bean.setGsize1(goods.getGsize1());

            bean.setGid(goods.getGid());
            bean.setOid(ordersinfo.getOid());
            bean.setId(ordersinfo.getId());
            bean.setUid(ordersinfo.getUid());
            bean.setDate(ordersinfo.getDate());
            bean.setUsername(ordersinfo.getUsername());
            bean.setPostal(ordersinfo.getPostal());
            bean.setPhone(ordersinfo.getPhone());
            bean.setEmail(ordersinfo.getEmail());
            bean.setCity(ordersinfo.getCity());
            bean.setAddress(ordersinfo.getAddress());
            beans.add(bean);
        }
        return beans;
    }

    @Override
    public Integer deleteBigOrdersByOid(String oid) {
        //先删除ordersinfo
        Integer count = ordersinfoMapper.deleteByOid(oid);
        //再删除orders
        Integer count1 = ordersMapper.deleteByOid(oid);
        return count+count1;
    }

    @Override
    public Integer deleteOrdersInfoById(String id) {
        return ordersinfoMapper.deleteOrdersInfoById(id);
    }


    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    @Override
    public List<OrdersInfoBean> findOrdersinfoDetailByOrdersinfoList(List<Ordersinfo> content) {

        List<OrdersInfoBean> beans = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            Ordersinfo ordersinfo = content.get(i);
            Integer gid = ordersinfo.getGid();
            //根据gid查找goods商品信息
            Goods goods = goodsMapper.findGoodByGid2(gid);
            OrdersInfoBean bean = new OrdersInfoBean();
            bean.setGimg1(goods.getGimg1());
            bean.setGname1(goods.getGname1());
            bean.setGname2(goods.getGname2());
            bean.setGprice(goods.getGprice());
            bean.setGsize1(goods.getGsize1());

            bean.setGid(goods.getGid());
            bean.setOid(ordersinfo.getOid());
            bean.setId(ordersinfo.getId());
            bean.setUid(ordersinfo.getUid());
            bean.setDate(ordersinfo.getDate());
            bean.setUsername(ordersinfo.getUsername());
            bean.setPostal(ordersinfo.getPostal());
            bean.setPhone(ordersinfo.getPhone());
            bean.setEmail(ordersinfo.getEmail());
            bean.setCity(ordersinfo.getCity());
            bean.setAddress(ordersinfo.getAddress());
            beans.add(bean);
        }
        return beans;
    }


    private PageInfo<Ordersinfo> getPageInfo(PageRequest pageRequest) {
        /**
         * 调用分页插件完成分页
         * @param pageQuery
         * @return
         */
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Ordersinfo> ordersinfos = ordersinfoMapper.selectPage();
        return new PageInfo<Ordersinfo>(ordersinfos);
    }
}
