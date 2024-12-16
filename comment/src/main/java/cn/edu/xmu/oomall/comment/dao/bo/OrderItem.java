package cn.edu.xmu.oomall.comment.dao.bo;

import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.comment.dao.CommentDao;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class OrderItem extends OOMallObject implements Serializable {

    private Long id;
    private Long productId;
    private Long shopId;
    private Integer quantity;

    private CommentDao commentDao;

    public OrderItem(Long id, Long productId, Long shopId, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.shopId = shopId;
        this.quantity = quantity;
    }

    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {
    }

    @Override
    public void setGmtModified(LocalDateTime gmtModified) {
    }

    public FirstComment createComment(FirstComment firstComment, UserDto user) {
        firstComment.setGmtCreate(LocalDateTime.now());
        firstComment.setGmtModified(LocalDateTime.now());
        firstComment.setOrderItemId(id);
        firstComment.setShopId(shopId);
        firstComment.setProductId(productId);
        return (FirstComment) commentDao.insert(firstComment,user);
    }
}
