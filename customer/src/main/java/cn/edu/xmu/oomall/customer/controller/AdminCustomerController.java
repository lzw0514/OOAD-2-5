package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;

import cn.edu.xmu.javaee.core.model.vo.IdNameTypeVo;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.validation.NewGroup;
import cn.edu.xmu.oomall.customer.controller.dto.CustomerDto;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import cn.edu.xmu.oomall.customer.controller.vo.CustomerVo;

import java.util.List;
import java.util.stream.Collectors;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;
/**
 * 顾客管理员控制器
 *
 */
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/platforms/{id}", produces = "application/json;charset=UTF-8")
@Transactional
@RequiredArgsConstructor
public class AdminCustomerController {
    private final static Logger logger = LoggerFactory.getLogger(AdminCustomerController.class);
    private final CustomerService customerService;
    /**
     * 管理员查看任意买家信息
     * @param shopId  管理员,必须为0
     * @param id  顾客Id
     * @return
     */
    @GetMapping("/shops/{shopId}/customers/{id)")
    public ReturnObject getCustomerById(@PathVariable("shopId") Long shopId,@PathVariable("id") Long id) {
        if(!PLATFORM.equals(shopId)){
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "顾客", id, shopId));
        }
        Customer customer = customerService.findCustomerById(id);
        CustomerVo vo = CloneFactory.copy(new CustomerVo(), customer);
        return new ReturnObject(ReturnNo.OK, vo);
    }
    /**
     * 管理员获取所有顾客列表
     * @param shopId, PLATFORM
     * @param page 页码
     * @param pageSize 每页数量
     */
    @GetMapping("shops/{shopId}/customers")
    public ReturnObject getCustomers(@PathVariable("shopId") Long shopId,
                                     @RequestParam(required = false, defaultValue = "1") Integer page,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        if(!PLATFORM.equals(shopId)){
            throw new BusinessException(ReturnNo.AUTH_NO_RIGHT, String.format(ReturnNo.AUTH_NO_RIGHT.getMessage()));
        }
        List<Customer> customerList = customerService.findAllCustomers();
        return new ReturnObject(new PageVo<>(customerList.stream().map(o -> IdNameTypeVo.builder().id(o.getId()).name(o.getName()).build()).collect(Collectors.toList()), page, pageSize));
    }
    /**
     * 管理员解封顾客
     * @param did, PLATFORM
     * @param id 顾客Id
     * @return
     */
    @PutMapping("shops/{did}/customers/{id}/release")
    public ReturnObject release(@PathVariable("did") Long did,@PathVariable("id") Long id) {
        if(!PLATFORM.equals(did)){
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "顾客", id, did));
        }
        customerService.release(id);
        return new ReturnObject(ReturnNo.OK, String.format(ReturnNo.OK.getMessage()));
    }
    /**
     * 管理员封禁顾客
     * @param did, PLATFORM
     * @param id 顾客Id
     * @return
     */
    @PutMapping("shops/{did}/customers/{id}/ban")
    public ReturnObject ban(@PathVariable("did") Long did,@PathVariable("id") Long id) {
        if(!PLATFORM.equals(did)){
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "顾客", id, did));
        }
        customerService.ban(id);
        return new ReturnObject(ReturnNo.OK, String.format(ReturnNo.OK.getMessage()));
    }
    /**
     * 管理员删除顾客
     * @param shopId, PLATFORM
     * @param id 顾客Id
     * @return
     */
    @DeleteMapping("shops/{shopId}/customers/{id}")
    public ReturnObject delete(@PathVariable("shopId") Long shopId,@PathVariable("id") Long id) {
        if(!PLATFORM.equals(shopId)){
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "顾客", id, shopId));
        }
        customerService.delete(id);
        return new ReturnObject(ReturnNo.OK, String.format(ReturnNo.OK.getMessage()));
    }
}
