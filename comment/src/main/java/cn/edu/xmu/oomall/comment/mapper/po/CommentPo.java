//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.comment.dao.bo.*;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({FirstComment.class, ReplyComment.class})
public class CommentPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String content;

    protected String rejectReason;

    protected Byte type; // 1-首评 2-商家回复

    private Byte rating;

    private Integer likes;

    protected Long creatorId;

    protected Long orderItemId;

    protected Long reviewerId;

    protected Long productId;

    protected Long shopId;

    protected Long replyCommentId;// 如果是首评，此属性为回复评论Id，如果是回复，此属性为首评Id

    protected LocalDateTime gmtPublish; // 发布时间，即审核通过时间

    protected Byte status = 0; // 0-待审核 1-通过审核 2-审核驳回

    protected boolean canReply = false;
}
