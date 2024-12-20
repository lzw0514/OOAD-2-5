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

    protected Byte type; // 0-首评 1-追评 2-商家回复

    protected Long creatorId;

    protected String creatorName;

    protected Long orderitemId;

    protected Long reviewerId;

    protected Long productId;

    protected Long shopId;

    protected Long replyId;

    protected Long addId;

    protected Long parentId;//首评的parentId为NULL,追评的parentId为首评ID，回复的parentId为所回复的评论Id

    protected LocalDateTime gmtPublish; // 发布时间，即审核通过时间

    protected Byte status = 0; // 0-待审核 1-通过审核 2-审核驳回 3-评论不可见

    protected boolean replyable = false; // 首评和追评只能回复一次



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

    // 共4种状态 0-待审核 1-通过审核 2-驳回 3-评论不可见 4-被举报待审核
    // 待审核
    @JsonIgnore
    public static final  Byte PENDING = 0;

    // 通过审核
    @JsonIgnore
    public static final  Byte PUBLISHED = 1;

    // 驳回
    @JsonIgnore
    public static final  Byte REJECTED  = 2;

    // 评论不可见
    @JsonIgnore
    public static final  Byte INVISIBLE   = 3;

    // 评论被举报待审核
    @JsonIgnore
    public static final  Byte REPORTED_FOR_REVIEW   = 4;

    // 状态和名称的对应
    @JsonIgnore
    public static final Map<Byte, String> STATUS_NAMES = new HashMap(){
        {
            put(PUBLISHED, "已发布");
            put(PENDING, "待审核");
            put(REJECTED, "已驳回");
            put(INVISIBLE,"不可见");
            put(REPORTED_FOR_REVIEW,"被举报待审核");
        }
    };


    public abstract String auditComment(boolean approve, Optional<String> rejectReason, UserDto user);

    public abstract ReplyComment createReply(Long shopId, ReplyComment newReplyComment, UserDto user);

    public abstract String auditReport(boolean approve, UserDto user);

    public abstract Long getId();
    public abstract void setId(Long id);

    public abstract String getContent();
    public abstract void setContent(String content);

    public abstract String getRejectReason();
    public abstract void setRejectReason(String rejectReason);

    public abstract Byte getType();
    public abstract void setType(Byte type);

    public abstract Long getCreatorId();
    public abstract void setCreatorId(Long creatorId);

    public abstract String getCreatorName();
    public abstract void setCreatorName(String CreatorName);

    public abstract Long getOrderitemId();
    public abstract void setOrderitemId(Long orderitemId);

    public abstract Long getReviewerId();
    public abstract void setReviewerId(Long reviewerId);

    public abstract Long getProductId();
    public abstract void setProductId(Long productId);

    public abstract Long getShopId();
    public abstract void setShopId(Long shopId);

    public abstract Long getReplyId();
    public abstract void setReplyId(Long replyId);

    public abstract LocalDateTime getGmtPublish();
    public abstract void setGmtPublish(LocalDateTime gmtPublish);

    public abstract Byte getStatus();
    public abstract void setStatus(Byte status);

    public abstract Long getParentId();
    public abstract void setParentId(Long parentId);

    public abstract Long getAddId();
    public abstract void setAddId(Long addId);

    public abstract boolean getReplyable();
    public abstract void setReplyable(boolean replyable);

    public abstract void setReplyComment(Comment replyComment);
    public abstract void setShop(Shop shop);
    public abstract void setOrderItem(OrderItem orderItem);
    public abstract void setCommentDao(CommentDao commentDao);
    public abstract void setShopDao(ShopDao shopDao);
    public abstract void setOrderItemDao(OrderItemDao orderItemDao);
    public abstract void setGmtCreate(LocalDateTime gmtCreate);

    public abstract LocalDateTime getGmtCreate();

    public abstract void setGmtModified(LocalDateTime gmtModified);

    public abstract LocalDateTime getGmtModified();


}
