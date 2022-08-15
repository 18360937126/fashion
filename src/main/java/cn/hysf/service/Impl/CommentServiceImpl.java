package cn.hysf.service.Impl;

import cn.hysf.Page.PageRequest;
import cn.hysf.Page.PageResult;
import cn.hysf.Page.PageUtils;
import cn.hysf.bean.CommentBean;
import cn.hysf.mapper.CommentMapper;
import cn.hysf.mapper.OrdersMapper;
import cn.hysf.mapper.OrdersinfoMapper;
import cn.hysf.mapper.UserMapper;
import cn.hysf.model.Comment;
import cn.hysf.model.Ordersinfo;
import cn.hysf.model.User;
import cn.hysf.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private OrdersinfoMapper ordersinfoMapper;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Integer makeAnComment(String id, String level, String comment) {
        //先查询是否已经评价 这个id就是 comment的ordersinfoId
        Comment c = commentMapper.findIfOrdersInfoIdExist(id);
        if(c!=null) return 0;//表示已经存在
        //如果不存在，先根据ordersinfoId找到ordersinfo信息
        Ordersinfo ordersinfo = ordersinfoMapper.findOrdersInfoById(id);

        Comment comment1 = new Comment();
        comment1.setComment(comment);
        comment1.setGid(ordersinfo.getGid());
        comment1.setLevel(Integer.parseInt(level));
        comment1.setRecordtime(new Date());
        comment1.setUid(ordersinfo.getUid());
        comment1.setOrdersinfoId(String.valueOf(ordersinfo.getId()));
        comment1.setOid(ordersinfo.getOid());

        //添加评论
        Integer count = commentMapper.addComment(comment1);
        return count;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    @Override
    public List<CommentBean> changeCommentForCommentBean(List<Comment> content) {
        List<CommentBean> beans = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            Comment comment = content.get(i);
            CommentBean bean = new CommentBean();
            Integer uid = comment.getUid();
            User user = userMapper.findUserByUid(uid);
            //转换时间格式
            String strDateFormat = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            String format = sdf.format(comment.getRecordtime());

            bean.setDate(format);
            if(comment.getLevel()==1){
                bean.setRecommend("Not Recommend");
            }else if(comment.getLevel()==2){
                bean.setRecommend("Reply");
            }else{
                bean.setRecommend("Recommend");
            }
            bean.setTouxiang(user.getTouxiang());
            bean.setUsername(user.getUsername());

            bean.setComment(comment.getComment());
            bean.setGid(comment.getGid());
            bean.setCid(comment.getCid());
            bean.setLevel(comment.getLevel());
            bean.setUid(comment.getUid());
            bean.setRecordtime(comment.getRecordtime());
            if(comment.getOid()!=null){
                bean.setOid(comment.getOid());
            }
            if(comment.getOrdersinfoId()!=null){
                bean.setOrdersinfoId(comment.getOrdersinfoId());
            }
            beans.add(bean);
        }
        return beans;
    }

    @Override
    public PageResult findPageByGid(PageRequest pageQuery, int gid) {
        return PageUtils.getPageResult(pageQuery, getPageInfoByGid(pageQuery,gid));
    }

    @Override
    public Integer makeAnReply(String cid, String reply,User user) {
        //根据cid查询comment
        Comment comment = commentMapper.findCommentByCid(cid);
        //查找被回复者的用户名
        User beuser = userMapper.findUserByUid(comment.getUid());
        String replycomment = "REPLY FOR,"+beuser.getUsername()+":"+reply;
        Comment comment1 = new Comment();
        comment1.setUid(user.getUid());
        comment1.setRecordtime(new Date());
        comment1.setLevel(2);
        comment1.setGid(comment.getGid());
        comment1.setComment(replycomment);
        //保存
        return commentMapper.saveReplyComment(comment1);
    }

    @Override
    public void deleteCommentByCid(String cid) {
        commentMapper.deleteCommentByCid(cid);
    }


    private PageInfo<Comment> getPageInfo(PageRequest pageRequest) {
        /**
         * 调用分页插件完成分页
         * @param pageQuery
         * @return
         */
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.selectPage();
        return new PageInfo<Comment>(comments);
    }

    private PageInfo<Comment> getPageInfoByGid(PageRequest pageRequest,int gid) {
        /**
         * 调用分页插件完成分页
         * @param pageQuery
         * @return
         */
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.selectPageByGid(gid);
        return new PageInfo<Comment>(comments);
    }
}
