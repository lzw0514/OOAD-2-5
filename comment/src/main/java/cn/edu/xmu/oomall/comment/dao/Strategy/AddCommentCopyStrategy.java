package cn.edu.xmu.oomall.comment.dao.Strategy;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.comment.controller.vo.CommentVo;
import cn.edu.xmu.oomall.comment.dao.bo.AddComment;
import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;

public class AddCommentCopyStrategy implements CopyStrategy<AddComment>{
    @Override
    public void copy(CommentVo target, AddComment source) {
        CloneFactory.copy(target, source);
    }

    @Override
    public void copy(AddComment target, CommentPo source) {
        CloneFactory.copy(target, source);
    }

    @Override
    public void copy( CommentPo target, AddComment source) {
        CloneFactory.copy(target, source);
    }
}
