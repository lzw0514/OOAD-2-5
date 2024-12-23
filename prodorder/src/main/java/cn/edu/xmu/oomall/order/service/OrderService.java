//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.order.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.order.controller.dto.OrderDto;
import cn.edu.xmu.oomall.order.dao.OrderDao;
import cn.edu.xmu.oomall.order.dao.bo.Order;
import cn.edu.xmu.oomall.order.dao.openfeign.GoodsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static cn.edu.xmu.oomall.order.dao.bo.Order.SHIPPED;

@Repository
public class OrderService {

    @Value("${oomall.order.server-num}")
    private int serverNum;

    private GoodsDao goodsDao;

    private final OrderDao orderDao;

    // private RocketMQTemplate rocketMQTemplate;

    @Autowired
    public OrderService(GoodsDao goodsDao, OrderDao orderDao) {
        this.goodsDao = goodsDao;
        this.orderDao = orderDao;
    }

    public Order getOrderState(Long orderId)
    {
        return orderDao.findById(orderId);
    }


    public Order getCustomerOrderById(Long id) {
        return orderDao.findById(id);
    }

    public void changeCustomerOrder(Long id , OrderDto dto) {
        Order order = orderDao.findById(id);
        if (Objects.equals(order.getStatus(), SHIPPED))
            throw new BusinessException(ReturnNo.ORDER_SHIPPED, String.format(ReturnNo.ORDER_SHIPPED.getMessage()));
        order.setConsignee(dto.getConsignee());
        order.setAddress(dto.getAddress());
        order.setRegionId(dto.getRegionId());
        order.setMobile(dto.getMobile());
        orderDao.update(order);
    }

    public void deleteCustomerOrder(Long id) {
        Order order = orderDao.findById(id);
        if (Objects.equals(order.getStatus(), SHIPPED))
            throw new BusinessException(ReturnNo.ORDER_SHIPPED, String.format(ReturnNo.ORDER_SHIPPED.getMessage()));
        orderDao.delete(order);
    }

    public List<Order> getShopOrder(Long shopId) {
        List<Order> orderlist = orderDao.findByShopId(shopId);
        return orderlist;
    }

    public void changeShopOrder(Long shopId, Long id, OrderDto dto) {
        Order order = orderDao.findById(id);
        if(!Objects.equals(order.getShopId(), shopId))
            throw new BusinessException(ReturnNo.ORDER_NOT_IN_SHOP, String.format(ReturnNo.ORDER_NOT_IN_SHOP.getMessage(), id));
        order.setMessage(dto.getMessage());
        orderDao.update(order);
    }
}
