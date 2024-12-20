//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.customer.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cartItem")
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({CartItem.class})
public class CartItemPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private Long productId;

    private Long quantity;

    // Getter and Setter methods
    public Long getId() {return id;}public void setId(Long id) {this.id = id;}
    public Long getCustomerId() {return customerId;}public void setCustomerId(Long customerId) {this.customerId = customerId;}
    public Long getProductId() {return productId;}public void setProductId(Long productId) {this.productId = productId;}
    public Long getQuantity() {return quantity;}public void setQuantity(Long quantity) {this.quantity = quantity;}
}
