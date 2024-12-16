//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.SnowFlakeIdWorker;
import cn.edu.xmu.oomall.comment.dao.*;
import cn.edu.xmu.oomall.comment.dao.bo.*;
import cn.edu.xmu.oomall.comment.dao.openfeign.OrderDao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
public class CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    private final CommentDao commentDao;
    private final OrderDao orderDao;

    // 根据Id查找评论
    public Comment findCommentById(Long id) throws BusinessException {
        logger.debug("findCommentById: id = {}", id);
        Comment bo = null;
        try {
            bo = this.commentDao.findCommentById(id);
        } catch (BusinessException e) {
            throw e;
        }
        return bo;
    }

    // 获取某一商品的评论
    public List<Comment> retrieveCommentByProduct(Long productId, Integer page, Integer pageSize) {
        logger.debug("retrieveComments: productId = {}", productId);
        return this.commentDao.retrieveCommentByProduct(productId, page, pageSize);
    }

    // 顾客撰写评论
    public FirstComment createComment(Long orderItemId, FirstComment firstComment, UserDto user) {
        OrderItem orderItem = orderDao.findOrderItemById(orderItemId);
        if (orderItem == null) {
            throw new BusinessException(ReturnNo.ORDERITEM_NOT_FOUND, String.format(ReturnNo.ORDERITEM_NOT_FOUND.getMessage()));
        }
        orderItem.setCommentDao(commentDao);
        return orderItem.createComment(firstComment, user);
    }

    // 商户回复评论
    public ReplyComment createReply(Long shopId, Long commentId, ReplyComment replyComment, UserDto user) {
        FirstComment firstComment = (FirstComment) commentDao.findCommentById(commentId);
        if (firstComment == null || firstComment.getType() == 2) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "首评", commentId));
        }
        firstComment.setCommentDao(commentDao);
        return firstComment.createReply(shopId, replyComment, user);
    }

    // 管理员审核评论
    public String auditReply(Long commentId, boolean isApproved, String rejectReason, UserDto user) {
        Comment comment = commentDao.findCommentById(commentId);
        if (comment == null) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "评论", commentId));
        }
        if (comment instanceof FirstComment)
        {
            FirstComment firstComment = (FirstComment) comment;
            firstComment.setCommentDao(commentDao);
            return firstComment.auditComment(isApproved, Optional.ofNullable(rejectReason), user);
        }else if (comment instanceof ReplyComment) {
            ReplyComment replyComment = (ReplyComment) comment;
            replyComment.setCommentDao(commentDao);
            return replyComment.auditComment(isApproved, Optional.ofNullable(rejectReason), user);
        }else{
            throw new IllegalArgumentException("Unknown comment type");
        }
    }

    // 点赞评论
    public String likeReply(Long commentId, int num, UserDto user) {
        Comment comment = commentDao.findCommentById(commentId);
        if (comment instanceof FirstComment)
        {
            FirstComment firstComment = (FirstComment) comment;
            firstComment.setCommentDao(commentDao);
            return firstComment.likeComment(num, user);
        }else {
            throw new BusinessException(ReturnNo.COMMENT_NOT_LIKEABLE, String.format(ReturnNo.COMMENT_NOT_LIKEABLE.getMessage()));
        }
    }
}
