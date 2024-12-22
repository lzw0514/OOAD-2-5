package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.controller.dto.CustomerDto;
import cn.edu.xmu.oomall.customer.controller.vo.CustomerVo;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 顾客控制器
 * @author Wuyuzhu
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
@Slf4j
public class UnauthorizedController {

    private final CustomerService customerService;


    /**
     * 注册顾客
     * @return
     */
    @PostMapping("/customer")
    public ReturnObject customerRegister(@RequestBody CustomerDto customerDto) {
        Customer customer = CloneFactory.copy(new Customer(), customerDto);
        Customer res = customerService.Register(customer);
        return new ReturnObject(ReturnNo.CREATED, new CustomerVo(res));
    }
}
