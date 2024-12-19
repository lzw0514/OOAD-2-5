package cn.edu.xmu.oomall.comment.controller.vo;


import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.comment.dao.bo.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({FirstComment.class, AddComment.class,ReplyComment.class})
public class CommentVo {
    private Long id;
    private String content;
    private Byte type; // 0-首评 1-追评 2-商家回复
    private Long creatorId;
    private Long productId;
    private Long shopId;
    private Long parentId; //首评的parentId为NULL,追评的parentId为首评ID，回复的parentId为所回复的评论Id
    private Byte status; //共4种状态 0-待审核 1-通过审核 2-驳回 3-评论不可见 4-被举报待审核
    private LocalDateTime GmtPublish; // 审核通过时间

    public CommentVo(Comment comment) {
        super();
        if (comment instanceof FirstComment)
        {
            CloneFactory.copy(this, (FirstComment)comment);
        }
        else if (comment instanceof AddComment)
        {
            CloneFactory.copy(this, (AddComment)comment);
        }
        else if (comment instanceof ReplyComment)
        {
            CloneFactory.copy(this, (ReplyComment)comment);
        }
        else
         throw new BusinessException(ReturnNo.COMMENT_NOT_TYPE, String.format(ReturnNo.COMMENT_NOT_TYPE.getMessage()));
    }
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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



    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }


    public LocalDateTime getGmtPublish() {
        return GmtPublish;
    }

    public void setGmtPublish(LocalDateTime GmtPublish) {
        this.GmtPublish = GmtPublish;
    }

}
