package cn.edu.xmu.oomall.customer.controller.vo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.dao.bo.CouponAct;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Shuyang Xing
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({CouponAct.class})
public class CouponActVo {

    protected Long id;

    protected String name;

    protected String description;

    protected LocalDateTime gmtBegin; // 优惠券活动开始时间

    protected LocalDateTime gmtEnd; // 优惠券活动结束时间

    protected Byte status; // 0-活动失效 1-活动有效

    public CouponActVo(CouponAct couponAct) {
        super();
        CloneFactory.copy(this, couponAct);
    }

    public Long getId() {return id;}public void setId(Long id) {this.id = id;}
    public String getName() {return name;}public void setName(String name) {this.name = name;}
    public LocalDateTime getGmtBegin() {return gmtBegin;}public void setGmtBegin(LocalDateTime gmtBegin) {this.gmtBegin = gmtBegin;}
    public LocalDateTime getGmtEnd() {return gmtEnd;}public void setGmtEnd(LocalDateTime gmtEnd) {this.gmtEnd = gmtEnd;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public Byte getStatus() {return status;}public void setStatus(Byte status) {this.status = status;}
}
