package cn.edu.xmu.oomall.customer.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDto {

    private int productId;
    private int quantity;

    public int getProductId() {
        return productId;
    }public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
