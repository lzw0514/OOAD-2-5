package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.controller.dto.AddressDto;
import cn.edu.xmu.oomall.customer.controller.vo.*;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import cn.edu.xmu.oomall.customer.mapper.po.AddressPo;
import cn.edu.xmu.oomall.customer.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
@Slf4j
public class AddressController {

    private final AddressService addressService;


    /**
     * 查看顾客地址列表
     * author Liuzhiwen
     * @return
     */
    @GetMapping("/address")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject getAddressByCustomer(@LoginUser UserDto user,
                                             @RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "5") Integer pageSize) {
        List<Address> addressList = this.addressService.retrieveAddressByCustomer(user.getId(), page, pageSize);
        return new ReturnObject(new PageVo<>(addressList.stream().map(bo -> AddressVo.builder().id(bo.getId()).regionId(bo.getRegionId()).detailAddress(bo.getDetailAddress()).consignee(bo.getConsignee()).mobile(bo.getMobile()).build()).collect(Collectors.toList()), page, pageSize));
    }

    // 顾客添加新地址
    @PostMapping("/address")
    public ReturnObject addaddress(@LoginUser UserDto user,
                                   @RequestBody AddressDto addressDto) {
        Address newAddress = addressService.addAddress(CloneFactory.copy(new Address(), addressDto), user);
        return new ReturnObject(new AddressVo(newAddress));
    }

    // 顾客修改地址信息
    @PutMapping("/address/{addressId}")
    public ReturnObject updateAddress(@PathVariable Long addressId,
                                      @LoginUser UserDto user,
                                      @RequestBody AddressDto addressDto) {
        addressService.updateAddress(addressId, addressDto, user);
        return new ReturnObject(ReturnNo.OK);
    }

    // 顾客设置默认地址
    @PutMapping("/address/{addressId}/default")
    public ReturnObject changeDefaultAddress(@PathVariable Long addressId,
                                             @LoginUser UserDto user) {
        addressService.changeDefaultAddress(addressId, user);
        return new ReturnObject(ReturnNo.OK);
    }

    // 顾客删除地址
    @DeleteMapping("/address/{addressId}")
    public ReturnObject deleteAddress(@PathVariable Long addressId,
                                      @LoginUser UserDto user) {
        addressService.deleteAddress(addressId, user);
        return new ReturnObject(ReturnNo.OK);
    }
}
