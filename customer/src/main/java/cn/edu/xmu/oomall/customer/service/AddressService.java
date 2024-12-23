package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.controller.dto.AddressDto;
import cn.edu.xmu.oomall.customer.dao.*;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Slf4j
public class AddressService {

    private final AddressDao addressDao;
    private final CustomerDao customerDao;


    /**
     * 根据id查找地址
     * author Fengjianhao
     */
    public Address findAddressById(Long addressId) {
        log.debug("findAddressById: id = {}", addressId);
        return addressDao.findAddressById(addressId);
    }


    /**
     * 顾客查看自己的地址列表
     * author Fengjianhao
     * @param customerId
     */
    public List<Address> retrieveAddressByCustomerId(Long customerId, Integer page, Integer pageSize) {
        log.debug("retrieveAddressByCustomerId: customerId = {}", customerId);
        return addressDao.retrieveAddressByCustomerId(customerId, page, pageSize);
    }


    /**
     * 顾客添加新地址
     * author Fengjianhao
     * @param newAddress
     * @param user
     */
    public Address addAddress(Address newAddress, UserDto user) {
        log.debug("addAddress: customerId = {}", user.getId());
        Customer customer=customerDao.findCustomerById(user.getId());
        return customer.addAddress(newAddress, user);
    }



    /**
     * 顾客修改地址信息
     * author Fengjianhao
     * @param addressId
     * @param newAddress
     * @param user
     */
    public Address updateAddress(Long addressId, AddressDto newAddress, UserDto user) {
        log.debug("updateAddress: addressId = {}", addressId);
        Address address = addressDao.findAddressById(addressId);
        return address.updateInfo(newAddress, user);
    }


    /**
     * 顾客设置默认地址
     * author Fengjianhao
     * @param addressId
     * @param user
     */
    public String setDefaultAddress(Long addressId, UserDto user) {
        log.debug("changeDefaultAddress: addressId = {}", addressId);
        Address address = addressDao.findAddressById(addressId);
        Customer customer=customerDao.findCustomerById(user.getId());
        return customer.setDefaultAddress(address,user);
    }


    /**
     * 顾客删除地址
     * author Fengjianhao
     * @param addressId
     * @param user
     */
    public String deleteAddress(Long addressId, UserDto user) {
        log.debug("deleteAddress: addressId = {}", addressId);
        return addressDao.delete(addressId);
    }
}
