package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.dao.CustomerDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerDao customerDao;

    // 根据Id查看顾客信息
    public Customer findCustomerById(Long customerId) {
        log.debug("findCustomerById: id = {}", customerId);
        return customerDao.findCustomerById(customerId);
    }


    /**
     * 顾客注册
     * author Wuyuzhu
     * @param customer
     * @return
     */
    public Customer Register(Customer customer) {
        log.debug("Register customer: {}", customer);
        return customerDao.insert(customer);
    }


    /**
     * 顾客修改密码
     * @param newPwd
     * @param user
     * @return
     */
    public String updatePwd(String newPwd, UserDto user) {
        Customer customer = customerDao.findCustomerById(user.getId());
        log.debug("UpdatePwd customer: {}", customer.getId());
        return customer.updatePwd(newPwd, user);
    }


    /**
     * 顾客修改个人信息
     * @param newCustomer
     * @param user
     * @return
     */
    public String changeMyselfInfo(Customer newCustomer, UserDto user) {
        Customer customer = customerDao.findCustomerById(user.getId());
        log.debug("changeMyselfInfo customer: {}", customer.getId());
        return customer.changeMyselfInfo(newCustomer, user);
    }


    /**
     * 管理员封禁顾客
     * author Linqihang
     * @param customerId
     * @param user
     */
    public void banCustomer(Long customerId, UserDto user) {
        Customer customer = customerDao.findCustomerById(customerId);
        log.debug("Ban customer: {}", customer.getId());
        customer.banCustomer(customer,user);
    }

    /**
     * 管理员解封顾客
     * author Linqihang
     * @param customerId
     * @param user
     */
    public void releaseCustomer(Long customerId, UserDto user) {
        Customer customer = customerDao.findCustomerById(customerId);
        log.debug("Release customer: {}", customer.getId());
        customer.releaseCustomer(customer,user);

    }

}
