package cn.edu.xmu.oomall.comment.dao.bo;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.comment.dao.CommentDao;
import cn.edu.xmu.oomall.comment.dao.openfeign.OrderItemDao;
import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class OrderItem extends OOMallObject implements Serializable {

    private Long id;
    private Long productId;
    private Long shopId;
    private Long customerId;
    private Integer quantity;

    private CommentDao commentDao;
    private OrderItemDao OrderitemDao;

    public OrderItem(Long id, Long productId, Long shopId, Integer quantity,Long customerId) {
        this.id = id;
        this.productId = productId;
        this.shopId = shopId;
        this.quantity = quantity;
        this.customerId=customerId;
    }

    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {
    }

    @Override
    public void setGmtModified(LocalDateTime gmtModified) {
    }

    public FirstComment createComment(FirstComment firstComment, UserDto user) {

        Optional<CommentPo> comment=this.commentDao.findByOrderItemId(id);

        if (comment.isPresent() && comment.get().getStatus() !=2)
        {
            throw new BusinessException(ReturnNo.COMMENT_UPPER_LIMIT, String.format(ReturnNo.COMMENT_UPPER_LIMIT.getMessage()));
        }
        firstComment.setType((byte) 0);
        firstComment.setOrderitemId(id);
        firstComment.setShopId(shopId);
        firstComment.setProductId(productId);

        return (FirstComment) commentDao.insert(firstComment,user);
    }

}
