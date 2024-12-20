package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.controller.dto.AddressDto;
import cn.edu.xmu.oomall.customer.dao.*;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
public class AddressService {
    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    private final AddressDao addressDao;

    // 根据id查找地址
    public Address findAddressById(Long addressId) {
        logger.debug("findAddressById: id = {}", addressId);
        return addressDao.findAddressById(addressId);
    }

    // 顾客查看自己的地址列表
    public List<Address> retrieveAddressByCustomer(Long customerId, Integer page, Integer pageSize) {
        logger.debug("findAddressByCustomerId: customerId = {}", customerId);
        return addressDao.retrieveAddressByCustomer(customerId, page, pageSize);
    }

    // 顾客添加新地址
    public Address addAddress(Address newAddress, UserDto user) {
        logger.debug("addAddress: customerId = {}", user.getId());
        return addressDao.insert(newAddress, user);
    }

    // 顾客修改地址信息
    public Address updateAddress(Long addressId, AddressDto newAddress, UserDto user) {
        logger.debug("updateAddress: addressId = {}", addressId);
        Address address = addressDao.findAddressById(addressId);
        return address.updateInfo(newAddress, user);
    }

    // 顾客设置默认地址
    public String changeDefaultAddress(Long addressId, UserDto user) {
        logger.debug("changeDefaultAddress: addressId = {}", addressId);
        Address address = addressDao.findAddressById(addressId);
        return address.changeSelfDefault(user);
    }

    // 顾客删除地址
    public String deleteAddress(Long addressId, UserDto user) {
        logger.debug("deleteAddress: addressId = {}", addressId);
        return addressDao.delete(addressId);
    }
}
