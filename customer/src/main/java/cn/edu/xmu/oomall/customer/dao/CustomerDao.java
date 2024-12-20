package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import cn.edu.xmu.oomall.customer.mapper.*;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.mapper.po.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RefreshScope
@RequiredArgsConstructor
public class CustomerDao {
    private final static Logger logger = LoggerFactory.getLogger(CustomerDao.class);
    private final CustomerPoMapper customerPoMapper;
    private final static String KEY = "Cu%d";

    // 根据Id查找顾客
    public Customer findCustomerById(Long customerId) throws RuntimeException {
        Optional<CustomerPo> ret = customerPoMapper.findById(customerId);
        if (ret.isPresent()) {
            CustomerPo po = ret.get();
            Customer res = CloneFactory.copy(new Customer(), po);
            res.setCustomerDao(this);
            return res;
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "顾客", customerId));
        }
    }

    // 判断电话号码是否已注册
    public boolean existByMobile(String mobile) {
        return customerPoMapper.existsByMobile(mobile);
    }

    // 插入新顾客（注册）
    public Customer insert(Customer customer) {
        if(customerPoMapper.existsByUserName(customer.getUserName())) {
            throw new BusinessException(ReturnNo.CUSTOMER_NAMEEXIST, String.format(ReturnNo.CUSTOMER_NAMEEXIST.getMessage()));
        }
        if(customerPoMapper.existsByMobile(customer.getMobile())) {
            throw new BusinessException(ReturnNo.CUSTOMER_MOBILEEXIST, String.format(ReturnNo.CUSTOMER_MOBILEEXIST.getMessage()));
        }
        customer.setGmtCreate(LocalDateTime.now());
        CustomerPo customerPo = CloneFactory.copy(new CustomerPo(), customer);
        customerPo.setId(null);
        CustomerPo save = customerPoMapper.save(customerPo);
        customer.setId(save.getId());
        return customer;
    }

    // 更新顾客信息
    public String save(Customer customer, UserDto user) throws RuntimeException {
        customer.setGmtModified(LocalDateTime.now());
        customer.setModifier(user);
        CustomerPo po = CloneFactory.copy(new CustomerPo(), customer);
        customerPoMapper.save(po);
        return String.format(KEY, customer.getId());
    }
}
