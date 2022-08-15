package cn.hysf.mapper;

import cn.hysf.model.Comment;
import cn.hysf.model.CommentKey;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentMapper {
    Comment findCommentByCid(String cid);

    Comment findIfOrdersInfoIdExist(String id);

    Integer addComment(Comment comment1);

    Integer saveReplyComment(Comment comment1);

    void deleteCommentByCid(String cid);

    List<Comment> selectPage();

    List<Comment> selectPageByGid(int gid);
}