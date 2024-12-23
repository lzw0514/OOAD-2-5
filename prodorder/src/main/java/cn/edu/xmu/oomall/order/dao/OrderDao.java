//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.order.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.util.JacksonUtil;
import cn.edu.xmu.oomall.order.dao.bo.Order;
import cn.edu.xmu.oomall.order.dao.bo.OrderItem;
import cn.edu.xmu.oomall.order.mapper.OrderItemPoMapper;
import cn.edu.xmu.oomall.order.mapper.OrderPoMapper;
import cn.edu.xmu.oomall.order.mapper.po.OrderItemPo;
import cn.edu.xmu.oomall.order.mapper.po.OrderPo;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDao {

    private final OrderPoMapper orderPoMapper;

    private OrderItemPoMapper orderItemPoMapper;


    @Autowired
    public OrderDao(OrderPoMapper orderPoMapper, OrderItemPoMapper orderItemPoMapper) {
        this.orderPoMapper = orderPoMapper;
        this.orderItemPoMapper = orderItemPoMapper;
    }

    public Order findById(Long id) {
        OrderPo po = orderPoMapper.findOrderById(id);
        if (null == po) {
            throw new BusinessException(ReturnNo.ORDER_NOT_FOUND, String.format(ReturnNo.ORDER_NOT_FOUND.getMessage()));
        }
        Order bo = CloneFactory.copy(new Order(), po);
        List<OrderItem> OrderItemList = orderPoMapper.findItemByOrderId(id).stream().map(entity->CloneFactory.copy(new OrderItem(), entity)).toList();
        bo.setOrderItems(OrderItemList);
        return bo;
    }

    public void update(Order order){
        OrderPo po = CloneFactory.copy(new OrderPo(), order);
        orderPoMapper.save(po);
    }

    public void delete(Order order){
        OrderPo po = CloneFactory.copy(new OrderPo(), order);
        orderPoMapper.delete(po);
    }

    public List<Order> findByShopId(Long shopId) {
        List<OrderPo> poList = orderPoMapper.findByShopId(shopId);
        List<Order> orderList = new ArrayList<>();
        poList.forEach(po->{
            Order order = CloneFactory.copy(new Order(), po);
            List<OrderItem> OrderItemList = orderPoMapper.findItemByOrderId(order.getId()).stream().map(entity->CloneFactory.copy(new OrderItem(), entity)).toList();
            order.setOrderItems(OrderItemList);
            orderList.add(order);
        });
        return orderList;
    }


//    public void createOrder(Order order){
//        OrderPo orderPo = OrderPo.builder().creatorId(order.getCreatorId()).creatorName(order.getCreatorName()).orderSn(order.getOrderSn()).build();
//        orderPoMapper.save(orderPo);
//
//        order.getOrderItems().stream().forEach(orderItem -> {
//            //TODO: 先要减去货品数量
//
//            OrderItemPo orderItemPo = OrderItemPo.builder().creatorId(orderItem.getCreatorId()).onsaleId(orderItem.getOnsaleId()).quantity(orderItem.getQuantity()).build();
//            orderItemPoMapper.save(orderItemPo);
//        });
//    }
}
