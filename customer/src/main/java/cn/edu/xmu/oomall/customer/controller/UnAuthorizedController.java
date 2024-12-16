package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.validation.NewGroup;
import cn.edu.xmu.oomall.customer.controller.dto.CustomerDto;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import cn.edu.xmu.oomall.customer.controller.vo.CustomerVo;
/**
 * 无需登录的顾客API
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
public class UnAuthorizedController {
    private final CustomerService customerService;

    /**
     * 注册
     * @param dto
     * @return
     */
    @PostMapping("/customers")
    public ReturnObject CreatCustomer(@Validated(NewGroup.class) @RequestBody CustomerDto dto) {
        Customer customer = CloneFactory.copy(new Customer(), dto);
        Customer newCustomer = this.customerService.createCustomer(customer);
        CustomerVo vo = CloneFactory.copy(new CustomerVo(), newCustomer);
        return new ReturnObject(ReturnNo.CREATED, vo);
    }

}
