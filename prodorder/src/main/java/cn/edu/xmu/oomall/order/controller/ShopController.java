package cn.edu.xmu.oomall.order.controller;


import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.order.controller.dto.OrderDto;
import cn.edu.xmu.oomall.order.controller.vo.OrderVo;
import cn.edu.xmu.oomall.order.dao.bo.Order;
import cn.edu.xmu.oomall.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController /*Restful的Controller对象*/
@RequiredArgsConstructor
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ShopController {
    private final OrderService orderService;

    /**
     * "店家查询商户所有订单 (概要)。"
     * @param shopId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("shops/{shopId}/orders")
    public ReturnObject getShopOrder(@PathVariable Long shopId,
                                    @RequestParam(required = false,defaultValue = "1") Integer page,
                                    @RequestParam(required = false,defaultValue = "10") Integer pageSize){
        List<Order> orderlist = orderService.getShopOrder(shopId);
        List<OrderVo> orderVoList = orderlist.stream().map(obj->CloneFactory.copy(new OrderVo(),obj)).toList();
        for(int i = 0;i < orderlist.size();i++ ){
            Order order = orderlist.get(i);
            OrderVo orderVo = orderVoList.get(i);
            orderVo.setItems(order.getOrderItems());
        }
        return new ReturnObject(ReturnNo.OK, new PageVo<>(orderVoList, page, pageSize));
    }

    /**
     * "店家修改订单 (留言)。"
     * @param shopId
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("shops/{shopId}/orders/{id}")
    public ReturnObject changeShopOrder(@PathVariable Long shopId, @PathVariable Long id, @RequestBody OrderDto dto){
        orderService.changeShopOrder(shopId,id,dto);
        return new ReturnObject(ReturnNo.OK);
    }
}
