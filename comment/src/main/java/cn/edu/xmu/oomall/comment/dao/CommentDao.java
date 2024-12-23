//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.comment.dao.bo.*;
import cn.edu.xmu.oomall.comment.dao.openfeign.OrderItemDao;
import cn.edu.xmu.oomall.comment.mapper.CommentPoMapper;
import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.IDNOTEXIST;

/**
 * @author Liuzhiwen
 */

@Repository
@RefreshScope
@RequiredArgsConstructor
@Slf4j
public class CommentDao {

    private final static String KEY = "C%d";
    private final CommentPoMapper commentPoMapper;
    private final OrderItemDao orderitemDao;


    /**
     * 平台管理人员通过commentId查询特定评论，
     * @param commentId
     */
    public Comment findById(Long commentId) {
        log.debug("findcommentById: id = {}",commentId);
        Optional<CommentPo> ret = this.commentPoMapper.findById(commentId);
        if (ret.isPresent()) {
            CommentPo po = ret.get();
            return this.build(po);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "评论", commentId));
        }
    }
    /**
     * 不用登录，通过commentId查询特定评论,只有PUBLISHED状态和REPORTED_FOR_REVIEW状态的评论可以被查询到
     * @param commentId
     */
    public Comment  findValidCommenntById(Long commentId) {
        log.debug("findvalidcommentById: id = {}",commentId);
        Optional<CommentPo> ret = this.commentPoMapper.findValidCommentById(commentId);
        if (ret.isPresent()) {
            CommentPo po = ret.get();
            return this.build(po);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "评论", commentId));
        }
    }


    /**
     * 通过orderItemId查询特定评论
     * @param orderItemId
     */
    public  Optional<CommentPo> findByOrderItemId(Long orderItemId) {
        Optional<CommentPo> ret = this.commentPoMapper.findByOrderitemId(orderItemId);
        return ret;
    }
    /**
     * 获得bo对象
     * @param po
     * @return
     */
    private Comment build(CommentPo po){
        Comment ret;
        switch (po.getType())
        {
            case 0:
                ret = CloneFactory.copy(new FirstComment(), po);
                break;
            case 1:
                ret = CloneFactory.copy(new AddComment(), po);
                break;
            case 2:
                ret = CloneFactory.copy(new ReplyComment(), po);
                break;
            default:
                throw new BusinessException(ReturnNo.COMMENT_NOT_TYPE, String.format(ReturnNo.COMMENT_NOT_TYPE.getMessage()));
        }
        this.build(ret);
        return ret;
    }


    /**
     * 赋予bo对象权限
     * @param bo
     */
    private Comment build(Comment bo){
        bo.setCommentDao(this);
        bo.setOrderItemDao(orderitemDao);
        return bo;
    }


    /**
     * 通过productId查询商品的全部评论
     * @param productId
     */
    public List<Comment> retrieveProductComments(Long productId, Integer page, Integer pageSize) throws RuntimeException {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        Page<CommentPo> pos = commentPoMapper.findCommentByProductId(productId, pageable);
        if (!pos.isEmpty())
            return pos.stream().map(po -> this.build(po)).collect(Collectors.toList());
        else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, "该商品暂无评论");
        }
    }


    /**
     * 修改评论信息
     * @param bo   评论bo
     * @param user 登录用户
     * @return
     */
    public String save(Comment bo, UserDto user) {
        bo.setModifier(user);
        bo.setGmtModified(LocalDateTime.now());
        CommentPo po = null;
        if (bo instanceof FirstComment) {
            FirstComment obj = (FirstComment) bo;
            po = CloneFactory.copy(new CommentPo(), obj);
        }
        else if (bo instanceof AddComment) {
            AddComment obj = (AddComment) bo;
            po = CloneFactory.copy(new CommentPo(), obj);
        }
        else if (bo instanceof ReplyComment) {
            ReplyComment obj = (ReplyComment) bo;
            po = CloneFactory.copy(new CommentPo(), obj);
        }
        CommentPo save = commentPoMapper.save(po);
        return String.format(KEY, save.getId());
    }

    /**
     * 创建评论
     * @param bo   地区bo
     * @param user 登录用户
     */
    public Comment insert(Comment bo, UserDto user) {
        bo.setId(null);
        bo.setCreator(user);
        bo.setGmtCreate(LocalDateTime.now());
        CommentPo po = null;

        if (bo instanceof FirstComment) {
            FirstComment obj = (FirstComment) bo;
            po = CloneFactory.copy(new CommentPo(), obj);
        }
        else if (bo instanceof AddComment) {
            AddComment obj = (AddComment) bo;
            po = CloneFactory.copy(new CommentPo(), obj);
        }
        else if (bo instanceof ReplyComment) {
            ReplyComment obj = (ReplyComment) bo;
            po = CloneFactory.copy(new CommentPo(), obj);
        } else{
            throw new BusinessException(ReturnNo.COMMENT_NOT_TYPE, String.format(ReturnNo.COMMENT_NOT_TYPE.getMessage()));
        }
        log.debug("save: po = {}", po);
        CommentPo save = this.commentPoMapper.save(po);
        bo.setId(save.getId());
        return bo;
    }
}
