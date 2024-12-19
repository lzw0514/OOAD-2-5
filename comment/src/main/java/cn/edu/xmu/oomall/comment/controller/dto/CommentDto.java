//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.controller.dto;

import cn.edu.xmu.javaee.core.validation.NewGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

/**
 * 评论DTO对象
 * @author Liuzhiwem
 **/
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {

    @NotBlank(message = "评论内容不能为空", groups = {NewGroup.class})
    protected String content;

    protected String rejectReason;

    protected Byte type; // 0-首评 1-追评 2-商家回复

    protected Long replyId;

    protected Long addId;    //追评Id

    protected Long parentId;   //首评的parentId为NULL,追评的parentId为首评ID，回复的parentId为所回复的评论Id

    protected Long creatorId;

    protected Long orderitemId;

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

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public Long getAddId() {
        return addId;
    }

    public void setAddId(Long addId) {
        this.addId = addId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getOrderitemId() {
        return orderitemId;
    }

    public void setOrderitemId(Long orderitemId) {
        this.orderitemId = orderitemId;
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
