//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.order.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.oomall.order.controller.dto.OrderDto;
import cn.edu.xmu.oomall.order.mapper.po.OrderPo;
import lombok.*;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ToString(callSuper = true)
@NoArgsConstructor
@CopyFrom({Optional.class, OrderDto.class, OrderPo.class})
public class Order extends OOMallObject {


    @Builder
    public Order(Long id, Long creatorId, String creatorName, Long modifierId, String modifierName, LocalDateTime gmtCreate, LocalDateTime gmtModified, Long customerId, Long shopId, String orderSn, Long pid, String consignee, Long regionId, String address, String mobile, String message, Long activityId, Long packageId, List<OrderItem> orderItems, Byte status) {
        super(id, creatorId, creatorName, modifierId, modifierName, gmtCreate, gmtModified);
        this.customerId = customerId;
        this.shopId = shopId;
        this.orderSn = orderSn;
        this.pid = pid;
        this.consignee = consignee;
        this.regionId = regionId;
        this.address = address;
        this.mobile = mobile;
        this.message = message;
        this.activityId = activityId;
        this.packageId = packageId;
        this.orderItems = orderItems;
        this.status = status;
    }

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long customerId;


    private Long shopId;
    public static final Byte PENDING_PAYMENT = 0;
    public static final Byte PAID = 1;
    public static final Byte SHIPPED = 2;
    public static final Byte COMPLETED = 3;



    private String orderSn;


    private Long pid;


    private String consignee;


    private Long regionId;


    private String address;


    private String mobile;


    private String message;


    private Long activityId;


    private Long packageId;


    private List<OrderItem> orderItems;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    private Byte status;


    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {

    }

    @Override
    public void setGmtModified(LocalDateTime gmtModified) {

    }
}
