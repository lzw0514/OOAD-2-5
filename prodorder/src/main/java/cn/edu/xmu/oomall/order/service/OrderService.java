//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.order.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.order.controller.dto.OrderDto;
import cn.edu.xmu.oomall.order.dao.OrderDao;
import cn.edu.xmu.oomall.order.dao.bo.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static cn.edu.xmu.oomall.order.dao.bo.Order.SHIPPED;

@Repository
public class OrderService {


    private final OrderDao orderDao;

    // private RocketMQTemplate rocketMQTemplate;

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Order getOrderState(Long orderId)
    {
        return orderDao.findById(orderId);
    }


    public Order getCustomerOrderById(Long id) {
        return orderDao.findById(id);
    }

    public void changeCustomerOrder(Long id , OrderDto dto,UserDto user) {
        Order order = orderDao.findById(id);
        if (Objects.equals(order.getStatus(), SHIPPED))
            throw new BusinessException(ReturnNo.ORDER_SHIPPED, String.format(ReturnNo.ORDER_SHIPPED.getMessage()));
        order.setConsignee(dto.getConsignee());
        order.setAddress(dto.getAddress());
        order.setRegionId(dto.getRegionId());
        order.setMobile(dto.getMobile());
        orderDao.update(order,user);
    }

    public void deleteCustomerOrder(Long id,UserDto user) {
        Order order = orderDao.findById(id);
        if (Objects.equals(order.getStatus(), SHIPPED))
            throw new BusinessException(ReturnNo.ORDER_SHIPPED, String.format(ReturnNo.ORDER_SHIPPED.getMessage()));
        orderDao.delete(order,user);
    }

    public List<Order> getShopOrder(Long shopId,UserDto user) {
        List<Order> orderlist = orderDao.findByShopId(shopId,user);
        return orderlist;
    }

    public void changeShopOrder(Long shopId, Long id, OrderDto dto, UserDto user) {
        Order order = orderDao.findById(id);
        if(!Objects.equals(order.getShopId(), shopId))
            throw new BusinessException(ReturnNo.ORDER_NOT_IN_SHOP, String.format(ReturnNo.ORDER_NOT_IN_SHOP.getMessage(), id));
        order.setMessage(dto.getMessage());
        orderDao.update(order,user);
    }
}
