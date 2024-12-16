package cn.edu.xmu.oomall.customer.service;


import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
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
    private final CustomerDao CustomerDao;
    /**
     * 创建顾客
     * @param customer     所创建对象的信息
     */
    public Customer createCustomer(Customer customer) {
        if (CustomerDao.isPresentUserName(customer.getUserName())) {
            throw new BusinessException(ReturnNo.CUSTOMER_USERNAMEEXISTS, String.format(ReturnNo.CUSTOMER_USERNAMEEXISTS.getMessage()));
        }
        if (CustomerDao.isPresentMobile(customer.getMobile())) {
            throw new BusinessException(ReturnNo.CUSTOMER_MOBILEEXISTS, String.format(ReturnNo.CUSTOMER_MOBILEEXISTS.getMessage()));
        }
        return CustomerDao.insert(customer);
    }

    /**
     * 查找顾客信息
     * @param id
     * @return
     */
    public Customer findCustomerById(Long id) {
        return CustomerDao.findById(id);
    }

    /**
     * 查找所有顾客
     * @return
     */
    public List<Customer> findAllCustomers() {
        return CustomerDao.findAllCustomers();
    }

    /**
     * 解封
     * @param id
     */
    public void release(Long id) {
        CustomerDao.release(id);
    }

    /**
     * 封禁
     * @param id
     */
    public void ban(Long id) {
        CustomerDao.ban(id);
    }

    public void delete(Long id) {
        CustomerDao.delete(id);
    }
}
