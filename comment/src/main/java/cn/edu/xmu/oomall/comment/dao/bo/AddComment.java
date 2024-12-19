
package cn.edu.xmu.oomall.comment.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.comment.controller.dto.CommentDto;
import cn.edu.xmu.oomall.comment.dao.CommentDao;
import cn.edu.xmu.oomall.comment.dao.openfeign.OrderItemDao;
import cn.edu.xmu.oomall.comment.dao.openfeign.ShopDao;
import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@ToString(callSuper = true, doNotUseGetters = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@CopyFrom({CommentPo.class, CommentDto.class})
public class AddComment extends Comment {




    /**
     * 审核评论
     * @param approve 审核结果
     * @param rejectReason 驳回原因，通过为NULL
     * @return
     */
    @Override
    public String auditComment(boolean approve, Optional<String> rejectReason, UserDto user)
    {
        if(approve) {
            setStatus(PUBLISHED);
            setGmtPublish(LocalDateTime.now());
            this.setReplyable(true);// 审核通过的评论设为可回复
            FirstComment newFirstComment=(FirstComment)commentDao.findById(parentId);
            newFirstComment.setAddId(id); //将审核通过后的追评Id记录到首评中
            newFirstComment.setAddtionable(false);
            commentDao.save(newFirstComment,user);
        }
        else {
            setStatus(REJECTED);
            this.setReplyable(false);
            setRejectReason(rejectReason.orElse(""));
            // 同时将回复驳回
            if(!Objects.isNull(replyId))
            {
                ReplyComment newReplyComment = (ReplyComment) commentDao.findById(replyId);
                newReplyComment.RelatedMask(newReplyComment, user);
            }
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
        if(Objects.equals(status, PUBLISHED)){
            if(replyable){
                newReplyComment.setParentId(id); // 该回复的parentId为追评id
                newReplyComment.setType((byte)2);
                newReplyComment.setProductId(productId);
                newReplyComment.setShopId(shopId);
                this.setReplyable(false);
                commentDao.save(this,user);
                return (ReplyComment) commentDao.insert(newReplyComment, user);
            }
            else{
                throw new BusinessException(ReturnNo.COMMENT_UPPER_LIMIT, String.format(ReturnNo.COMMENT_UPPER_LIMIT.getMessage()));
            }

        }
        else {
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.COMMENT_NOT_PUBLISHED.getMessage()));
        }
    }


    /**
     * 审核被举报的评论
     * 评论可以被举报，审核通过，将该追评及其下的回复都设为INVISIBLE状态；审核不通过，将该评论设为PUBLISHED状态
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
        }
        else{
            setStatus(PUBLISHED);
        }
        return commentDao.save(this, user);
    }

    /**
     * 关联屏蔽
     * 当首评被成功举报时，关联的追评及追评下的回复也要被屏蔽，设为INVISIBLE状态
     * @param newAddComment
     * @param user
     * @return
     */
    public void RelatedMask(AddComment newAddComment, UserDto user)
    {
        newAddComment.setStatus(INVISIBLE);
        commentDao.save(newAddComment, user);
        if(!Objects.isNull(replyId)){
            ReplyComment newReplyComment = (ReplyComment) commentDao.findById(replyId);
            newReplyComment.RelatedMask(newReplyComment, user);
        }
    }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getContent() { return content; } public void setContent(String content) { this.content = content; }
    public String getRejectReason() { return rejectReason; } public void setRejectReason(String rejectReason) { this.rejectReason = rejectReason; }
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
    public void setReplyComment(Comment replyComment) { ReplyComment = replyComment; }
    public void setShop(Shop shop) { this.shop = shop; }
    public void setOrderItem(OrderItem orderItem) { this.orderItem = orderItem; }
    public void setCommentDao(CommentDao commentDao) { this.commentDao = commentDao; }
    public void setShopDao(ShopDao shopDao) { this.shopDao = shopDao; }
    public void setOrderItemDao(OrderItemDao orderItemDao) { this.orderitemDao = orderItemDao; }
    public Long getParentId() {return parentId;}public void setParentId(Long ParentId) {this.parentId = ParentId;}
    public Long getAddId() {return addId;}
    public void setAddId(Long addId) {this.addId = addId;}
    public LocalDateTime getGmtCreate() {return gmtCreate;}
    public void setGmtCreate(LocalDateTime gmtCreate) {this.gmtCreate = gmtCreate;}
    public LocalDateTime getGmtModified() {return gmtModified;}
    public void setGmtModified(LocalDateTime gmtModified) {this.gmtModified = gmtModified;}
    public String getCreatorName() { return creatorName; } public void setCreatorName(String CreatorName) { this.creatorName=CreatorName; }

}
