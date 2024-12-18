//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
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
     * 根据Id查找评论
     * author Wuyuzhu
     * @param id
     * @return
     */

    public Comment findCommentById(Long id) throws BusinessException {
        log.debug("findCommentById: id = {}", id);
        return this.commentDao.findById(id);
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
        return comment.createReply(shopId, replyComment, user);
    }


    /**
     * 管理员审核评论
     * author Wuyuzhu
     * @param commentId
     * @param isApproved
     * @param rejectReason
     * @return
     */

    public String auditReply(Long commentId, boolean isApproved, String rejectReason, UserDto user) {
        Comment comment = commentDao.findById(commentId);
        return comment.auditComment(isApproved, Optional.ofNullable(rejectReason), user);
    }


}
