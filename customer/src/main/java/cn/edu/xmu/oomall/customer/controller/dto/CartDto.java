package cn.edu.xmu.oomall.customer.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDto {

    private Long productId;
    private Long quantity;

    public Long getProductId() {
        return productId;
    }public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Long getQuantity() {
        return quantity;
    }public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
