//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.dao.openfeign;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.comment.dao.CommentDao;
import cn.edu.xmu.oomall.comment.mapper.openfeign.OrderItemPoMapper;
import cn.edu.xmu.oomall.comment.dao.bo.OrderItem;
import cn.edu.xmu.oomall.comment.mapper.openfeign.po.OrderItemPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class OrderItemDao {

    private final CommentDao commentDao;
    private OrderItemPoMapper orderItemPoMapper;

    @Autowired
    public OrderItemDao(OrderItemPoMapper orderItemPoMapper, CommentDao commentDao) {
        this.orderItemPoMapper = orderItemPoMapper;
        this.commentDao = commentDao;
    }

    /**
     * 根据orderItemId找到订单项
     * @author Liuzhiwen
     * @param orderItemId
     */
    public OrderItem findById(Long orderItemId) {
        Optional<OrderItemPo> ret = orderItemPoMapper.findById(orderItemId);
        if (ret.isPresent()) {
            OrderItemPo po = ret.get();
            OrderItem orderitem = new OrderItem(po.getId(), po.getProductId(), po.getShopId(), po.getQuantity(),po.getCustomerId());
            return this.build(orderitem);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "订单项", orderItemId));
        }
    }


        /**
         * 把bo中设置dao
         * @author Liuzhiwen
         * @param bo
         */
        private OrderItem build(OrderItem bo)
        {
            bo.setCommentDao(commentDao);
            return bo;
        }

}
