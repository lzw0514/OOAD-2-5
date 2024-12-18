package cn.edu.xmu.oomall.comment.dao.Strategy;

import cn.edu.xmu.oomall.comment.controller.vo.CommentVo;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;

public interface CopyStrategy <T extends Comment>{
    void copy(CommentVo target,T source);
    void copy(T target, CommentPo source);
    void copy(CommentPo target,T source);
}
