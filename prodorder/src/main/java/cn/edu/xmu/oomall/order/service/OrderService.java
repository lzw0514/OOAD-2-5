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
    public OrderService(GoodsDao goodsDao, OrderDao orderDao
            //, RocketMQTemplate rocketMQTemplate
    ) {
        this.goodsDao = goodsDao;
        this.orderDao = orderDao;
        // this.rocketMQTemplate = rocketMQTemplate;
    }

//    @Transactional
//    public Map<Long, List<OrderItem>> packOrder(List<OrderItemDto> items, UserDto customer){
//        Map<Long, List<OrderItem>> packs = new HashMap<>();
//        items.stream().forEach(item -> {
//            OnsaleDto onsaleDto = this.goodsDao.getOnsaleById(PLATFORM, item.getOnsaleId()).getData();
//            OrderItem orderItem = OrderItem.builder().onsaleId(onsaleDto.getId()).price(onsaleDto.getPrice()).name(onsaleDto.getProduct().getName()).creatorId(customer.getId()).creatorName(customer.getName()).build();
//            if (null != onsaleDto.getActList() && null != item.getActId()){
//                if (onsaleDto.getActList().stream().filter(activity -> activity.getId() == item.getActId()).count()  > 0){
//                    orderItem.setActId(item.getActId());
//                    //TODO: 需要查看优惠卷id所属的活动是否在onsale的活动列表中，并且优惠卷是有效的，才能设置到orderItem中
//                }
//            }
//            if (item.getQuantity() <= onsaleDto.getMaxQuantity()){
//                //不能超过最大可购买数量
//                orderItem.setQuantity(item.getQuantity());
//            }else{
//                throw new BusinessException(ReturnNo.ITEM_OVERMAXQUANTITY, String.format(ReturnNo.ITEM_OVERMAXQUANTITY.getMessage(), onsaleDto.getId(), item.getQuantity(), onsaleDto.getMaxQuantity()));
//            }
//            Long shopId = onsaleDto.getShop().getId();
//            List<OrderItem> pack = packs.get(shopId);
//            if (null == pack){
//                packs.put(shopId, new ArrayList<>(){
//                    {
//                        add(orderItem);
//                    }
//                });
//            }else{
//                pack.add(orderItem);
//            }
//        });
//        return packs;
//    }
//
//    @Transactional
//    public void saveOrder(Map<Long, List<OrderItem>> packs, ConsigneeDto consignee, String message, UserDto customer){
//        packs.keySet().stream().forEach(shopId -> {
//            Order order = Order.builder().creatorId(customer.getId()).customerId(customer.getId()).creatorName(customer.getName()).gmtCreate(LocalDateTime.now()).shopId(shopId).
//                    consignee(consignee.getConsignee()).address(consignee.getAddress()).mobile(consignee.getMobile()).regionId(consignee.getRegionId()).
//                    orderSn(Common.genSeqNum(serverNum)).message(message).orderItems(packs.get(shopId)).build();
//                    this.orderDao.createOrder(order);
//                }
//        );
//
//    }
//
//    public void createOrder(List<OrderItemDto> items, ConsigneeDto consignee, String message, UserDto customer) {
//        Map<Long, List<OrderItem>> packs = this.packOrder(items, customer);
//
//        String packStr = JacksonUtil.toJson(packs);
//        Message msg = MessageBuilder.withPayload(packStr).setHeader("consignee", consignee).setHeader("message",message).setHeader("user", customer).build();
//        rocketMQTemplate.sendMessageInTransaction("order-topic:1", msg, null);
//    }
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
