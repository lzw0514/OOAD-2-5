//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.comment.dao.bo.*;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({FirstComment.class,AddComment.class, ReplyComment.class})
public class CommentPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String content;

    protected String rejectReason;

    protected Byte type; // 1-首评 2-追评 3-商家回复

    protected Long creatorId;

    protected Long orderItemId;

    protected Long reviewerId;

    protected Long productId;

    protected Long shopId;

    protected Long replyCommentId;

    protected Long addCommentId;

    protected Long PId;  //首评的PId为NULL,追评的PId为首评ID，回复的PId为所回复的评论Id

    protected LocalDateTime gmtPublish; // 发布时间，即审核通过时间

    protected Byte status = 0; // 0-待审核 1-通过审核 2-审核驳回

    protected boolean Replyable = false;

    protected boolean Addtionable = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getReplyCommentId() {
        return replyCommentId;
    }

    public void setReplyCommentId(Long replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public Long getAddCommentId() {
        return addCommentId;
    }

    public void setAddCommentId(Long addCommentId) {
        this.addCommentId = addCommentId;
    }

    public Long getPId() {
        return PId;
    }

    public void setPId(Long PId) {
        this.PId = PId;
    }

    public LocalDateTime getGmtPublish() {
        return gmtPublish;
    }

    public void setGmtPublish(LocalDateTime gmtPublish) {
        this.gmtPublish = gmtPublish;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public boolean isReplyable() {
        return Replyable;
    }

    public void setReplyable(boolean replyable) {
        Replyable = replyable;
    }

    public boolean isAddtionable() {
        return Addtionable;
    }

    public void setAddtionable(boolean addtionable) {
        Addtionable = addtionable;
    }


}
