//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.oomall.comment.dao.bo.*;
import cn.edu.xmu.oomall.comment.dao.openfeign.OrderItemDao;
import cn.edu.xmu.oomall.comment.dao.openfeign.ShopDao;
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
import java.util.ArrayList;
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
    private final ShopDao shopDao;
    private final OrderItemDao orderitemDao;


    /**
     * 通过commentId查询特定评论
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
     * 通过orderItemId查询特定评论
     * @param orderItemId
     */
    public  Optional<CommentPo> findByOrderItemId(Long orderItemId) {

        Optional<CommentPo> ret = this.commentPoMapper.findByOrderItemId(orderItemId);
        return ret;
    }
    /**
     * 获得bo对象
     * @author Liuzhiwen
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
                throw new IllegalArgumentException("Undefined comment type");
        }
        this.build(ret);
        return ret;
    }


    /**
     * 把bo中设置dao
     * @author Liuzhiwen
     * @param bo
     */
    private Comment build(Comment bo){
        bo.setCommentDao(this);
        bo.setOrderItemDao(orderitemDao);
        bo.setShopDao(shopDao);
        return bo;
    }


    /**
     * 通过productId查询商品的全部评论
     */
    public List<Comment> retrieveProductComments(Long productId, Integer page, Integer pageSize) throws RuntimeException {
        log.debug("retrieveProductComments: productId={}",productId);
        Pageable pageable = PageRequest.of(page-1, pageSize);
        List<CommentPo> pos = commentPoMapper.findCommentByProductId(productId, pageable);
        return pos.stream().map(po -> this.build(po)).collect(Collectors.toList());

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
        CommentPo updatePosave = commentPoMapper.save(po);
        if(IDNOTEXIST.equals(updatePosave.getId())) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "评论", bo.getId()));
        }

        return String.format(KEY, bo.getId());
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
        else if (bo instanceof FirstComment) {
            FirstComment obj = (FirstComment) bo;
            po = CloneFactory.copy(new CommentPo(), obj);
        }
        else if (bo instanceof ReplyComment) {
            ReplyComment obj = (ReplyComment) bo;
            po = CloneFactory.copy(new CommentPo(), obj);
        } else{
            throw new IllegalArgumentException("Unknown comment type");
        }
        log.debug("save: po = {}", po);
        po = this.commentPoMapper.save(po);
        bo.setId(po.getId());
        return bo;
    }
}
