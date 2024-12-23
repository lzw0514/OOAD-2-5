//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.order.controller.vo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.order.dao.bo.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@CopyFrom(OrderItem.class)
public class OrderItemVo {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "商品销售不能为空")
    private Long onsaleId;

    @Min(value = 1, message = "至少购买一个")
    private Integer quantity;

    private Long actId;

    public @NotNull(message = "商品销售不能为空") Long getOnsaleId() {
        return onsaleId;
    }

    public void setOnsaleId(@NotNull(message = "商品销售不能为空") Long onsaleId) {
        this.onsaleId = onsaleId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public @Min(value = 1, message = "至少购买一个") Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@Min(value = 1, message = "至少购买一个") Integer quantity) {
        this.quantity = quantity;
    }

    private Long couponId;
}

