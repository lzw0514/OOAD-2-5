package cn.edu.xmu.oomall.comment.controller.vo;


import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.model.vo.IdNameTypeVo;
import cn.edu.xmu.oomall.comment.dao.bo.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;

/**
 * @author Shuyang Xing
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({FirstComment.class, ReplyComment.class})
public class CommentVo {
    private Long id;
    private String content;
    private Byte type; // 1-首评 2-商家回复
    private Integer rating; // 评分，非首评时为null
    private Integer likes;
    private Long creatorId;
    private Long productId;
    private Long shopId;
    private Long replyCommentId; // 如果是首评，此属性为回复评论Id，如果是回复，此属性为首评Id
    private Byte status; // 0-待审核 1-通过审核 2-审核驳回
    private boolean canReply; // 首评只能回复一次，回复不能回复
    private LocalDateTime gmtPublish; // 发布时间，即审核通过时间

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
}
