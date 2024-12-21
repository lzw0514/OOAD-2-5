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
public class ReplyComment extends Comment {



    /**
     * 审核回复
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
            Comment originComment = commentDao.findById(parentId);      // 找到回复评论对应的首评或者追评
            originComment.setReplyable (false);
            originComment.setReplyId(id);
            originComment.commentDao.save(originComment, user);
        }
        else {
            setStatus(REJECTED);
            setRejectReason(rejectReason.orElse(""));
        }
        this.setReplyable(false); // 回复评论始终不可回复

        return commentDao.save(this, user);
    }

    /**
     * 审核被举报的回复
     * 商家回复可以被举报，审核通过，将该回复设为INVISIBLE状态；审核不通过，将该回复设为PUBLISHED状态
     * @param approve 审核状态
     * @return
     */
    @Override
    public String auditReport(boolean approve, UserDto user)
    {
        if(approve) {
            setStatus(INVISIBLE);
        }
        else{
            setStatus(PUBLISHED);
        }
        return commentDao.save(this, user);
    }
    /**
     * 关联屏蔽
     * 当评论或追评被成功举报时，其下面关联的回复也要被屏蔽，设为INVISIBLE状态
     * @param newReplyComment
     * @param user
     * @return
     */
   public void RelatedMask(ReplyComment newReplyComment, UserDto user)
   {
       newReplyComment.setStatus(INVISIBLE);
       commentDao.save(newReplyComment, user);
   }


    @Override
    public ReplyComment createReply(Long shopId, ReplyComment newReplyComment, UserDto user)
    {
        throw new BusinessException(ReturnNo.COMMENT_NOT_RETURNABLE , String.format(ReturnNo.COMMENT_NOT_RETURNABLE .getMessage()));

    }

    public boolean getReplyable () { return replyable; } public void setReplyable (boolean Replyable ) { this.replyable = Replyable ; }
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
    public void setReplyComment(Comment replyComment) { ReplyComment = replyComment; }
    public void setShop(Shop shop) { this.shop = shop; }
    public void setOrderItem(OrderItem orderItem) { this.orderItem = orderItem; }
    public void setCommentDao(CommentDao commentDao) { this.commentDao = commentDao; }
    public void setShopDao(ShopDao shopDao) { this.shopDao = shopDao; }
    public void setOrderItemDao(OrderItemDao orderItemDao) { this.orderitemDao = orderItemDao; }
    public Long getParentId() {return parentId;}public void setParentId(Long addPId) {this.parentId = addPId;}
    public Long getAddId() {return addId;}
    public void setAddId(Long addId) {this.addId = addId;}
    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    public LocalDateTime getGmtModified() {
        return gmtModified;
    }
    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
    public String getCreatorName() { return creatorName; } public void setCreatorName(String CreatorName) { this.creatorName=CreatorName; }

}
