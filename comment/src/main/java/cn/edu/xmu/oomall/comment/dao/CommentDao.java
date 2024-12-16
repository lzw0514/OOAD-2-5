//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.oomall.comment.dao.bo.*;
import cn.edu.xmu.oomall.comment.dao.openfeign.ShopDao;
import cn.edu.xmu.oomall.comment.mapper.CommentPoMapper;
import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * CommentDao 父类
 * @author Shuyang Xing
 */
@Repository
@RefreshScope
@RequiredArgsConstructor
public class CommentDao {

    private final static Logger logger = LoggerFactory.getLogger(CommentDao.class);
    private final CommentPoMapper commentPoMapper;
    private final ShopDao shopDao;

    // 根据Id找评论
    public Comment findCommentById(Long commentId) {
        Optional<CommentPo> ret = this.commentPoMapper.findById(commentId);
        if (ret.isPresent()) {
            CommentPo po = ret.get();
            // 使用工厂返回不同子类的对象
            return CommentFactory.build(po);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "评论", commentId));
        }
    }

    // 根据商品Id找评论
    public List<Comment> retrieveCommentByProduct(Long productId, Integer page, Integer pageSize) throws RuntimeException {
        List<Comment> commentList = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<CommentPo> poPage = commentPoMapper.findCommentByProductId(productId, pageable);
        if (!poPage.isEmpty()) {
            commentList = poPage.stream()
                    .map(po -> CommentFactory.build(po))  // 工厂方法转换为Comment对象
                    .collect(Collectors.toList());
        }
        logger.debug("retrieveCommentByProduct: commentList size = {}", commentList.size());
        return commentList;
    }


    public static class CommentFactory {
        public static Comment build(CommentPo po) {
            switch (po.getType()) {
                case 1:
                    return CloneFactory.copy(new FirstComment(), po);
                case 2:
                    return CloneFactory.copy(new ReplyComment(), po);
                default:
                    throw new IllegalArgumentException("Unknown comment type");
            }
        }
    }

    public String save(Comment comment, UserDto userDto) {
        CommentPo po = null;
        if (comment instanceof FirstComment) {
            FirstComment obj = (FirstComment) comment;
            po = CloneFactory.copy(new CommentPo(), obj);
        } else if (comment instanceof ReplyComment) {
            ReplyComment obj = (ReplyComment) comment;
            po = CloneFactory.copy(new CommentPo(), obj);
        } else{
            throw new IllegalArgumentException("Unknown comment type");
        }
        CommentPo save = commentPoMapper.save(po);
        return "OK";
    }

    public Comment insert(Comment comment, UserDto user) {
        CommentPo commentPo = null;
        if (comment instanceof FirstComment) {
            FirstComment obj = (FirstComment) comment;
            commentPo = CloneFactory.copy(new CommentPo(), obj);
        } else if (comment instanceof ReplyComment) {
            ReplyComment obj = (ReplyComment) comment;
            commentPo = CloneFactory.copy(new CommentPo(), obj);
        } else{
            throw new IllegalArgumentException("Unknown comment type");
        }
        comment.setGmtCreate(LocalDateTime.now());
        comment.setCreator(user);
        commentPo.setId(null);
        CommentPo save = this.commentPoMapper.save(commentPo);
        comment.setId(save.getId());
        return comment;
    }
}
