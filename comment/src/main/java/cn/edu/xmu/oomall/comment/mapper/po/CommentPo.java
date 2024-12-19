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

    protected Byte type; // 0-首评 1-追评 2-商家回复

    protected Long creatorId;

    protected String creatorName;

    protected Long orderitemId;

    protected Long reviewerId;

    protected Long productId;

    protected Long shopId;

    protected Long replyId;

    protected Long addId;

    protected Long parentId;  //首评的parentId为NULL,追评的parentId为首评ID，回复的parentId为所回复的评论Id

    protected LocalDateTime gmtPublish; // 发布时间，即审核通过时间

    protected Byte status = 0;  // 共4种状态 0-待审核 1-通过审核 2-驳回 3-评论不可见 4-被举报待审核

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    protected boolean replyable = false;

    protected boolean addtionable = false;

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

    public String getCreatorName() {
        return creatorName;
    }


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

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
    public Long getOrderitemId() {
        return orderitemId;
    }

    public void setOrderitemId(Long orderItemId) {
        this.orderitemId = orderItemId;
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

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyCommentId) {
        this.replyId = replyCommentId;
    }

    public Long getAddId() {
        return addId;
    }

    public void setAddId(Long addCommentId) {
        this.addId = addCommentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long PId) {
        this.parentId = PId;
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

    public boolean getReplyable() {
        return replyable;
    }

    public void setReplyable(boolean Replyable) {
        replyable = Replyable;
    }

    public boolean getAddtionable() {
        return addtionable;
    }

    public void setAddtionable(boolean Addtionable) {
        addtionable = Addtionable;
    }


}
