package cn.edu.xmu.oomall.customer.controller.vo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.model.vo.IdNameTypeVo;
import cn.edu.xmu.oomall.customer.dao.CouponDao;
import cn.edu.xmu.oomall.customer.dao.CustomerDao;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;

/**
 * @author Liuzhiwen
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({Coupon.class})
public class CouponVo {

    private Long id;

    private Long customerId;

    private Long actId;

    private String name;

    protected LocalDateTime gmtBegin; // 优惠券开始时间(later(活动开始时间, 用户获取优惠券时间))

    protected LocalDateTime gmtEnd; // 优惠券结束时间

    private Byte status; // 0-不可用 1-可使用 -1-已使用

    public CouponVo(Coupon coupon) {
        super();
        CloneFactory.copy(this, coupon);
    }

    public Long getId() {return id;}public void setId(Long id) {this.id = id;}
    public String getName() {return name;}public void setName(String name) {this.name = name;}
    public LocalDateTime getGmtBegin() {return gmtBegin;}public void setGmtBegin(LocalDateTime gmtBegin) {this.gmtBegin = gmtBegin;}
    public LocalDateTime getGmtEnd() {return gmtEnd;}public void setGmtEnd(LocalDateTime gmtEnd) {this.gmtEnd = gmtEnd;}
    public Byte getStatus() {return status;}public void setStatus(Byte status) {this.status = status;}
    public Long getCustomerId() {return customerId;}public void setCustomerId(Long customerId) {this.customerId = customerId;}
    public Long getActId() {return actId;}public void setActId(Long actId) {this.actId = actId;}
}
