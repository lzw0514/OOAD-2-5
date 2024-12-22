package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.mapper.AddressPoMapper;
import cn.edu.xmu.oomall.customer.mapper.po.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



/**
 * author Liuzhiwen
 */
@Repository
@RefreshScope
@RequiredArgsConstructor
@Slf4j
public class AddressDao {

    private final AddressPoMapper addressPoMapper;
    private final static String KEY = "AD%d";


    /**
     * 根据Id找顾客地址
     * @param addressId
     */
    public Address findAddressById(Long addressId) throws RuntimeException {
        Optional<AddressPo> ret = this.addressPoMapper.findById(addressId);
        if (ret.isPresent()) {
            AddressPo po = ret.get();
            return this.build(po);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "地址", addressId));
        }
    }


    /**
     * 查找顾客地址列表
     * @param customerId
     */

    public List<Address> retrieveAddressByCustomerId(Long customerId, Integer page, Integer pageSize) throws RuntimeException {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        Page<AddressPo> poPage = addressPoMapper.findAddressListByCustomerId(customerId, pageable);
        if (!poPage.isEmpty()) {
           return poPage.stream().map(po -> CloneFactory.copy(new Address(), po)).collect(Collectors.toList());
        }
        else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, "顾客地址为空");
        }
    }


    /**
     * 查找顾客默认地址
     * @param customerId
     */
    public Address findDefaultAddressByCustomer(Long customerId) {
        Optional<AddressPo> ret = addressPoMapper.findByCustomerIdAndBeDefault(customerId, true);
        if (ret.isPresent()) {
            AddressPo po = ret.get();
            return this.build(po);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "顾客无默认地址", customerId));
        }
    }


    /**
     * 保存地址
     * @param address
     * @param user
     */
    public String save(Address address, UserDto user) throws RuntimeException {
        address.setGmtModified(LocalDateTime.now());
        address.setModifier(user);
        AddressPo po = CloneFactory.copy(new AddressPo(), address);
        AddressPo save=this.addressPoMapper.save(po);
        return String.format(KEY, save.getId());
    }


    /**
     * 插入地址
     * @param bo
     * @param user
     */
    public Address insert(Address bo, UserDto user) throws RuntimeException {
        bo.setId(null);
        bo.setGmtCreate(LocalDateTime.now());
        bo.setCreator(user);
        AddressPo Po = CloneFactory.copy(new AddressPo(), bo);
        AddressPo save = this.addressPoMapper.save(Po);
        bo.setId(save.getId());
        return bo;
    }


    /**
     * 顾客物理删除收货地址
     * @param id
     */
    public String delete(Long id) {
        this.addressPoMapper.deleteById(id);
        return String.format(KEY, id);
    }

    /**
     * 获得bo对象
     * @param po
     * @return
     */
    private Address build(AddressPo po){
        Address ret = CloneFactory.copy(new Address(), po);
        this.build(ret);
        return ret;
    }

    /**
     * 赋予bo对象权限
     * @param bo
     */
    private Address build(Address bo){
        bo.setAddressDao(this);
        return bo;
    }

    public Long countAddressByCustomerId(Long CustomerId){
        return addressPoMapper.countByCustomerId(CustomerId);
    }

}
