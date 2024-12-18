package cn.edu.xmu.oomall.comment.controller.vo;


import cn.edu.xmu.javaee.core.aop.CopyFrom;
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
    private Byte type; // 1-首评 2-追评 3-商家回复
    private Long creatorId;
    private Long productId;
    private Long shopId;
    private Long PId; //首评的PId为NULL,追评的PId为首评ID，回复的PId为所回复的评论Id
    private Byte status; // 0-待审核 1-通过审核 2-审核驳回
    private boolean Replyable; // 首评和追评只能回复一次，回复不能回复
    private LocalDateTime createtime; // 审核通过时间

    public CommentVo(Comment comment) {
        super();
        if (comment instanceof FirstComment)
        {
            CloneFactory.copy(this, (FirstComment)comment);
        }else if (comment instanceof ReplyComment)
        {
            CloneFactory.copy(this, (ReplyComment)comment);
        }else {
            throw new IllegalArgumentException("Unknown comment type");
        }
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

    public boolean isReplyable() {
        return Replyable;
    }

    public void setReplyable(boolean Replyable) {
        this.Replyable= Replyable;
    }

    public LocalDateTime getcreatetime() {
        return createtime;
    }

    public void setGmtPublish(LocalDateTime createtime) {
        this.createtime = createtime;
    }

}
