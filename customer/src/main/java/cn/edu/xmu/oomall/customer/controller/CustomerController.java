package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.oomall.customer.controller.dto.CustomerDto;
import cn.edu.xmu.oomall.customer.controller.vo.CustomerVo;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import cn.edu.xmu.oomall.customer.mapper.po.CustomerPo;
import cn.edu.xmu.oomall.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * 顾客控制器
 * @author Shuyang Xing
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    // 顾客查看自己信息
    @GetMapping("/customer")
    public ReturnObject getCustomerInfo(@LoginUser UserDto user) {
        Customer customer = customerService.findCustomerById(user.getId());
        return new ReturnObject(new CustomerVo(customer));
    }

    // 注册顾客
    @PostMapping("/customer")
    public ReturnObject customerRegister(@RequestBody CustomerDto customerDto) {
        Customer customer = CloneFactory.copy(new Customer(), customerDto);
        Customer res = customerService.Register(customer);
        return new ReturnObject(new CustomerVo(res));
    }

    // 顾客修改密码
    @PutMapping("/customer/password")
    public ReturnObject updatePwd(@RequestParam String newPwd,
                                  @LoginUser UserDto user) {
        customerService.updatePwd(newPwd, user);
        return new ReturnObject(ReturnNo.OK);
    }

    // 顾客修改电话号码
    @PutMapping("/customer/mobile")
    public ReturnObject updateMobile(@RequestParam String newMobile,
                                     @LoginUser UserDto user) {
        customerService.updateMobile(newMobile, user);
        return new ReturnObject(ReturnNo.OK);
    }

    // 管理员封禁顾客
    @PutMapping("/customers/{customerId}/ban")
    public ReturnObject banCustomer(@PathVariable Long customerId,
                                    @LoginUser UserDto user) {
        customerService.banCustomer(customerId, user);
        return new ReturnObject(ReturnNo.OK);
    }

    // 管理员解封顾客
    @PutMapping("/customers/{customerId}/release")
    public ReturnObject releaseCustomer(@PathVariable Long customerId,
                                        @LoginUser UserDto user) {
        customerService.releaseCustomer(customerId, user);
        return new ReturnObject(ReturnNo.OK);
    }

    // 管理员注销顾客
    @PutMapping("/customers/{customerId}/delete")
    public ReturnObject deleteCustomer(@PathVariable Long customerId,
                                        @LoginUser UserDto user) {
        customerService.deleteCustomer(customerId, user);
        return new ReturnObject(ReturnNo.OK);
    }
}
