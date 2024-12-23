//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.order.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.order.dao.bo.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom(OrderItem.class)
public class OrderItemPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 创建者id
     */
    private Long creatorId;

    /**
     * 创建者
     */
    private String creatorName;

    /**
     * 修改者id
     */
    private Long modifierId;

    /**
     * 修改者
     */
    private String modifierName;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    private Long orderId;

    private Long onsaleId;

    private Integer quantity;

    private Long price;

    private Long discountPrice;

    private Long point;

    private String name;

    private Long couponId;

    private Byte commented;

    public Long getId() {
        return id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public String getModifierName() {
        return modifierName;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getOnsaleId() {
        return onsaleId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public Long getPrice() {
        return price;
    }

    public Long getPoint() {
        return point;
    }

    public String getName() {
        return name;
    }



    public Byte getCommented() {
        return commented;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public void setOnsaleId(Long onsaleId) {
        this.onsaleId = onsaleId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public void setCommented(Byte commented) {
        this.commented = commented;
    }
}
