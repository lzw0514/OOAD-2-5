//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.customer.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.customer.dao.bo.Coupon;
import cn.edu.xmu.oomall.customer.dao.bo.CouponActConstraintTotal;
import cn.edu.xmu.oomall.customer.dao.bo.CouponActConstraintUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_act")
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({CouponActConstraintUser.class, CouponActConstraintTotal.class})
public class CouponActPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String name;

    protected String description;

    protected Byte constraintType; // 1-限制领取总数 2-限制顾客领取数

    protected LocalDateTime gmtBegin; // 优惠券活动开始时间

    protected LocalDateTime gmtEnd; // 优惠券活动结束时间

    protected Byte status; // 0-活动失效 1-活动有效

    private Long maxPerUser; // 优惠券每人领取最大数量

    private Long maxCount; // 优惠券最大数量

    private Long remainCount; // 优惠券剩余数量

    protected Long creatorId;
    private String creatorName;
    private Long modifierId;
    private String modifierName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    // Getter and Setter methods
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getName() { return name; } public void setName(String name) { this.name = name; }
    public String getDescription() { return description; } public void setDescription(String description) { this.description = description; }
    public Byte getConstraintType() { return constraintType; } public void setConstraintType(Byte constraintType) { this.constraintType = constraintType; }
    public LocalDateTime getGmtBegin() { return gmtBegin; } public void setGmtBegin(LocalDateTime gmtBegin) { this.gmtBegin = gmtBegin; }
    public LocalDateTime getGmtEnd() { return gmtEnd; } public void setGmtEnd(LocalDateTime gmtEnd) { this.gmtEnd = gmtEnd; }
    public Byte getStatus() { return status; } public void setStatus(Byte status) { this.status = status; }
    public Long getMaxPerUser() {return maxPerUser;}public void setMaxPerUser(Long maxPerUser) {this.maxPerUser = maxPerUser;}
    public Long getMaxCount() {return maxCount;}public void setMaxCount(Long maxCount) {this.maxCount = maxCount;}
    public String getCreatorName() {return creatorName;}public void setCreatorName(String creatorName) {this.creatorName = creatorName;}
    public Long getModifierId() {return modifierId;}public void setModifierId(Long modifierId) {this.modifierId = modifierId;}
    public String getModifierName() {return modifierName;}public void setModifierName(String modifierName) {this.modifierName = modifierName;}
    public LocalDateTime getGmtCreate() {return gmtCreate;}public void setGmtCreate(LocalDateTime gmtCreate) {this.gmtCreate = gmtCreate;}
    public LocalDateTime getGmtModified() {return gmtModified;}public void setGmtModified(LocalDateTime gmtModified) {this.gmtModified = gmtModified;}
    public Long getCreatorId() {return creatorId;}public void setCreatorId(Long creatorId) {this.creatorId = creatorId;}
    public Long getRemainCount() {return remainCount;}public void setRemainCount(Long remainCount) {this.remainCount = remainCount;}
}
