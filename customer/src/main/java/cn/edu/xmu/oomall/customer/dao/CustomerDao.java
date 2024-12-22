package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import cn.edu.xmu.oomall.customer.mapper.*;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.mapper.po.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * author Liuzhiwen
 */
@Repository
@RefreshScope
@RequiredArgsConstructor
@Slf4j
public class CustomerDao {
    private final CustomerPoMapper customerPoMapper;
    private final static String KEY = "Cu%d";
    private final AddressDao addressDao;


    /**
     * 根据Id查找顾客
     * @param customerId
     */
    public Customer findCustomerById(Long customerId) throws RuntimeException {
        Optional<CustomerPo> ret = customerPoMapper.findById(customerId);
        if (ret.isPresent()) {
            CustomerPo po = ret.get();
           return this.build(po);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "顾客", customerId));
        }
    }


    /**
     * 判断电话号码是否已注册
     * @param mobile
     */
    public boolean existByMobile(String mobile) {
        return customerPoMapper.existsByMobile(mobile);
    }


    /**
     * 插入新顾客（注册）
     * @param bo
     */

    public Customer insert(Customer bo) {
        if(customerPoMapper.existsByUserName(bo.getUserName())) {
            throw new BusinessException(ReturnNo.CUSTOMER_NAMEEXIST, String.format(ReturnNo.CUSTOMER_NAMEEXIST.getMessage()));
        }
        if(customerPoMapper.existsByMobile(bo.getMobile())) {
            throw new BusinessException(ReturnNo.CUSTOMER_MOBILEEXIST, String.format(ReturnNo.CUSTOMER_MOBILEEXIST.getMessage()));
        }
        bo.setGmtCreate(LocalDateTime.now());
        bo.setId(null);
        CustomerPo Po = CloneFactory.copy(new CustomerPo(), bo);
        CustomerPo save = customerPoMapper.save(Po);
        bo.setId(save.getId());
        return bo;
    }


    /**
     * 更新顾客信息
     * @param customer
     */
    public String save(Customer customer, UserDto user) throws RuntimeException {
        customer.setGmtModified(LocalDateTime.now());
        customer.setModifier(user);
        CustomerPo po = CloneFactory.copy(new CustomerPo(), customer);
        CustomerPo save=this.customerPoMapper.save(po);
        return String.format(KEY,save.getId());
    }

    /**
     * 获得bo对象
     * @param po
     * @return
     */
    private Customer build(CustomerPo po){
        Customer ret = CloneFactory.copy(new Customer(), po);
        this.build(ret);
        return ret;
    }
    /**
     * 赋予bo对象权限
     * @param bo
     */
    private Customer build(Customer bo){
        bo.setCustomerDao(this);
        bo.setAddressDao(addressDao);
        return bo;
    }
}
