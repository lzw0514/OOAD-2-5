//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.customer.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_cart")
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

    private Long creatorId;
    private String creatorName;
    private Long modifierId;
    private String modifierName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private String spec;   //商品规格
    private String productName;



    public Long getId() {return id;}public void setId(Long id) {this.id = id;}
    public Long getCustomerId() {return customerId;}public void setCustomerId(Long customerId) {this.customerId = customerId;}
    public Long getProductId() {return productId;}public void setProductId(Long productId) {this.productId = productId;}
    public Long getQuantity() {return quantity;}public void setQuantity(Long quantity) {this.quantity = quantity;}
    public String getCreatorName() {return creatorName;}public void setCreatorName(String creatorName) {this.creatorName = creatorName;}
    public Long getModifierId() {return modifierId;}public void setModifierId(Long modifierId) {this.modifierId = modifierId;}
    public String getModifierName() {return modifierName;}public void setModifierName(String modifierName) {this.modifierName = modifierName;}
    public LocalDateTime getGmtCreate() {return gmtCreate;}public void setGmtCreate(LocalDateTime gmtCreate) {this.gmtCreate = gmtCreate;}
    public LocalDateTime getGmtModified() {return gmtModified;}public void setGmtModified(LocalDateTime gmtModified) {this.gmtModified = gmtModified;}
    public Long getCreatorId() {return creatorId;}public void setCreatorId(Long creatorId) {this.creatorId = creatorId;}
    public String getSpec() {return spec;}public void setSpec(String spec) {this.spec = spec;}
    public String getProductName() {return productName;}public void setProductName(String productName) {this.productName = productName;}
}
