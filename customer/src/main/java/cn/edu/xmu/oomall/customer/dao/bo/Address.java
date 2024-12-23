package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.controller.dto.AddressDto;
import cn.edu.xmu.oomall.customer.dao.*;
import cn.edu.xmu.oomall.customer.mapper.po.AddressPo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
@CopyFrom({AddressPo.class, AddressDto.class})
public class Address extends OOMallObject implements Serializable{
    private Long id;

    private Long customerId;

    private Long regionId;

    private String mobile;

    private String consignee;

    private String detailAddress;

    private boolean beDefault = false;

    private AddressDao addressDao;



    /**
     * 变更地址信息
     * author Fengjianhao
     * @param info
     * @param user
     */
    public Address updateInfo(AddressDto info, UserDto user) {
        setConsignee(info.getConsignee());
        setDetailAddress(info.getDetailAddress());
        setMobile(info.getMobile());
        setRegionId(info.getRegionId());
        addressDao.save(this, user);
        return this;
    }



    public Long getId() {return id;}public void setId(Long id) {this.id = id;}
    public String getConsignee() {return consignee;}public void setConsignee(String consignee) {this.consignee = consignee;}
    public Long getCustomerId() {return customerId;}public void setCustomerId(Long customerId) {this.customerId = customerId;}
    public Long getRegionId() {return regionId;}public void setRegionId(Long regionId) {this.regionId = regionId;}
    public String getMobile() {return mobile;}public void setMobile(String mobile) {this.mobile = mobile;}
    public String getDetailAddress() {return detailAddress;}public void setDetailAddress(String detailAddress) {this.detailAddress = detailAddress;}
    public boolean isBeDefault() {return beDefault;}public void setBeDefault(boolean beDefault) {this.beDefault = beDefault;}
    public AddressDao getAddressDao() {return addressDao;}public void setAddressDao(AddressDao addressDao) {this.addressDao = addressDao;}
    public String getCreatorName() {return creatorName;}public void setCreatorName(String creatorName) {this.creatorName = creatorName;}
    public Long getModifierId() {return modifierId;}public void setModifierId(Long modifierId) {this.modifierId = modifierId;}
    public String getModifierName() {return modifierName;}public void setModifierName(String modifierName) {this.modifierName = modifierName;}
    public LocalDateTime getGmtCreate() {return gmtCreate;}public void setGmtCreate(LocalDateTime gmtCreate) {this.gmtCreate = gmtCreate;}
    public LocalDateTime getGmtModified() {return gmtModified;}public void setGmtModified(LocalDateTime gmtModified) {this.gmtModified = gmtModified;}
    public Long getCreatorId() {return creatorId;}public void setCreatorId(Long creatorId) {this.creatorId = creatorId;}
}
