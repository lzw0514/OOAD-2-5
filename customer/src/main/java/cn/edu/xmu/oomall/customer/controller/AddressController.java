package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
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
     *查看顾客地址列表
     * author Linqihang
     * @return
     */
    @GetMapping("/address")
    @Transactional(propagation = Propagation.REQUIRED)
    @Audit(departName = "customers")
    public ReturnObject getCustomerAddress(@LoginUser UserDto user,
                                             @RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Address> addressList = this.addressService.retrieveAddressByCustomerId(user.getId(), page, pageSize);
        List<AddressVo> vos = addressList.stream().map(o -> CloneFactory.copy(new AddressVo(), o)).collect(Collectors.toList());
        return new ReturnObject(new PageVo<>(vos, page, pageSize));
    }


    /**
     * 顾客添加新地址
     * author Linqihang
     * @return
     */
    @PostMapping("/address")
    @Audit(departName = "customers")
    public ReturnObject addaddress(@LoginUser UserDto user,
                                   @RequestBody AddressDto addressDto) {
        Address newAddress = addressService.addAddress(CloneFactory.copy(new Address(), addressDto), user);
        return new ReturnObject(ReturnNo.CREATED,new AddressVo(newAddress));
    }

    /**
     * 顾客修改地址信息
     * author Linqihang
     * @param addressId
     * @return
     */
    @PutMapping("/address/{addressId}")
    @Audit(departName = "customers")
    public ReturnObject updateAddress(@PathVariable Long addressId,
                                      @LoginUser UserDto user,
                                      @RequestBody AddressDto addressDto) {
        addressService.updateAddress(addressId, addressDto, user);
        return new ReturnObject(ReturnNo.OK);
    }


    /**
     * 顾客设置默认地址
     * author Linqihang
     * @param addressId
     * @return
     */
    @PutMapping("/address/{addressId}/default")
    @Audit(departName = "customers")
    public ReturnObject setDefaultAddress(@PathVariable Long addressId,
                                             @LoginUser UserDto user) {
        addressService.setDefaultAddress(addressId, user);
        return new ReturnObject(ReturnNo.OK);
    }



    /**
     * 顾客删除地址
     * author Linqihang
     * @param addressId
     * @return
     */
    @DeleteMapping("/address/{addressId}")
    @Audit(departName = "customers")
    public ReturnObject deleteAddress(@PathVariable Long addressId,
                                      @LoginUser UserDto user) {
        addressService.deleteAddress(addressId, user);
        return new ReturnObject(ReturnNo.OK);
    }
}
