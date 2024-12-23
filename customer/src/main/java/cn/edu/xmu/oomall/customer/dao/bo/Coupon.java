package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.*;
import cn.edu.xmu.oomall.customer.mapper.po.CouponPo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 优惠券bo对象，每发放一张优惠券数据库就写入一条新记录
 * @author Liuzhiwen
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
@CopyFrom({CouponPo.class})
public class Coupon extends OOMallObject implements Serializable{

    private Long id;

    private Long customerId;

    private Long actId;

    private String name;

    protected LocalDateTime gmtBegin; // 优惠券开始时间(later(活动开始时间, 用户获取优惠券时间))

    protected LocalDateTime gmtEnd; // 优惠券结束时间

    private Byte status; // 0-不可用 1-可使用 -1-已使用

    protected LocalDateTime gmtReceive; //最近一次领取时间

    @JsonIgnore
    @ToString.Exclude
    protected CouponDao couponDao;

    @JsonIgnore
    @ToString.Exclude
    protected CouponDao couponActDao;

    @JsonIgnore
    @ToString.Exclude
    protected CustomerDao customerDao;

    // 共3种状态 0-不可用 1-可使用 -1-已使用
    // 不可用
    @JsonIgnore
    public static final Byte DISABLED = 0;

    // 未使用
    @JsonIgnore
    public static final Byte AVAILABLE = 1;

    // 已使用
    @JsonIgnore
    public static final Byte USED = -1;

    // 状态和名称的对应
    @JsonIgnore
    public static final Map<Byte, String> STATUS_NAMES = new HashMap(){
        {
            put(AVAILABLE, "可使用");
            put(DISABLED, "不可用");
            put(USED, "已使用");
        }
    };

    public Long getId() {return id;}public void setId(Long id) {this.id = id;}
    public String getName() {return name;}public void setName(String name) {this.name = name;}
    public LocalDateTime getGmtBegin() {return gmtBegin;}public void setGmtBegin(LocalDateTime gmtBegin) {this.gmtBegin = gmtBegin;}
    public LocalDateTime getGmtEnd() {return gmtEnd;}public void setGmtEnd(LocalDateTime gmtEnd) {this.gmtEnd = gmtEnd;}
    public Byte getStatus() {return status;}public void setStatus(Byte status) {this.status = status;}
    public CouponDao getCouponDao() {return couponDao;}public void setCouponDao(CouponDao couponDao) {this.couponDao = couponDao;}
    public CouponDao getCouponActDao() {return couponActDao;}public void setCouponActDao(CouponDao couponActDao) {this.couponActDao = couponActDao;}
    public CustomerDao getCustomerDao() {return customerDao;}public void setCustomerDao(CustomerDao customerDao) {this.customerDao = customerDao;}
    public Long getCustomerId() {return customerId;}public void setCustomerId(Long customerId) {this.customerId = customerId;}
    public Long getActId() {return actId;}public void setActId(Long actId) {this.actId = actId;}
    public String getCreatorName() {return creatorName;}public void setCreatorName(String creatorName) {this.creatorName = creatorName;}
    public Long getModifierId() {return modifierId;}public void setModifierId(Long modifierId) {this.modifierId = modifierId;}
    public String getModifierName() {return modifierName;}public void setModifierName(String modifierName) {this.modifierName = modifierName;}
    public LocalDateTime getGmtCreate() {return gmtCreate;}public void setGmtCreate(LocalDateTime gmtCreate) {this.gmtCreate = gmtCreate;}
    public LocalDateTime getGmtModified() {return gmtModified;}public void setGmtModified(LocalDateTime gmtModified) {this.gmtModified = gmtModified;}
    public Long getCreatorId() {return creatorId;}public void setCreatorId(Long creatorId) {this.creatorId = creatorId;}
    public LocalDateTime getGmtReceive() {return gmtReceive;}public void setGmtReceive(LocalDateTime gmtReceive) {this.gmtReceive = gmtReceive;}
}
