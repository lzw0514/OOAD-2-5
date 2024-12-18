//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.controller.dto;

import cn.edu.xmu.javaee.core.validation.NewGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;

/**
 * 评论DTO对象
 * @author Shuyang Xing
 **/
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {

    @NotBlank(message = "评论内容不能为空", groups = {NewGroup.class})
    protected String content;

    protected String rejectReason;

    protected Byte type; // 1-首评 2-追评 3-商家回复

    protected Long replyCommentId;

    protected Long addCommentId;

    protected Long PId;   //首评的PId为NULL,追评的PId为首评ID，回复的PId为所回复的评论Id

    protected Long creatorId;

    protected Long orderItemId;

    protected Long productId;

    protected Long shopId;

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

}
