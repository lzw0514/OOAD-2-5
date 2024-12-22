package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.controller.dto.CustomerDto;
import cn.edu.xmu.oomall.customer.dao.*;
import cn.edu.xmu.oomall.customer.mapper.AddressPoMapper;
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
@Slf4j
public class Customer extends OOMallObject implements Serializable{

    private Long id;

    private String userName;

    private String password;

    private String mobile;

    private String name;

    private Byte status = 1; // 初始为正常状态

    private Double point = 0d;

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

    /**
     * 顾客修改密码
     * @param newPwd
     * @param user
     * @return
     * @throws RuntimeException
     */
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


    /**
     * 顾客修改个人信息
     * @param newcustomer
     * @param user
     * @return
     * @throws RuntimeException
     */
    public String changeMyselfInfo(Customer newcustomer, UserDto user) throws RuntimeException {
        if (!Objects.equals(getStatus(), 1)){
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "顾客", this.id, "账户无效"));
        }
        if(customerDao.existByMobile(newcustomer.getMobile())){
            throw new BusinessException(ReturnNo.CUSTOMER_MOBILEEXIST, String.format(ReturnNo.CUSTOMER_MOBILEEXIST.getMessage()));
        }
        setMobile(newcustomer.getMobile());
        setUserName(newcustomer.getUserName());
        return customerDao.save(this, user);
    }


    @JsonIgnore
    @Setter
    @ToString.Exclude
    private AddressDao addressDao;

    /**
     * 新增收货地址，地址簿上限为20
     * author Fengjianhao
     * @param newAddress
     * @param user
     * @return
     */
    public Address addAddress(Address newAddress, UserDto user)
    {
        Long cnt = addressDao.countAddressByCustomerId(id);
        if (cnt >= 20) {
            throw new BusinessException(ReturnNo.ADDRESS_OUTLIMIT, String.format(ReturnNo.ADDRESS_OUTLIMIT.getMessage()));
        }
        else{
            newAddress.setCustomerId(id);
            return addressDao.insert(newAddress,user);
        }
    }

    /**
     * 设为默认地址
     * author Linqihang
     * @param address
     * @param user
     * @return
     */
    public String setDefaultAddress(Address address,UserDto user){
        Address defaultAddress;
        try {
            defaultAddress = addressDao.findDefaultAddressByCustomer(id);
        } catch (BusinessException e) {
            log.debug("用户之前未设置默认地址: customerId = {}", user.getId());
            address.setBeDefault(true);
            return addressDao.save(address, user);
        }
        address.setBeDefault(true);
        defaultAddress.setBeDefault(false);
        addressDao.save(defaultAddress, user);
        return addressDao.save(address, user);
    }

    /**
     * 管理员解封顾客
     * author Linqihang
     * @param customer
     * @param user
     */
    public void releaseCustomer(Customer customer,UserDto user){
        if(!Objects.equals(status, 0)){
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "顾客", id, "账户无效"));
        }
        customer.setStatus((byte) 1);
        customerDao.save(customer, user);
    }

    /**
     * 管理员封禁顾客
     * author Linqihang
     * @param customer
     * @param user
     */
    public void banCustomer(Customer customer,UserDto user){
        if(!Objects.equals(status, 1)){
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "顾客", id, "账户无效"));
        }
        customer.setStatus((byte) 0);
        customerDao.save(customer, user);
    }

    @JsonIgnore
    @Setter
    @ToString.Exclude
    private CartDao cartDao;

    /**
     * 将商品加入购物车
     * author Liuzhiwen
     * @param cartItem
     * @param user
     */
    public CartItem addCartItemToCart(CartItem cartItem, UserDto user){
        try {
            cartItem = cartDao.findCartItemByProductAndCustomer(cartItem.getProductId(),id);
        }catch (BusinessException e) {
            cartItem.setCustomerId(id);
            return cartDao.insert(cartItem, user);
        }
        return cartItem.updateItemQuantity(cartItem.getQuantity(), user);
    }


    public Long getId() {return id;}public void setId(Long id) {
        this.id = id;}
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
