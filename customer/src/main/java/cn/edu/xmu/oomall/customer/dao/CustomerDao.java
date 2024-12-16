package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.controller.vo.CustomerVo;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.mapper.po.CustomerPo;
import cn.edu.xmu.oomall.customer.mapper.CustomerPoMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@RefreshScope
@RequiredArgsConstructor

public class CustomerDao {
    private final static Logger logger = LoggerFactory.getLogger(CustomerDao.class);

    private final CustomerPoMapper CustomerPoMapper;


    /**
     *注册用户
     * @param bo  用户bo
     * @return
     * @throws RuntimeException
     */
    public Customer insert(Customer bo) throws RuntimeException {
        bo.setId(null);
        CustomerPo po = CloneFactory.copy(new CustomerPo(), bo);
        logger.debug("save: po = {}", po);
        po = CustomerPoMapper.save(po);
        bo.setId(po.getId());
        return bo;
    }

    /**
     *查找顾客
     * @param id  顾客id
     * @return
     * @throws RuntimeException
     */
    public Customer findById(Long id) throws RuntimeException {
        CustomerPo po = CustomerPoMapper.findById(id);
        return CloneFactory.copy(new Customer(), po);
    }

    /**
     * 判断userName是否存在
     * @param userName
     * @return
     */
    public boolean isPresentUserName(String userName) {
        Optional<CustomerPo> po = CustomerPoMapper.findByUserName(userName);
        return po.isPresent();
    }

    /**
     * 判断mobile是否存在
     * @param mobile
     * @return
     */
    public boolean isPresentMobile(String mobile) {
        Optional<CustomerPo> po = CustomerPoMapper.findByMobile(mobile);
        return po.isPresent();
    }

    /**
     * 查找所有顾客
     * @return
     */
    public List<Customer> findAllCustomers() {
        List<CustomerPo> poList = CustomerPoMapper.findAll();
        List<Customer> list = new ArrayList<>();
        for (CustomerPo o : poList) {
            Customer customer = CloneFactory.copy(new Customer(), o);
            list.add(customer);
        }
        return list;

    }

    /**
     * 恢复顾客
     * @param id
     */
    public void release(Long id) {
        Customer customer = findById(id);
        customer.release();
        CustomerPo po = CloneFactory.copy(new CustomerPo(), customer);
        CustomerPoMapper.save(po);
    }

    /**
     * 解封顾客
     * @param id
     */
    public void ban(Long id) {
        Customer customer = findById(id);
        customer.ban();
        CustomerPo po = CloneFactory.copy(new CustomerPo(), customer);
        CustomerPoMapper.save(po);
    }

    public void delete(Long id) {
        Customer customer = findById(id);
        customer.delete();
        CustomerPo po = CustomerPoMapper.findById(id);
        CustomerPoMapper.save(po);
    }
}
