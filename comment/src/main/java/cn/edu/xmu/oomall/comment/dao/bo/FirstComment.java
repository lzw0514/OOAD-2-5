package cn.edu.xmu.oomall.comment.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.comment.controller.dto.CommentDto;
import cn.edu.xmu.oomall.comment.dao.CommentDao;
import cn.edu.xmu.oomall.comment.dao.openfeign.OrderItemDao;
import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;


/**
 * @author Liuzhiwen
 */

@ToString(callSuper = true, doNotUseGetters = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@CopyFrom({CommentPo.class, CommentDto.class})
@Slf4j
public class FirstComment extends Comment {


    protected boolean addtionable;


    /**
     * 审核评论
     * @param approve 审核状态
     * @param rejectReason   驳回原因
     * @return
     */
    @Override
    public String auditComment(boolean approve, Optional<String> rejectReason, UserDto user)
    {
        if(approve) {
            setStatus(PUBLISHED);
            setGmtPublish(LocalDateTime.now());
            setReplyable(true);// 审核通过的评论设为可回复
            setAddtionable(true);// 审核通过的评论设为可追评
        }
        else {
            setStatus(REJECTED);
            setReplyable(false);
            setAddtionable(false);
            setRejectReason(rejectReason.orElse(""));
        }
        return commentDao.save(this, user);
    }

    /**
     * 审核被举报的评论
     * 评论可以被举报，审核通过，将该评论及其下的追评和回复都设为INVISIBLE状态；审核不通过，将该评论设为PUBLISHED状态
     * @param approve 审核状态
     * @return
     */
    @Override
    public String auditReport(boolean approve, UserDto user)
    {
        if(approve) {
            setStatus(INVISIBLE);
            // 同时将回复设为不可见
            if(!Objects.isNull(replyId)){
                ReplyComment newReplyComment = (ReplyComment) commentDao.findById(replyId);
                newReplyComment.RelatedMask(newReplyComment, user);
            }
            //同时将追评设为不可见
            if(!Objects.isNull(addId)){
                AddComment newaddComment=(AddComment) commentDao.findById(addId);
                newaddComment.RelatedMask(newaddComment,user);
            }
        }
        else{
            setStatus(PUBLISHED);
        }
        return commentDao.save(this, user);
    }

    /**
     * 回复评论
     * 业务需要判断当前评论状态，且商家只能回复自己店铺的评论，一个评论只能回复一次
     * @param shopId
     * @param newReplyComment
     * @return
     */
    public ReplyComment createReply(Long shopId, ReplyComment newReplyComment, UserDto user)
    {
        if(Objects.equals(status, PUBLISHED))
        {
            if (replyable) {
                        newReplyComment.setParentId(id); // 该回复的parentId为首评id
                        newReplyComment.setType((byte) 2);
                        this.setReplyable(false);
                        newReplyComment.setProductId(productId);
                        newReplyComment.setShopId(shopId);
                        newReplyComment.setCreatorId(user.getId());
                        commentDao.save(this,user);
                        return (ReplyComment) commentDao.insert(newReplyComment, user);
                    }
            else
            {
                throw new BusinessException(ReturnNo.COMMENT_UPPER_LIMIT, String.format(ReturnNo.COMMENT_UPPER_LIMIT.getMessage()));
            }

        }
        else {
            throw new BusinessException(ReturnNo.COMMENT_NOT_PUBLISHED, String.format(ReturnNo.COMMENT_NOT_PUBLISHED.getMessage()));
        }
    }



    /**
     * 创建追评
     * 业务需要判断当前评论状态，一个订单项只能追评一次
     * @param Id
     * @param newAddComment
     * @return
     */
    public AddComment createAddComment(Long Id, AddComment newAddComment, UserDto user)
    {

        if(Objects.equals(status, PUBLISHED) )
        {
            if(addtionable)
            {
                newAddComment.setParentId(id);
                newAddComment.setProductId(productId);
                newAddComment.setShopId(shopId);
                newAddComment.setType((byte)1);
                newAddComment.setCreatorId(user.getId());
                setAddtionable(false);
                commentDao.save(this,user);
                return (AddComment) commentDao.insert(newAddComment, user);
            }
           else
            {
                throw new BusinessException(ReturnNo.COMMENT_UPPER_LIMIT, String.format(ReturnNo.COMMENT_UPPER_LIMIT.getMessage()));
            }

        }
        else {
            throw new BusinessException(ReturnNo.COMMENT_NOT_PUBLISHED, String.format(ReturnNo.COMMENT_NOT_PUBLISHED.getMessage()));
        }
    }


    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getContent() { return content; } public void setContent(String content) { this.content = content; }
    public String getRejectReason() { return rejectReason; } public void setRejectReason(String rejectReason) { this.rejectReason = rejectReason; }

    public String getReportReason() { return reportReason; } public void setReportReason(String reportReason) { this.reportReason = reportReason; }
    public Byte getType() { return type; } public void setType(Byte type) { this.type = type; }
    public Long getCreatorId() { return creatorId; } public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }
    public Long getOrderitemId() { return orderitemId; } public void setOrderitemId(Long orderitemId) { this.orderitemId = orderitemId; }
    public Long getReviewerId() { return reviewerId; } public void setReviewerId(Long reviewerId) { this.reviewerId = reviewerId; }
    public Long getProductId() { return productId; } public void setProductId(Long productId) { this.productId = productId; }
    public Long getShopId() { return shopId; } public void setShopId(Long shopId) { this.shopId = shopId; }
    public Long getReplyId() { return replyId; } public void setReplyId(Long replyId) { this.replyId = replyId; }
    public LocalDateTime getGmtPublish() { return gmtPublish; } public void setGmtPublish(LocalDateTime gmtPublish) { this.gmtPublish = gmtPublish; }
    public Byte getStatus() { return status; } public void setStatus(Byte status) { this.status = status; }
    public boolean getReplyable () { return replyable; } public void setReplyable (boolean Replyable ) { this.replyable = Replyable ; }
    public boolean getAddtionable() { return addtionable; } public void setAddtionable(boolean Addtionable ) { this.addtionable = Addtionable ; }
    public void setReplyComment(Comment replyComment) { ReplyComment = replyComment; }
    public void setOrderItem(OrderItem orderItem) { this.orderItem = orderItem; }
    public void setCommentDao(CommentDao commentDao) { this.commentDao = commentDao; }
    public void setOrderItemDao(OrderItemDao orderItemDao) { this.orderitemDao = orderItemDao; }
    public Long getParentId() {return parentId;}public void setParentId(Long addPId) {this.parentId = addPId;}
    public Long getAddId() {return addId;}public void setAddId(Long addId) {this.addId = addId;}
    public LocalDateTime getGmtCreate() {return gmtCreate;}
    public void setGmtCreate(LocalDateTime gmtCreate) {this.gmtCreate = gmtCreate;}
    public LocalDateTime getGmtModified() {return gmtModified;}
    public void setGmtModified(LocalDateTime gmtModified) {this.gmtModified = gmtModified;}
    public String getCreatorName() { return creatorName; } public void setCreatorName(String CreatorName) { this.creatorName=CreatorName; }
}
