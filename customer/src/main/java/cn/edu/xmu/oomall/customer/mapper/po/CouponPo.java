//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.customer.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.customer.dao.CouponDao;
import cn.edu.xmu.oomall.customer.dao.CustomerDao;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_coupon")
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({Coupon.class})
public class CouponPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private Long actId;

    private String name;

    protected LocalDateTime gmtBegin; // 优惠券开始时间(later(活动开始时间, 用户获取优惠券时间))

    protected LocalDateTime gmtEnd; // 优惠券结束时间

    private Byte status; // 0-不可用 1-可使用 -1-已使用

    private Long creatorId;
    private String creatorName;
    private Long modifierId;
    private String modifierName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    // Getter and Setter methods
    public Long getId() {return id;}public void setId(Long id) {this.id = id;}
    public String getName() {return name;}public void setName(String name) {this.name = name;}
    public LocalDateTime getGmtBegin() {return gmtBegin;}public void setGmtBegin(LocalDateTime gmtBegin) {this.gmtBegin = gmtBegin;}
    public LocalDateTime getGmtEnd() {return gmtEnd;}public void setGmtEnd(LocalDateTime gmtEnd) {this.gmtEnd = gmtEnd;}
    public Long getCustomerId() {return customerId;}public void setCustomerId(Long customerId) {this.customerId = customerId;}
    public Byte getStatus() {return status;}public void setStatus(Byte status) {this.status = status;}
    public Long getActId() {return actId;}public void setActId(Long actId) {this.actId = actId;}
    public String getCreatorName() {return creatorName;}public void setCreatorName(String creatorName) {this.creatorName = creatorName;}
    public Long getModifierId() {return modifierId;}public void setModifierId(Long modifierId) {this.modifierId = modifierId;}
    public String getModifierName() {return modifierName;}public void setModifierName(String modifierName) {this.modifierName = modifierName;}
    public LocalDateTime getGmtCreate() {return gmtCreate;}public void setGmtCreate(LocalDateTime gmtCreate) {this.gmtCreate = gmtCreate;}
    public LocalDateTime getGmtModified() {return gmtModified;}public void setGmtModified(LocalDateTime gmtModified) {this.gmtModified = gmtModified;}
    public Long getCreatorId() {return creatorId;}public void setCreatorId(Long creatorId) {this.creatorId = creatorId;}
}
