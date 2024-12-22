package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.CartDao;
import cn.edu.xmu.oomall.customer.dao.CouponDao;
import cn.edu.xmu.oomall.customer.mapper.po.CouponActPo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 优惠券活动-限制总数,限制顾客领取时间间隔
 * @author Liuzhiwen
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
@CopyFrom({CouponActPo.class})
public class CouponActConstraintTotal extends CouponAct {


    private Long maxCount;    // 优惠券最大数量

    private Long remainCount; // 优惠券剩余数量

    private Long minInterval; //两次领取最小时间间隔



    @JsonIgnore
    @Setter
    @ToString.Exclude
    private CouponDao couponDao;
    /**
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
        if(remainCount <= 0) {
            throw new BusinessException(ReturnNo.COUPON_FINISH);
        }else {
            Coupon lastestReceiveCoupon=null;
            try{
               lastestReceiveCoupon=couponDao.findLatestReceivedCouponByCustomerAndAct(user.getId(),id);
            } catch (BusinessException e) {   //没有查到顾客在此优惠活动下的优惠券时，直接领取
               remainCount -= 1;
               couponActDao.save(this, user);
               return issueCoupon(user);
            }
            Duration duration = Duration.between(lastestReceiveCoupon.getGmtReceive(),LocalDateTime.now());

            long intervalMinutes = duration.toMinutes();// 获取时间间隔的分钟数

            if(intervalMinutes>minInterval){
                throw new BusinessException(ReturnNo. COUPON_RECLAIM_INTERVAL, String.format(ReturnNo. COUPON_RECLAIM_INTERVAL.getMessage()));
            }
            else{
                remainCount -= 1;
                couponActDao.save(this, user);
                return issueCoupon(user);
            }

        }
    }


    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getName() { return name; } public void setName(String name) { this.name = name; }
    public String getDescription() { return description; } public void setDescription(String description) { this.description = description; }
    public Byte getConstraintType() { return constraintType; } public void setConstraintType(Byte constraintType) { this.constraintType = constraintType; }
    public LocalDateTime getGmtBegin() { return gmtBegin; } public void setGmtBegin(LocalDateTime gmtBegin) { this.gmtBegin = gmtBegin; }
    public LocalDateTime getGmtEnd() { return gmtEnd; } public void setGmtEnd(LocalDateTime gmtEnd) { this.gmtEnd = gmtEnd; }
    public Byte getStatus() { return status; } public void setStatus(Byte status) { this.status = status; }
    public CouponDao getCouponDao() {return couponDao;}public void setCouponDao(CouponDao couponDao) {this.couponDao = couponDao;}
    public Long getMaxCount() {return maxCount;}public void setMaxCount(Long maxCount) {this.maxCount = maxCount;}
    public String getCreatorName() {return creatorName;}public void setCreatorName(String creatorName) {this.creatorName = creatorName;}
    public Long getModifierId() {return modifierId;}public void setModifierId(Long modifierId) {this.modifierId = modifierId;}
    public String getModifierName() {return modifierName;}public void setModifierName(String modifierName) {this.modifierName = modifierName;}
    public LocalDateTime getGmtCreate() {return gmtCreate;}public void setGmtCreate(LocalDateTime gmtCreate) {this.gmtCreate = gmtCreate;}
    public LocalDateTime getGmtModified() {return gmtModified;}public void setGmtModified(LocalDateTime gmtModified) {this.gmtModified = gmtModified;}
    public Long getCreatorId() {return creatorId;}public void setCreatorId(Long creatorId) {this.creatorId = creatorId;}
    public Long getMinInterval() {return minInterval;}public void setMinInterval(Long minInterval) {this.minInterval = minInterval;}

}
