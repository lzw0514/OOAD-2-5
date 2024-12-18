//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.dao.bo;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.comment.dao.CommentDao;
import cn.edu.xmu.oomall.comment.dao.openfeign.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;


@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
public abstract class Comment extends OOMallObject implements Serializable {

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

    protected Long PId;//首评的PId为NULL,追评的PId为首评ID，回复的PId为所回复的评论Id

    protected LocalDateTime gmtPublish; // 发布时间，即审核通过时间

    protected Byte status = 0; // 0-待审核 1-通过审核 2-审核驳回

    protected boolean Replyable = false; // 首评和追评只能回复一次



    @JsonIgnore
    @ToString.Exclude
    protected Comment ReplyComment;


    @JsonIgnore
    @ToString.Exclude
    protected Shop shop; // 导航向相关商家

    @JsonIgnore
    @ToString.Exclude
    protected OrderItem orderItem; // 导航向相关订单项

    @JsonIgnore
    @ToString.Exclude
    protected CommentDao commentDao;

    @JsonIgnore
    @ToString.Exclude
    protected ShopDao shopDao;

    @JsonIgnore
    @ToString.Exclude
    protected OrderItemDao orderitemDao ;

    // 无回复评论
    @JsonIgnore
    public static final Long NO_REPLY_COMMENT =0L;

    // 共3种状态 0-待审核 1-通过审核 2-审核驳回
    // 待审核
    @JsonIgnore
    public static final  Byte PENDING = 0;

    // 通过审核
    @JsonIgnore
    public static final  Byte PUBLISHED = 1;

    // 审核驳回
    @JsonIgnore
    public static final  Byte REJECTED  = -1;

    // 状态和名称的对应
    @JsonIgnore
    public static final Map<Byte, String> STATUS_NAMES = new HashMap(){
        {
            put(PUBLISHED, "已发布");
            put(PENDING, "待审核");
            put(REJECTED, "已驳回");
        }
    };

    @JsonIgnore
    public String getStatusName(){
        return STATUS_NAMES.get(this.status);
    }

    // 获取回复或被回复的评论
    @JsonIgnore
    public Comment getReplyComment(){
        if (ReplyComment == null && commentDao != null)
            ReplyComment = commentDao.findById(replyCommentId);
        return ReplyComment;
    }

    // 获取评论相关商家
    @JsonIgnore
    public Shop getShop(){
        if (shopId == null)
            throw new BusinessException(ReturnNo.INCONSISTENT_DATA, String.format(ReturnNo.INCONSISTENT_DATA.getMessage(), "评论", id, "shopId为空"));

        if (shop == null && shopDao != null)
            shop = shopDao.findById(shopId);
        return shop;
    }

    // 获取评论所属订单项
    @JsonIgnore
    public OrderItem getOrderItem(){
        if (orderItemId == null)
            throw new BusinessException(ReturnNo.INCONSISTENT_DATA, String.format(ReturnNo.INCONSISTENT_DATA.getMessage(), "评论", id, "orderItemId为空"));

        if (orderItem == null && orderitemDao != null)
            orderItem = orderitemDao.findById(orderItemId);
        return orderItem;
    }

    // 抽象方法 审核评论
    public abstract String auditComment(boolean approve, Optional<String> rejectReason, UserDto user);
    // 抽象方法 提交回复
    public abstract ReplyComment createReply(Long shopId, ReplyComment newReplyComment, UserDto user);

    // Getter and Setter methods
    public boolean getReplyable () { return Replyable ; } public void setReplyable (boolean Replyable ) { this.Replyable  = Replyable ; }
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
