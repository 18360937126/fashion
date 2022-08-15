package cn.hysf.mapper;

import cn.hysf.model.Goods;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GoodsMapper {
    List<Goods> findAllGoods();

    Goods findGoodByGid(String gid);

    List<Goods> findGoodsByKind(String gkind);

    List<Goods> findManTypeGoods();

    List<Goods> findWomanTypeGoods();

    List<Goods> findOtherTypeGoods();

    List<Goods> findUnder100Goods();

    void changeGoodStatusTo1(String gid);

    void changeGoodStatusTo0(String gid);

    void addGoods(Goods good);

    List<Goods> selectPage();

    Goods findGoodByGid2(Integer gid);
}