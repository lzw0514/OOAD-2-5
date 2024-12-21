package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.mapper.AddressPoMapper;
import cn.edu.xmu.oomall.customer.mapper.po.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RefreshScope
@RequiredArgsConstructor
public class AddressDao {
    private final static Logger logger = LoggerFactory.getLogger(AddressDao.class);
    private final AddressPoMapper addressPoMapper;
    private final static String KEY = "AD%d";

    // 根据Id找顾客地址
    public Address findAddressById(Long addressId) throws RuntimeException {
        Optional<AddressPo> ret = this.addressPoMapper.findById(addressId);
        if (ret.isPresent()) {
            AddressPo po = ret.get();
            Address res = CloneFactory.copy(new Address(), po);
            res.setAddressDao(this);
            return res;
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "地址", addressId));
        }
    }

    // 查找顾客地址列表
    public List<Address> retrieveAddressByCustomer(Long customerId, Integer page, Integer pageSize) throws RuntimeException {
        List<Address> addressList;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<AddressPo> poPage = addressPoMapper.findAddressListByCustomerId(customerId, pageable);
        if (!poPage.isEmpty()) {
            addressList = poPage.stream()
                    .map(po -> CloneFactory.copy(new Address(), po))  // 工厂方法转换为Comment对象
                    .collect(Collectors.toList());
        }
        else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, "顾客地址为空");
        }
        logger.debug("retrieveCommentByProduct: addressList size = {}", addressList.size());
        return addressList;
    }

    // 查找顾客默认地址
    public Address findDefaultAddressByCustomer(Long customerId) {
        Optional<AddressPo> ret = addressPoMapper.findByCustomerIdAndBeDefault(customerId, true);
        if (ret.isPresent()) {
            AddressPo po = ret.get();
            Address res = CloneFactory.copy(new Address(), po);
            res.setAddressDao(this);
            return res;
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "顾客无默认地址", customerId));
        }
    }

    // 保存地址
    public String save(Address address, UserDto user) throws RuntimeException {
        address.setGmtModified(LocalDateTime.now());
        address.setModifier(user);
        AddressPo po = CloneFactory.copy(new AddressPo(), address);
        addressPoMapper.save(po);
        return String.format(KEY, address.getId());
    }

    // 插入地址
    public Address insert(Address address, UserDto user) throws RuntimeException {
        Long cnt = addressPoMapper.countByCustomerId(user.getId());
        if (cnt >= 20) {
            throw new BusinessException(ReturnNo.ADDRESS_OUTLIMIT, String.format(ReturnNo.ADDRESS_OUTLIMIT.getMessage()));
        }
        address.setCustomerId(user.getId());
        address.setGmtCreate(LocalDateTime.now());
        address.setCreator(user);
        AddressPo addressPo = CloneFactory.copy(new AddressPo(), address);
        addressPo.setId(null);
        AddressPo save = this.addressPoMapper.save(addressPo);
        address.setId(save.getId());
        return address;
    }

    // 根据Id物理删除
    public String delete(Long id) {
        this.addressPoMapper.deleteById(id);
        return String.format(KEY, id);
    }
}
