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
public class FirstComment extends Comment {

    private static final Logger logger = LoggerFactory.getLogger(FirstComment.class);

    private Byte rating;

    private Integer likes;

    @Override
    // 审核评论
    public String auditComment(boolean approve, Optional<String> rejectReason, UserDto user)
    {
        if(approve) {
            setStatus(PUBLISHED);
            setCanReply(true); // 审核通过的评论设为可回复
        }
        else {
            setStatus(REJECTED);
            setCanReply(false);
            setRejectReason(rejectReason.orElse(""));
            // 同时将回复驳回
            if(replyCommentId!=null)
            {
                ReplyComment newReplyComment = (ReplyComment) commentDao.findCommentById(replyCommentId);
                newReplyComment.relatedReject();
                newReplyComment.setReplyCommentId(replyCommentId);
                newReplyComment.setStatus(REJECTED);
                newReplyComment.setRejectReason("原评论被驳回");
                commentDao.save(newReplyComment, user);
            }
        }
        return commentDao.save(this, user);
    }

    // 回复评论（创建者：回复评论需要首评Id信息，业务需要判断当前评论状态）
    public ReplyComment createReply(Long shopId, ReplyComment newReplyComment, UserDto user)
    {
        if(!Objects.equals(shopId, this.shopId))
            throw new BusinessException(ReturnNo.AUTH_NO_RIGHT, String.format(ReturnNo.AUTH_NO_RIGHT.getMessage()));
        if(Objects.equals(status, PUBLISHED) && canReply)
        {
            newReplyComment.setReplyCommentId(id); // 回复的ReplyCommentId应该为首评id
            return (ReplyComment) commentDao.insert(newReplyComment, user);
        }
        else {
            throw new BusinessException(ReturnNo.COMMENT_NOT_RETURNABLE, String.format(ReturnNo.COMMENT_NOT_RETURNABLE.getMessage(), this.id));
        }
    }

    // 点赞评论（信息专家：业务需要判断当前评论状态并且获取评论赞数的信息）
    public String likeComment(int like, UserDto user)
    {
        if(Objects.equals(status, PUBLISHED))
        {
            setLikes(likes+like);
            return commentDao.save(this, user);
        }
        else {
            throw new BusinessException(ReturnNo.COMMENT_NOT_PUBLISHED, String.format(ReturnNo.COMMENT_NOT_PUBLISHED.getMessage(), this.id));
        }
    }

    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {}

    @Override
    public void setGmtModified(LocalDateTime gmtModified) {}

    public Byte getRating() { return rating; } public void setRating(Byte rating) { this.rating = rating; }
    public Integer getLikes() { return likes; } public void setLikes(Integer likes) { this.likes = likes; }
}
