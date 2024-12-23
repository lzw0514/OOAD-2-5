//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.order.controller.vo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.order.dao.bo.Order;
import cn.edu.xmu.oomall.order.dao.bo.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@CopyFrom(Order.class)
public class OrderVo {

    private List<OrderItemVo> items;

    @NotBlank(message = "联系人不能为空")
    private String consignee;

    @NotBlank(message = "地址不能为空")
    private String address;

    @NotNull(message = "地区不能为空")
    private Long regionId;

    @NotBlank(message = "联系电话不能为空")
    private String mobile;

    private String message;

    public @NotBlank(message = "联系电话不能为空") String getMobile() {
        return mobile;
    }

    public void setMobile(@NotBlank(message = "联系电话不能为空") String mobile) {
        this.mobile = mobile;
    }

    public @NotNull(message = "地区不能为空") Long getRegionId() {
        return regionId;
    }

    public void setRegionId(@NotNull(message = "地区不能为空") Long regionId) {
        this.regionId = regionId;
    }

    public @NotBlank(message = "地址不能为空") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "地址不能为空") String address) {
        this.address = address;
    }

    public @NotBlank(message = "联系人不能为空") String getConsignee() {
        return consignee;
    }

    public void setConsignee(@NotBlank(message = "联系人不能为空") String consignee) {
        this.consignee = consignee;
    }

    public List<OrderItemVo> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> orderitems) {
        this.items = orderitems.stream().map(o-> CloneFactory.copy(new OrderItemVo(), o)).toList();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
