package cn.hysf.service.Impl;

import cn.hysf.Page.PageRequest;
import cn.hysf.Page.PageResult;
import cn.hysf.Page.PageUtils;
import cn.hysf.bean.GoodsRankBean;
import cn.hysf.bean.GoodsUserScoreBean;
import cn.hysf.bean.UserLikeNumber;
import cn.hysf.mapper.*;
import cn.hysf.model.Comment;
import cn.hysf.model.Goods;
import cn.hysf.model.User;
import cn.hysf.model.Wishlist;
import cn.hysf.service.GoodsService;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private CartMapper cartMapper;
    @Resource
    private WishlistMapper wishlistMapper;
    @Resource
    private OrdersinfoMapper ordersinfoMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public List<Goods> findAllGoods() {
        return goodsMapper.findAllGoods();
    }

    @Override
    public Goods findGoodByGid(String gid) {
        return goodsMapper.findGoodByGid(gid);
    }

    @Override
    public List<Goods> findGoodsByKind(String gkind) {
        return goodsMapper.findGoodsByKind(gkind);
    }

    @Override
    public List<Goods> findManTypeGoods() {
        return goodsMapper.findManTypeGoods();
    }

    @Override
    public List<Goods> findWomanTypeGoods() {
        return goodsMapper.findWomanTypeGoods();
    }

    @Override
    public List<Goods> findOtherTypeGoods() {
        return goodsMapper.findOtherTypeGoods();
    }

    @Override
    public List<Goods> findUnder100Goods() {
        return goodsMapper.findUnder100Goods();
    }


    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    @Override
    public void changeGoodStatusByGid(String gid) {
        //先查询是在售状态-0 还是下架状态-1
        Goods goodByGid = goodsMapper.findGoodByGid(gid);
        Integer status = goodByGid.getStatus();
        if(status==0){
            goodsMapper.changeGoodStatusTo1(gid);
        }else{
            goodsMapper.changeGoodStatusTo0(gid);
        }
    }

    @Override
    public void addGood(Goods good) {
        goodsMapper.addGoods(good);
    }

    @Override
    public Set<Goods> findHotGoods(String gid) {
        //查找收藏，加购，下单的gid
        //收藏等级1，加购等级2，下单等级3
        //等级最高的前五个进行推荐
        List<Integer> ordersgid = ordersinfoMapper.findAllGids();
        List<Integer> wishlistgid = wishlistMapper.findAllGids();
        List<Integer> cartgid = cartMapper.findAllGids();
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < ordersgid.size(); i++) {
            Integer integer = ordersgid.get(i);
            if(map.get(integer)==null){
                map.put(integer,3);
            }else{
                map.put(integer,map.get(integer)+3);
            }
        }
        for (int i = 0; i < wishlistgid.size(); i++) {
            Integer integer = wishlistgid.get(i);
            if(map.get(integer)==null){
                map.put(integer,1);
            }else{
                map.put(integer,map.get(integer)+1);
            }
        }
        for (int i = 0; i < cartgid.size(); i++) {
            Integer integer = cartgid.get(i);
            if(map.get(integer)==null){
                map.put(integer,2);
            }else{
                map.put(integer,map.get(integer)+2);
            }
        }

        Set<Goods> goods = new HashSet<>();
        List<Integer> counts = new ArrayList<>();
        List<Integer> gids = new ArrayList<>();
        Set<Integer> integers = map.keySet();
        List<GoodsRankBean> rankBeans = new ArrayList<>();
        for (Integer i:integers) {
            Integer integer = map.get(i);
            GoodsRankBean bean = new GoodsRankBean();
            bean.setGid(i);
            bean.setCount(integer);
            rankBeans.add(bean);
        }

        Collections.sort(rankBeans);
        for (int i = 0; i < 5; i++) {
            GoodsRankBean goodsRankBean = rankBeans.get(i);
            Integer integer = goodsRankBean.getGid();
            String s = String.valueOf(integer);
            Goods goodByGid = goodsMapper.findGoodByGid(s);
            goods.add(goodByGid);
        }

        return goods;
    }

    @Override
    public Set<Goods> findUserRecommendsGoods(String gid, User user) {
        Set<Goods> goodsresult = new HashSet<>();
        //查找用户收藏加购的gid
        //查找用户对商品的评分，收藏1分，加购2分，下单3分
        List<Integer> userGids = findUserGoods(user);

        //用户相似度容器
        List<UserLikeNumber> userlikeList = new ArrayList<>();

        //在所有用户中，找到和登录用户相似度最高的用户，并存中挑出商品
        List<User> allUser = userMapper.findAllUser();
        for (int i = 0; i < allUser.size(); i++) {
            User user1 = allUser.get(i);
            if(user.getUid()==user1.getUid()){
                allUser.remove(user1);
            }else{
                //查找相似度 jaccard相似度  交集/并集
                //查找用户收藏加购所有的gid
                List<Integer> user1Gids = findUserGoods(user1);
                int jiaoji = 0;
                int bingji = 0;
                Set<Integer> bingji1 = new HashSet<>();
                for (int j = 0; j < userGids.size(); j++) {
                    bingji1.add(userGids.get(j));
                }
                for (int j = 0; j < user1Gids.size(); j++) {
                    bingji1.add(user1Gids.get(j));
                }
                bingji = bingji1.size();
                for (int j = 0; j < userGids.size(); j++) {
                    Integer integer = userGids.get(j);
                    for (int k = 0; k < user1Gids.size(); k++) {
                        Integer integer1 = user1Gids.get(k);
                        if(integer==integer1){
                            jiaoji++;
                        }
                    }
                }
                double result = (double)jiaoji/(double)bingji;
                Double likeNumber = result;
                UserLikeNumber userLikeNumber = new UserLikeNumber();
                userLikeNumber.setUser(user1);
                userLikeNumber.setUserLikeNumber(likeNumber);
                userlikeList.add(userLikeNumber);
            }
        }
        Collections.sort(userlikeList);
        //根据user找到除了本用户的其他商品
        UserLikeNumber userlikeInfo = userlikeList.get(0);
        List<Integer> userGids2 = findUserGoods(userlikeInfo.getUser());
        for (int i = 0; i < userGids2.size(); i++) {
            Integer integer = userGids2.get(i);
            for (int j = 0; j < userGids.size(); j++) {
                Integer integer1 = userGids.get(j);
                if(integer==integer1){
                    userGids2.remove(integer1);
                }
            }
        }
        if(userGids2.size()<5){
            UserLikeNumber userLikeNumber = userlikeList.get(1);
            List<Integer> userGoods = findUserGoods(userLikeNumber.getUser());
            while(userGids2.size()<5){
                int k = (int)(Math.random()*82+1);
                userGids2.add(k);
            }
        }
        for (int i = 0; i < userGids2.size(); i++) {
            Integer integer = userGids2.get(i);
            String s = String.valueOf(integer);
            Goods good = goodsMapper.findGoodByGid(s);
            goodsresult.add(good);
        }


        return goodsresult;
    }

    private List<Integer> findUserGoods(User user){
        int s = 0;
        List<Integer> ordersgid = ordersinfoMapper.findGidsByUid(user.getUid());
        List<Integer> wishlistgid = wishlistMapper.findGidsByUid(user.getUid());
        List<Integer> cartgid = cartMapper.findGidsByUid(user.getUid());
        Set<Integer> gids = new HashSet<>();
        for (int i = 0; i < ordersgid.size(); i++) {
            gids.add(ordersgid.get(i));
        }
        for (int i = 0; i < wishlistgid.size(); i++) {
            gids.add(wishlistgid.get(i));
        }
        for (int i = 0; i < cartgid.size(); i++) {
            gids.add(cartgid.get(i));
        }
        List<Integer> gids1 = new ArrayList<>();
        for (Integer gid:gids) {
            gids1.add(gid);
        }
        return gids1;
    }

    private List<GoodsUserScoreBean> findGoodsUserScorde(User user) {
        List<Integer> ordersgid = ordersinfoMapper.findGidsByUid(user.getUid());
        List<Integer> wishlistgid = wishlistMapper.findGidsByUid(user.getUid());
        List<Integer> cartgid = cartMapper.findGidsByUid(user.getUid());
        List<GoodsUserScoreBean> GoodsUserScoreBean = new ArrayList<>();

        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < ordersgid.size(); i++) {
            Integer integer = ordersgid.get(i);
            if(map.get(integer)==null){
                map.put(integer,3);
            }else{
                map.put(integer,map.get(integer)+3);
            }
        }
        for (int i = 0; i < wishlistgid.size(); i++) {
            Integer integer = wishlistgid.get(i);
            if(map.get(integer)==null){
                map.put(integer,1);
            }else{
                map.put(integer,map.get(integer)+1);
            }
        }
        for (int i = 0; i < cartgid.size(); i++) {
            Integer integer = cartgid.get(i);
            if(map.get(integer)==null){
                map.put(integer,2);
            }else{
                map.put(integer,map.get(integer)+2);
            }
        }

        for (Integer gid:map.keySet()) {
            GoodsUserScoreBean bean = new GoodsUserScoreBean();
            Integer integer = map.get(gid);
            bean.setUser(user);
            bean.setScore(integer);
            bean.setGid(gid);
            GoodsUserScoreBean.add(bean);
        }

        return GoodsUserScoreBean;
    }


    private PageInfo<Goods> getPageInfo(PageRequest pageRequest) {
        /**
         * 调用分页插件完成分页
         * @param pageQuery
         * @return
         */
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> goods = goodsMapper.selectPage();
        return new PageInfo<Goods>(goods);
    }
}
