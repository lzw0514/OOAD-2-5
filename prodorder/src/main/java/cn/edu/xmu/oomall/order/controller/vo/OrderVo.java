//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.order.controller.vo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.order.dao.bo.Order;
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

}
