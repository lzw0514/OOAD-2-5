package cn.edu.xmu.oomall.customer.controller.vo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * @author Liuzhiwen
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({CartItem.class})
public class CartItemVo {

    private Long id;

    private Long productId;

    private Long quantity;

    public CartItemVo(CartItem cartItem) {
        super();
        CloneFactory.copy(this, cartItem);
    }

    public Long getId() {return id;}public void setId(Long id) {this.id = id;}
    public Long getProductId() {return productId;}public void setProductId(Long productId) {this.productId = productId;}
    public Long getQuantity() {return quantity;}public void setQuantity(Long quantity) {this.quantity = quantity;}
}
