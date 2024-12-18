package cn.edu.xmu.oomall.comment.dao.Strategy;

import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import cn.edu.xmu.oomall.comment.dao.bo.FirstComment;
import cn.edu.xmu.oomall.comment.dao.bo.ReplyComment;

import java.util.HashMap;
import java.util.Map;

public class CopyStrategyFactory {
    private static final Map<Class<? extends Comment>, CopyStrategy<? extends Comment>> strategies = new HashMap<>();

    static {
        strategies.put(FirstComment.class, new FirstCommentCopyStrategy());
        strategies.put(ReplyComment.class, new ReplyCommentCopyStrategy());
    }

    @SuppressWarnings("unchecked")
    public static <T extends Comment> CopyStrategy<T> getStrategy(Class<T> type) {
        return (CopyStrategy<T>) strategies.get(type);
    }

    private static final Map<Integer, Class<? extends CopyStrategy<? extends Comment>>> strategyMap = new HashMap<>();

    static {
        strategyMap.put(0, FirstCommentCopyStrategy.class);
        strategyMap.put(1, AddCommentCopyStrategy.class);
        strategyMap.put(2, ReplyCommentCopyStrategy.class);
    }

    public static CopyStrategy<? extends Comment> getStrategy(int type) {
        Class<? extends CopyStrategy<? extends Comment>> strategyClass = strategyMap.get(type);
        if (strategyClass == null)
        {
            throw new IllegalArgumentException("No strategy found for type: " + type);
        }
        try
        {
            return strategyClass.getConstructor().newInstance();
        } catch (Exception e)
        {
            throw new RuntimeException("Error creating strategy instance", e);
        }
    }
}
