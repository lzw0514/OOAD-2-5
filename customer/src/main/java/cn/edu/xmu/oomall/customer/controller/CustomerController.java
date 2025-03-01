package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.oomall.customer.controller.dto.AddressDto;
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
 * @author Liuzhiwen
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 顾客查看自己信息
     * @param user
     * @return
     */

    @GetMapping("/customer")
    @Audit(departName = "customers")
    public ReturnObject getCustomerInfo(@LoginUser UserDto user) {
        Customer customer = customerService.findCustomerById(user.getId());
        return new ReturnObject(new CustomerVo(customer));
    }

    /**
     * 顾客修改密码
     * @param newPwd
     * @param user
     * @return
     */
    @PutMapping("/customer/password")
    @Audit(departName = "customers")
    public ReturnObject updatePwd(@RequestParam String newPwd,
                                  @LoginUser UserDto user) {
        customerService.updatePwd(newPwd, user);
        return new ReturnObject(ReturnNo.OK);
    }

    /**
     * 顾客修改个人信息
     * @param user
     * @return
     */
    @PutMapping("/customer")
    @Audit(departName = "customers")
    public ReturnObject changeMyselfInfo(@RequestBody CustomerDto customerDto,
                                         @LoginUser UserDto user) {
        Customer newCustomer=CloneFactory.copy(new Customer(),customerDto);
        customerService.changeMyselfInfo(newCustomer, user);
        return new ReturnObject(ReturnNo.OK);
    }
}
