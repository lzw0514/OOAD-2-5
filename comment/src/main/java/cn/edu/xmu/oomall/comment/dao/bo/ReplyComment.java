package cn.edu.xmu.oomall.comment.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.comment.controller.dto.CommentDto;
import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.*;

@ToString(callSuper = true, doNotUseGetters = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@CopyFrom({CommentPo.class, CommentDto.class})
public class ReplyComment extends Comment {

    private static final Logger logger = LoggerFactory.getLogger(FirstComment.class);

    @Override
    public String auditComment(boolean approve, Optional<String> rejectReason, UserDto user)
    {
        // 找到回复评论对应的首评
        Comment originComment = commentDao.findCommentById(replyCommentId);
        if (originComment == null)
            throw new BusinessException(ReturnNo.COMMENT_NOT_FIND, "原评论不存在");
        // 如果之前已经通过了一条回复，则当前不允许回复
        if (!originComment.getCanReply())
            throw new BusinessException(ReturnNo.COMMENT_NOT_RETURNABLE, String.format(ReturnNo.COMMENT_NOT_RETURNABLE.getMessage(), this.id));


        ReplyComment newReplyComment = new ReplyComment();
        newReplyComment.setId(this.id);

        if(approve) {
            newReplyComment.setStatus(PUBLISHED);
            // 同时将首评的replyCommentId设为回复的id
            if(replyCommentId!=null)
            {
                originComment.setReplyCommentId(id);
                originComment.setCanReply(false);
                commentDao.save(originComment, user);
            }
        }
        else {
            newReplyComment.setStatus(REJECTED);
            newReplyComment.setRejectReason(rejectReason.orElse(""));
        }
        newReplyComment.setCanReply(false); // 回复评论始终不可回复

        return commentDao.save(newReplyComment, user);
    }


    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {

    }

    @Override
    public void setGmtModified(LocalDateTime gmtModified) {

    }
}
