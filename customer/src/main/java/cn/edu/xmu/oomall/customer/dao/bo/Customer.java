package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.controller.dto.CustomerDto;
import cn.edu.xmu.oomall.customer.dao.*;
import cn.edu.xmu.oomall.customer.mapper.po.CustomerPo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({CustomerPo.class, CustomerDto.class})
public class Customer extends OOMallObject implements Serializable{

    private Long Id;

    private String userName;

    private String password;

    private String mobile;

    private String name;

    private Byte status = 1; // 初始为正常状态

    private Double point;

    @JsonIgnore
    @ToString.Exclude
    private List<Coupon> couponList;

    @JsonIgnore
    @ToString.Exclude
    private List<Address> addressList;

    @JsonIgnore
    @ToString.Exclude
    private List<CartItem> cartItemList;

    @JsonIgnore
    @ToString.Exclude
    protected CustomerDao customerDao;

    // 共3种状态 0-被封禁 1-正常 -1-已注销
    // 正常
    @JsonIgnore
    public static final  Byte VALID = 1;

    // 被封禁
    @JsonIgnore
    public static final  Byte INVALID = 0;

    // 已注销
    @JsonIgnore
    public static final  Byte DELETED  = -1;

    // 状态和名称的对应
    @JsonIgnore
    public static final Map<Byte, String> STATUS_NAMES = new HashMap(){
        {
            put(VALID, "正常");
            put(INVALID, "被封禁");
            put(DELETED, "已注销");
        }
    };

    @JsonIgnore
    public String getStatusName(){
        return STATUS_NAMES.get(this.status);
    }

    // 顾客修改密码
    public String updatePwd(String newPwd, UserDto user) throws RuntimeException {
        if (!Objects.equals(getStatus(), 1)){
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "顾客", this.id, "账户无效"));
        }
        if(Objects.equals(newPwd, password)){
            throw new BusinessException(ReturnNo.CUSTOMER_PASSWORDSAME, String.format(ReturnNo.CUSTOMER_PASSWORDSAME.getMessage()));
        }
        password = newPwd;
        return customerDao.save(this, user);
    }

    // 顾客修改电话号码
    public String updateMobile(String newMobile, UserDto user) throws RuntimeException {
        if (!Objects.equals(getStatus(), 1)){
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "顾客", this.id, "账户无效"));
        }
        if(customerDao.existByMobile(newMobile)){
            throw new BusinessException(ReturnNo.CUSTOMER_MOBILEEXIST, String.format(ReturnNo.CUSTOMER_MOBILEEXIST.getMessage()));
        }
        mobile = newMobile;
        return customerDao.save(this, user);
    }

    // Getter and Setter methods
    public Long getId() {return Id;}public void setId(Long id) {Id = id;}
    public String getUserName() {return userName;}public void setUserName(String userName) {this.userName = userName;}
    public String getPassword() {return password;}public void setPassword(String password) {this.password = password;}
    public String getMobile() {return mobile;}public void setMobile(String mobile) {this.mobile = mobile;}
    public String getName() {return name;}public void setName(String name) {this.name = name;}
    public Byte getStatus() {return status;}public void setStatus(Byte status) {this.status = status;}
    public Double getPoint() {return point;}public void setPoint(Double point) {this.point = point;}
    public List<Coupon> getCouponList() {return couponList;}public void setCouponList(List<Coupon> couponList) {this.couponList = couponList;}
    public List<Address> getAddressList() {return addressList;}public void setAddressList(List<Address> addressList) {this.addressList = addressList;}
    public List<CartItem> getCartItemList() {return cartItemList;}public void setCartItemList(List<CartItem> cartItemList) {this.cartItemList = cartItemList;}
    public CustomerDao getCustomerDao() {return customerDao;}public void setCustomerDao(CustomerDao customerDao) {this.customerDao = customerDao;}
    public String getCreatorName() {return creatorName;}public void setCreatorName(String creatorName) {this.creatorName = creatorName;}
    public Long getModifierId() {return modifierId;}public void setModifierId(Long modifierId) {this.modifierId = modifierId;}
    public String getModifierName() {return modifierName;}public void setModifierName(String modifierName) {this.modifierName = modifierName;}
    public LocalDateTime getGmtCreate() {return gmtCreate;}public void setGmtCreate(LocalDateTime gmtCreate) {this.gmtCreate = gmtCreate;}
    public LocalDateTime getGmtModified() {return gmtModified;}public void setGmtModified(LocalDateTime gmtModified) {this.gmtModified = gmtModified;}
    public Long getCreatorId() { return creatorId; } public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }
}
