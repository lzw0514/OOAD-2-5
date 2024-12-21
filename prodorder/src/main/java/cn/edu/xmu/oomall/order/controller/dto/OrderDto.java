package cn.edu.xmu.oomall.order.controller.dto;

import cn.edu.xmu.oomall.order.dao.bo.OrderItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    @Setter
    @Getter
    private String orderSn;

    @Setter
    @Getter
    private Long pid;

    @Setter
    @Getter
    private ConsigneeDto consignee;

    @Setter
    @Getter
    private Long regionId;

    @Setter
    @Getter
    private String address;

    @Setter
    @Getter
    private String mobile;

    @Setter
    @Getter
    private String message;

    @Setter
    private Long activityId;

    @Setter
    private Long packageId;

    @Setter
    @Getter
    private List<OrderItemDto> orderItems;

    @Getter
    @Setter
    private Byte status;



}
