package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.controller.dto.CartDto;
import cn.edu.xmu.oomall.customer.dao.*;
import cn.edu.xmu.oomall.customer.mapper.po.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Liuzhiwen
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
@CopyFrom({CartItemPo.class, CartDto.class})
public class CartItem extends OOMallObject implements Serializable {

    private Long id;

    private Long customerId;

    private Long productId;

    private Long quantity;

    private CartDao cartDao;

    private String spec;   //商品规格

    private String productName;

    /**
     * 变更购物车项数量
     * author Liuzhiwen
     * @param updateQuantity
     * @param user
     */
    //updateQuantity只能为1或-1，从商品详情页加入时为1，在购物车列表点击"+"时为1，在购物车列表点击"-"时为-1
    public CartItem updateItemQuantity(Long updateQuantity, UserDto user) {
        quantity += updateQuantity;
        if (quantity <= 0) {
            cartDao.delete(id);    //数量小于0时，将该购物车项从购物车中删除
            throw new BusinessException(ReturnNo.OK, "成功将商品从购物车中删除");
        }
        cartDao.save(this, user);
        return this;
    }

    public Long getId() {return id;}public void setId(Long id) {this.id = id;}
    public Long getCustomerId() {return customerId;}public void setCustomerId(Long customerId) {this.customerId = customerId;}
    public Long getProductId() {return productId;}public void setProductId(Long productId) {this.productId = productId;}
    public Long getQuantity() {return quantity;}public void setQuantity(Long quantity) {this.quantity = quantity;}
    public CartDao getCartDao() {return cartDao;}public void setCartDao(CartDao cartDao) {this.cartDao = cartDao;}
    public String getCreatorName() {return creatorName;}public void setCreatorName(String creatorName) {this.creatorName = creatorName;}
    public Long getModifierId() {return modifierId;}public void setModifierId(Long modifierId) {this.modifierId = modifierId;}
    public String getModifierName() {return modifierName;}public void setModifierName(String modifierName) {this.modifierName = modifierName;}
    public LocalDateTime getGmtCreate() {return gmtCreate;}public void setGmtCreate(LocalDateTime gmtCreate) {this.gmtCreate = gmtCreate;}
    public LocalDateTime getGmtModified() {return gmtModified;}public void setGmtModified(LocalDateTime gmtModified) {this.gmtModified = gmtModified;}
    public Long getCreatorId() {return creatorId;}public void setCreatorId(Long creatorId) {this.creatorId = creatorId;}
    public String getSpec() {return spec;}public void setSpec(String spec) {this.spec = spec;}
    public String getProductName() {return productName;}public void setProductName(String productName) {this.productName = productName;}
}
