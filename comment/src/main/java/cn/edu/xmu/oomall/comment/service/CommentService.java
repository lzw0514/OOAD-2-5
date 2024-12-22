//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.comment.dao.*;
import cn.edu.xmu.oomall.comment.dao.bo.*;
import cn.edu.xmu.oomall.comment.dao.openfeign.OrderItemDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static cn.edu.xmu.oomall.comment.dao.bo.Comment.INVISIBLE;
import static cn.edu.xmu.oomall.comment.dao.bo.Comment.REPORTED_FOR_REVIEW;


@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
public class CommentService {


    private final CommentDao commentDao;
    private final OrderItemDao orderitemDao;



    /**
     * 查询商品的全部评论
     * author Liuzhiwen
     * @param productId
     * @param page
     * @param pageSize
     * @return
     */
    public List<Comment> retrieveProductComments(Long productId, Integer page, Integer pageSize) {
        log.debug("retrieveComments: productId = {}", productId);
        return this.commentDao.retrieveProductComments(productId, page, pageSize);
    }

    /**
     * 根据Id查找评论,所有状态评论都可查到
     * author Wuyuzhu
     * @param id
     * @return
     */

    public Comment findCommentById(Long id) throws BusinessException {
        log.debug("findCommentById: id = {}", id);
        return this.commentDao.findById(id);
    }

    /**
     * 根据Id查找评论,PUBILISHED状态和REPORTED_FOR_REVIEW评论才可被查到
     * author Wuyuzhu
     * @param id
     * @return
     */
    public Comment findValidCommentById(Long id) throws BusinessException {
        log.debug("findValidCommentById: id = {}", id);
        return this.commentDao.findValidCommenntById(id);
    }


    /**
     * 顾客创建首评
     * author Liuzhiwen
     * @param orderItemId
     * @param firstComment
     * @return
     */

    public FirstComment createComment(Long orderItemId, FirstComment firstComment, UserDto user) {
        OrderItem orderItem = orderitemDao.findById(orderItemId);
        if(!Objects.equals(user.getId(),orderItem.getCustomerId()))
        {
            throw new BusinessException(ReturnNo.AUTH_NO_RIGHT, String.format(ReturnNo.AUTH_NO_RIGHT.getMessage()));
        }
        return orderItem.createComment(firstComment, user);
    }

    /**
     * 顾客创建追评
     * author Liuzhiwen
     * @param commentId
     * @param addComment
     * @return
     */

    public AddComment createAddComment(Long commentId, AddComment addComment, UserDto user) {
        FirstComment firstComment = (FirstComment)commentDao.findById(commentId);
        if(!Objects.equals(user.getId(),firstComment.getCreatorId()))
        {
            throw new BusinessException(ReturnNo.AUTH_NO_RIGHT, String.format(ReturnNo.AUTH_NO_RIGHT.getMessage()));
        }
        return firstComment.createAddComment(commentId,addComment, user);
    }


     /**
     * 商户回复评论
     * author Wuyuzhu
     * @param shopId
     * @param replyComment
     * @return
     */
    public ReplyComment createReply(Long shopId, Long commentId, ReplyComment replyComment, UserDto user) {
        Comment comment = commentDao.findById(commentId);
        if(!Objects.equals(shopId, comment.getShopId())){
            throw new BusinessException(ReturnNo.AUTH_NO_RIGHT, String.format(ReturnNo.AUTH_NO_RIGHT.getMessage()));
        }
        return comment.createReply(shopId, replyComment, user);
    }


    /**
     * 顾客提交举报评论
     * author Liuzhiwen
     * @param commentId
     * @param reportReason
     * @return
     */

    public void reportComment(Long commentId, String reportReason, UserDto user) {
        Comment comment = commentDao.findById(commentId);
        comment.setReportReason(reportReason);
        comment.setStatus(INVISIBLE);
        commentDao.save(comment, user);
    }

    /**
     * 管理员审核评论
     * author Wuyuzhu
     * @param commentId
     * @param isApproved
     * @param rejectReason
     * @return
     */

    public String auditComment(Long commentId, boolean isApproved, String rejectReason, UserDto user) {
        Comment comment = commentDao.findById(commentId);
        return comment.auditComment(isApproved, Optional.ofNullable(rejectReason), user);
    }

    /**
     * 管理员审核举报的评论
     * author Liuzhiwen
     * @param commentId
     * @param isApproved
     * @return
     */

    public String auditReport(Long commentId, boolean isApproved,  UserDto user) {
        Comment comment = commentDao.findById(commentId);
        if(!REPORTED_FOR_REVIEW.equals(comment.getStatus())) {
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "评论", comment.getStatus()));
        }
        return comment.auditReport(isApproved, user);
    }




}
