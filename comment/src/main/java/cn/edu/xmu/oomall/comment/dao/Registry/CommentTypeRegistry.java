package cn.edu.xmu.oomall.comment.dao.Registry;

import cn.edu.xmu.oomall.comment.dao.bo.AddComment;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import cn.edu.xmu.oomall.comment.dao.bo.FirstComment;
import cn.edu.xmu.oomall.comment.dao.bo.ReplyComment;

import java.util.HashMap;
import java.util.Map;

public class CommentTypeRegistry {

    private static final Map<Integer, Class<? extends Comment>> typeMap = new HashMap<>();

    static {
        typeMap.put(0, FirstComment.class);
        typeMap.put(1, AddComment.class);
        typeMap.put(2, ReplyComment.class);
    }

    public static Class<? extends Comment> getCommentClass(int type) {
        return typeMap.get(type);
    }
}
