package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
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
 * 优惠券活动-限制每人领取数
 * @author Shuyang Xing
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
@CopyFrom({CouponActPo.class})
public class CouponActConstraintUser extends CouponAct {

    private static final Logger logger = LoggerFactory.getLogger(CouponActConstraintUser.class);

    private Long maxPerUser; // 优惠券每人领取最大数量

    @Override
    // 判断优惠券是否可领
    public Coupon judgeClaimable(UserDto user)
    {
        if(!Objects.equals(getStatus(), AVAILABLE)) {
            throw new BusinessException(ReturnNo.STATENOTALLOW, String.format(ReturnNo.STATENOTALLOW.getMessage(), "活动不可用", STATUS_NAMES.get(this.status)));
        }
        Long curCnt = couponDao.countCouponByActAndCustomer(id, user.getId());
        if(curCnt >= maxPerUser) {
            throw new BusinessException(ReturnNo.FIELD_NOTVALID, "请求超过限制");
        }else {
            return issueCoupon(user);
        }
    }

    // Getter and Setter methods
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getName() { return name; } public void setName(String name) { this.name = name; }
    public String getDescription() { return description; } public void setDescription(String description) { this.description = description; }
    public Byte getConstraintType() { return constraintType; } public void setConstraintType(Byte constraintType) { this.constraintType = constraintType; }
    public Long getCreatorId() { return creatorId; } public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }
    public LocalDateTime getGmtBegin() { return gmtBegin; } public void setGmtBegin(LocalDateTime gmtBegin) { this.gmtBegin = gmtBegin; }
    public LocalDateTime getGmtEnd() { return gmtEnd; } public void setGmtEnd(LocalDateTime gmtEnd) { this.gmtEnd = gmtEnd; }
    public Byte getStatus() { return status; } public void setStatus(Byte status) { this.status = status; }
    public CouponDao getCouponDao() {return couponDao;}public void setCouponDao(CouponDao couponDao) {this.couponDao = couponDao;}
    public Long getMaxPerUser() {return maxPerUser;}public void setMaxPerUser(Long maxPerUser) {this.maxPerUser = maxPerUser;}
    public String getCreatorName() {return creatorName;}public void setCreatorName(String creatorName) {this.creatorName = creatorName;}
    public Long getModifierId() {return modifierId;}public void setModifierId(Long modifierId) {this.modifierId = modifierId;}
    public String getModifierName() {return modifierName;}public void setModifierName(String modifierName) {this.modifierName = modifierName;}
    public LocalDateTime getGmtCreate() {return gmtCreate;}public void setGmtCreate(LocalDateTime gmtCreate) {this.gmtCreate = gmtCreate;}
    public LocalDateTime getGmtModified() {return gmtModified;}public void setGmtModified(LocalDateTime gmtModified) {this.gmtModified = gmtModified;}
}
