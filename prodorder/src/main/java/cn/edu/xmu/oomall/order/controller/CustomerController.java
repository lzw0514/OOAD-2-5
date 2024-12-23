//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.order.controller;

import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.model.vo.IdNameTypeVo;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.order.controller.dto.OrderDto;
import cn.edu.xmu.oomall.order.controller.vo.OrderVo;
import cn.edu.xmu.oomall.order.dao.bo.Order;
import cn.edu.xmu.oomall.order.mapper.SearchMapper;
import cn.edu.xmu.oomall.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author Fengjianhao 订单控制器
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(produces = "application/json;charset=UTF-8")
public class CustomerController {

    private final OrderService orderService;

    private final SearchMapper searchMapper;


    /**
     *
     * @param itemName
     * @param customerId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/orders")
    public ReturnObject testFeignSearch(
            @RequestParam(value = "itemName") String itemName,
            @RequestParam(value = "customerId") Long customerId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            InternalReturnObject<List<Long>> response = searchMapper.searchOrders(itemName, customerId, page, size);

            if (response.getErrno() == ReturnNo.OK.getErrNo()) {
                return new ReturnObject(ReturnNo.OK, response.getData());
            } else {
                throw new BusinessException(ReturnNo.getReturnNoByCode(response.getErrno()), response.getErrmsg());
            }
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, "Feign 调用失败: " + e.getMessage());
        }
    }

    /**
     * 获得订单的所有状态
     * @param orderId
     * @return
     */
    @GetMapping("/orders/{orderId}/states")
    public ReturnObject getOrderState(@PathVariable Long orderId) {
        Order order = orderService.getOrderState(orderId);
        IdNameTypeVo vo = IdNameTypeVo.builder().type(order.getStatus()).name(order.getStatus().toString()).build();
        return new ReturnObject(ReturnNo.OK, vo);
    }

    /**
     * 顾客查询订单完整信息
     * @param id
     * @return
     */
    @GetMapping("/orders/{id}")
    public ReturnObject getCustomerOrderById(@PathVariable Long id){
        Order order = orderService.getCustomerOrderById(id);
        OrderVo vo = CloneFactory.copy(new OrderVo(), order);
        vo.setItems(order.getOrderItems());
        return new ReturnObject(ReturnNo.OK, vo);
    }

    /**
     * 顾客修改本人名下订单。
     * 现在仅允许用户调用本 API 更改未发货订单的收货地址
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("/orders/{id}")
    public ReturnObject changeCustomerOrder(@PathVariable Long id,
                                            @RequestBody OrderDto dto,
                                            @LoginUser UserDto user){
        orderService.changeCustomerOrder(id,dto,user);
        return new ReturnObject(ReturnNo.OK);
    }

    /**
     * 顾客取消本人名下订单。
     * @param id
     * @return
     */
    @DeleteMapping("/orders/{id}")
    public ReturnObject deleteCustomerOrder(@PathVariable Long id,
                                            @LoginUser UserDto user){
        orderService.deleteCustomerOrder(id,user);
        return new ReturnObject(ReturnNo.OK);
    }

}
