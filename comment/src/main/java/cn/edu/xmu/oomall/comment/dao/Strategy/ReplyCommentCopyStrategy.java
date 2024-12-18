package cn.edu.xmu.oomall.comment.dao.Strategy;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.comment.controller.vo.CommentVo;
import cn.edu.xmu.oomall.comment.dao.bo.ReplyComment;
import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;

public class ReplyCommentCopyStrategy implements CopyStrategy<ReplyComment> {
    @Override
    public void copy(CommentVo target, ReplyComment source) {
        CloneFactory.copy(target, source);
    }

    @Override
    public void copy(ReplyComment target, CommentPo source) {
        CloneFactory.copy(target, source);
    }

    @Override
    public void copy( CommentPo target,ReplyComment source) {
        CloneFactory.copy(target, source);
    }
}
