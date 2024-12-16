//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.dao.openfeign;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.comment.mapper.openfeign.OrderItemPoMapper;
import cn.edu.xmu.oomall.comment.dao.bo.OrderItem;
import cn.edu.xmu.oomall.comment.mapper.openfeign.po.OrderItemPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderDao {

    private OrderItemPoMapper orderItemPoMapper;

    @Autowired
    public OrderDao(OrderItemPoMapper orderItemPoMapper) {
        this.orderItemPoMapper = orderItemPoMapper;
    }

    public OrderItem findOrderItemById(Long orderItemId) {
        Optional<OrderItemPo> ret = orderItemPoMapper.findById(orderItemId);
        if (ret.isPresent()) {
            OrderItemPo po = ret.get();
            return new OrderItem(po.getId(), po.getProductId(), po.getShopId(), po.getQuantity());
        }else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "订单项", orderItemId));
        }
    }
}
