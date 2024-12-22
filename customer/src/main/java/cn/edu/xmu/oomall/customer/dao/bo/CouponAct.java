package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 优惠券活动bo对象
 * @author Liuzhiwen
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
public abstract class CouponAct extends OOMallObject implements Serializable {

    protected Long id;

    protected String name;

    protected String description;

    protected Byte constraintType;     // 1-限制领取总数,限制顾客两次领取时间间隔 2-限制顾客领取数，不限制领取总数

    protected Long creatorId;

    protected LocalDateTime gmtBegin;  // 优惠券活动开始时间

    protected LocalDateTime gmtEnd;    // 优惠券活动结束时间

    protected Byte status;             // 0-活动失效 1-活动有效

    @JsonIgnore
    @ToString.Exclude
    protected CouponDao couponDao;

    @JsonIgnore
    @ToString.Exclude
    protected CouponActDao couponActDao;

    @JsonIgnore
    @ToString.Exclude
    protected CustomerDao customerDao;

    // 共2种状态 0-活动失效 1-活动有效
    // 活动失效
    @JsonIgnore
    public static final Byte DISABLED = 0;

    // 活动有效
    @JsonIgnore
    public static final Byte AVAILABLE = 1;

    // 状态和名称的对应
    @JsonIgnore
    public static final Map<Byte, String> STATUS_NAMES = new HashMap(){
        {
            put(AVAILABLE, "活动有效");
            put(DISABLED, "活动失效");
        }
    };

/*    @JsonIgnore
    public String getStatusName(){
        return STATUS_NAMES.get(this.status);
    }

    // 获取所有属于此活动的优惠券
    @JsonIgnore
    public List<Coupon> getActivityCoupon(){
        return null;
    }*/

    /**
     * 判断优惠券是否可领
     * @param user
     * @return
     */
    public abstract Coupon isClaimable(UserDto user);


    /**
     * 顾客领取一张某一活动的优惠券
     * @param user
     * @return
     */
    protected Coupon issueCoupon(UserDto user) {
        Coupon newCoupon = new Coupon();
        newCoupon.setActId(id);
        newCoupon.setName(name);
        newCoupon.setGmtBegin(gmtBegin);
        newCoupon.setGmtEnd(gmtEnd);
        newCoupon.setStatus(AVAILABLE);
        newCoupon.setCustomerId(user.getId());
        return couponDao.insert(newCoupon,user);
    }


    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getName() { return name; } public void setName(String name) { this.name = name; }
    public String getDescription() { return description; } public void setDescription(String description) { this.description = description; }
    public Byte getConstraintType() { return constraintType; } public void setConstraintType(Byte constraintType) { this.constraintType = constraintType; }
    public Long getCreatorId() { return creatorId; } public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }
    public LocalDateTime getGmtBegin() { return gmtBegin; } public void setGmtBegin(LocalDateTime gmtBegin) { this.gmtBegin = gmtBegin; }
    public LocalDateTime getGmtEnd() { return gmtEnd; } public void setGmtEnd(LocalDateTime gmtEnd) { this.gmtEnd = gmtEnd; }
    public Byte getStatus() { return status; } public void setStatus(Byte status) { this.status = status; }
    public CouponDao getCouponDao() {return couponDao;}public void setCouponDao(CouponDao couponDao) {this.couponDao = couponDao;}
    public CouponActDao getCouponActDao() {return couponActDao;}public void setCouponActDao(CouponActDao couponActDao) {this.couponActDao = couponActDao;}
}
