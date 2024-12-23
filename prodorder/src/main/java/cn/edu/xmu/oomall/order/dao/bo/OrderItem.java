//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.order.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.oomall.order.controller.dto.OrderItemDto;
import cn.edu.xmu.oomall.order.mapper.po.OrderItemPo;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString(callSuper = true)
@NoArgsConstructor
@CopyFrom({OrderItemDto.class, OrderItemPo.class})
public class OrderItem extends OOMallObject implements Serializable {

    @Builder
    public OrderItem(Long id, Long creatorId, String creatorName, Long modifierId, String modifierName, LocalDateTime gmtCreate, LocalDateTime gmtModified, Long orderId, Long onsaleId, Integer quantity, Long price, Long discountPrice, Long point, String name, Long actId, Long couponId, Byte commented) {
        super(id, creatorId, creatorName, modifierId, modifierName, gmtCreate, gmtModified);
        this.orderId = orderId;
        this.onsaleId = onsaleId;
        this.quantity = quantity;
        this.price = price;
        this.discountPrice = discountPrice;
        this.point = point;
        this.name = name;
        this.actId = actId;
        this.couponId = couponId;
        this.commented = commented;
    }


    private Long orderId;


    private Long onsaleId;


    private Integer quantity;


    private Long price;


    private Long discountPrice;


    private Long point;


    private String name;


    private Long actId;


    private Long couponId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Byte getCommented() {
        return commented;
    }

    public void setCommented(Byte commented) {
        this.commented = commented;
    }

    private Byte commented;

    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {

    }

    @Override
    public void setGmtModified(LocalDateTime gmtModified) {

    }
}
