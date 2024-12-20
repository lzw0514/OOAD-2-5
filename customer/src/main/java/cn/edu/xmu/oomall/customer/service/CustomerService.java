package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.dao.CustomerDao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
public class CustomerService {

    private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerDao customerDao;

    // 根据Id查看顾客信息
    public Customer findCustomerById(Long customerId) {
        logger.debug("findCustomerById: id = {}", customerId);
        return customerDao.findCustomerById(customerId);
    }

    // 顾客注册
    public Customer Register(Customer customer) {
        logger.debug("Register customer: {}", customer);
        return customerDao.insert(customer);
    }

    // 顾客修改密码
    public String updatePwd(String newPwd, UserDto user) {
        Customer customer = customerDao.findCustomerById(user.getId());
        logger.debug("UpdatePwd customer: {}", customer.getId());
        return customer.updatePwd(newPwd, user);
    }

    // 顾客修改电话号码
    public String updateMobile(String newMobile, UserDto user) {
        Customer customer = customerDao.findCustomerById(user.getId());
        logger.debug("UpdateMobile customer: {}", customer.getId());
        return customer.updateMobile(newMobile, user);
    }

    // 封禁顾客
    public void banCustomer(Long customerId, UserDto user) {
        Customer customer = customerDao.findCustomerById(customerId);
        logger.debug("Ban customer: {}", customer.getId());
        if(customer.getStatus() != 1){
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "顾客", customerId, "账户无效"));
        }
        customer.setStatus((byte) 0);
        customerDao.save(customer, user);
    }

    // 解封顾客
    public void releaseCustomer(Long customerId, UserDto user) {
        Customer customer = customerDao.findCustomerById(customerId);
        logger.debug("Release customer: {}", customer.getId());
        if(customer.getStatus() != 0){
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "顾客", customerId, "账户无效"));
        }
        customer.setStatus((byte) 1);
        customerDao.save(customer, user);
    }

    // 注销顾客
    public void deleteCustomer(Long customerId, UserDto user) {
        Customer customer = customerDao.findCustomerById(customerId);
        logger.debug("Delete customer: {}", customer.getId());
        if(customer.getStatus() == -1){
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "顾客", customerId, "账户无效"));
        }
        customer.setStatus((byte) -1);
        customerDao.save(customer, user);
    }
}
