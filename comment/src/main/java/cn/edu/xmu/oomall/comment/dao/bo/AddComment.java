
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
     * 追评被驳回时，要修改被追评的Addtionable状态；追评可以被举报，举报驳回时要同时驳回该追评下的回复
     * @param approve 审核结果
     * @param rejectReason 驳回原因，通过为NULL
     * @return
     */
    @Override
    public String auditComment(boolean approve, Optional<String> rejectReason, UserDto user)
    {
        if(approve) {
            setStatus(PUBLISHED);
            setReplyable (true);// 审核通过的评论设为可回复
            FirstComment newFirstComment=(FirstComment)commentDao.findById(PId);
            newFirstComment.setAddCommentId(id); //将审核通过后的追评Id记录到首评中
            newFirstComment.setAddtionable(false);
            commentDao.save(newFirstComment,user);
        }
        else {
            setStatus(REJECTED);
            setReplyable (false);
            setRejectReason(rejectReason.orElse(""));
            // 同时将回复驳回
            if(!Objects.isNull(replyCommentId))
            {
                ReplyComment newReplyComment = (ReplyComment) commentDao.findById(replyCommentId);
                newReplyComment.RelatedReject(newReplyComment, user);
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
        if(!Objects.equals(shopId, this.shopId))
            throw new BusinessException(ReturnNo.AUTH_NO_RIGHT, String.format(ReturnNo.AUTH_NO_RIGHT.getMessage()));
        if(Objects.equals(status, PUBLISHED) && Replyable )
        {
            newReplyComment.setPId(id); // 该回复的PId为首评id
            setReplyable(false);
            commentDao.save(this,user);
            return (ReplyComment) commentDao.insert(newReplyComment, user);
        }
        else {
            throw new BusinessException(ReturnNo.FIELD_NOTVALID,"回复数已超过最大值");
        }
    }

    /**
     * 关联驳回
     * 当评论驳回时，关联的追评及追评下的回复也要被驳回
     * @param newAddComment
     * @param user
     * @return
     */
    public void RelatedReject(AddComment newAddComment, UserDto user)
    {
        newAddComment.setStatus(REJECTED);
        setReplyable(false);
        newAddComment.setRejectReason("原评论被驳回");
        commentDao.save(newAddComment, user);
        if(!Objects.isNull(replyCommentId))
        {
            ReplyComment newReplyComment = (ReplyComment) commentDao.findById(replyCommentId);
            newReplyComment.RelatedReject(newReplyComment, user);
        }
    }


    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {}

    @Override
    public void setGmtModified(LocalDateTime gmtModified) {}


    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getContent() { return content; } public void setContent(String content) { this.content = content; }
    public String getRejectReason() { return rejectReason; } public void setRejectReason(String rejectReason) { this.rejectReason = rejectReason; }
    public Byte getType() { return type; } public void setType(Byte type) { this.type = type; }
    public Long getCreatorId() { return creatorId; } public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }
    public Long getOrderItemId() { return orderItemId; } public void setOrderItemId(Long orderItemId) { this.orderItemId = orderItemId; }
    public Long getReviewerId() { return reviewerId; } public void setReviewerId(Long reviewerId) { this.reviewerId = reviewerId; }
    public Long getProductId() { return productId; } public void setProductId(Long productId) { this.productId = productId; }
    public Long getShopId() { return shopId; } public void setShopId(Long shopId) { this.shopId = shopId; }
    public Long getReplyCommentId() { return replyCommentId; } public void setReplyCommentId(Long replyCommentId) { this.replyCommentId = replyCommentId; }
    public LocalDateTime getGmtPublish() { return gmtPublish; } public void setGmtPublish(LocalDateTime gmtPublish) { this.gmtPublish = gmtPublish; }
    public Byte getStatus() { return status; } public void setStatus(Byte status) { this.status = status; }
    public boolean getReplyable () { return Replyable ; } public void setReplyable (boolean Replyable ) { this.Replyable  = Replyable ; }
    public void setReplyComment(Comment replyComment) { ReplyComment = replyComment; }
    public void setShop(Shop shop) { this.shop = shop; }
    public void setOrderItem(OrderItem orderItem) { this.orderItem = orderItem; }
    public void setCommentDao(CommentDao commentDao) { this.commentDao = commentDao; }
    public void setShopDao(ShopDao shopDao) { this.shopDao = shopDao; }
    public void setOrderItemDao(OrderItemDao orderItemDao) { this.orderitemDao = orderItemDao; }
    public Long getPId() {return PId;}public void setPId(Long addPId) {this.PId = addPId;}
    public Long getAddCommentId() {return addCommentId;}
    public void setAddCommentId(Long addCommentId) {this.addCommentId = addCommentId;}
}
