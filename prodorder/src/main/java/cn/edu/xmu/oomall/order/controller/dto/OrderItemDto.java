//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.order.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OrderItemDto implements Serializable {
    private Long onsaleId;

    private Integer quantity;

    private Long actId;

    public Long getOnsaleId() {
        return onsaleId;
    }

    public void setOnsaleId(Long onsaleId) {
        this.onsaleId = onsaleId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    private Long couponId;
}
