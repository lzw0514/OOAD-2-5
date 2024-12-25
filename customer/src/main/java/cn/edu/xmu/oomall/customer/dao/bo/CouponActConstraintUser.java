package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.CouponActDao;
import cn.edu.xmu.oomall.customer.dao.CouponDao;
import cn.edu.xmu.oomall.customer.mapper.po.CouponActPo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 优惠券活动-不限制优惠券总数，限制每人领取数量
 * @author Liuzhiwen
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
@CopyFrom({CouponActPo.class})
public class CouponActConstraintUser extends CouponAct {


    private Long maxUser; // 优惠券每人领取最大数量

    /**
     * 不限制优惠券总数，限制用户领取数量
     * 判断优惠券是否可领
     * @param user
     * @return
     */
    @Override
    public Coupon isClaimable(UserDto user)
    {
        if(!Objects.equals(getStatus(), AVAILABLE)) {
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "优惠活动", id,STATUS_NAMES.get(this.status)));
        }
        Long curCnt = couponDao.countCouponByActAndCustomer(id, user.getId());
        if(curCnt >= maxUser) {
            throw new BusinessException(ReturnNo.COUPON_UPPER_LIMIT, String.format(ReturnNo. COUPON_UPPER_LIMIT.getMessage()));
        }else {
            return allocateCoupon(user);
        }
    }
    public Coupon allocateCoupon(UserDto user) {
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
    public CouponActDao getCouponActDao() {return couponActDao;}public void setCouponActDao(CouponActDao couponActDao) {this.couponActDao = couponActDao;}
    public CouponDao getCouponDao() {return couponDao;}public void setCouponDao(CouponDao couponDao) {this.couponDao = couponDao;}

    public Long getMaxUser() {return maxUser;}public void setMaxUser(Long maxUser) {this.maxUser = maxUser;}
    public String getCreatorName() {return creatorName;}public void setCreatorName(String creatorName) {this.creatorName = creatorName;}
    public Long getModifierId() {return modifierId;}public void setModifierId(Long modifierId) {this.modifierId = modifierId;}
    public String getModifierName() {return modifierName;}public void setModifierName(String modifierName) {this.modifierName = modifierName;}
    public LocalDateTime getGmtCreate() {return gmtCreate;}public void setGmtCreate(LocalDateTime gmtCreate) {this.gmtCreate = gmtCreate;}
    public LocalDateTime getGmtModified() {return gmtModified;}public void setGmtModified(LocalDateTime gmtModified) {this.gmtModified = gmtModified;}
}
