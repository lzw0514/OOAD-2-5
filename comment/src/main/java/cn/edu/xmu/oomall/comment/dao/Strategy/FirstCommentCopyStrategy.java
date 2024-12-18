package cn.edu.xmu.oomall.comment.dao.Strategy;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.comment.controller.vo.CommentVo;
import cn.edu.xmu.oomall.comment.dao.bo.FirstComment;
import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;

public class FirstCommentCopyStrategy implements CopyStrategy<FirstComment>{
    @Override
    public void copy(CommentVo target, FirstComment source) {
        CloneFactory.copy(target, source);
    }

    @Override
    public void copy(FirstComment target, CommentPo source) {
        CloneFactory.copy(target, source);
    }

    @Override
    public void copy( CommentPo target, FirstComment source) {
        CloneFactory.copy(target, source);
    }
}
