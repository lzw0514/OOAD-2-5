package cn.edu.xmu.oomall.customer.dao.bo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
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
 * 购物车项bo对象，每个顾客加入一个购物车项就增加一条记录
 *
 * @author Liuzhiwen
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
@CopyFrom({CartItemPo.class})
public class CartItem extends OOMallObject implements Serializable {

    private Long id;

    private Long customerId;

    private Long productId;

    private Long quantity;

    private CartDao cartDao;

    // 新增购物车项
    public CartItem addItem(Long productId, Long quantity, UserDto user) {
        if (quantity <= 0) {
            throw new BusinessException(ReturnNo.FIELD_NOTVALID, "商品数量需>0");
        }
        setProductId(productId);
        setQuantity(quantity);
        setCustomerId(user.getId());
        return cartDao.insert(this, user);
    }

    // 变更购物车项数量
    public CartItem updateItemQuantity(Long updateQuantity, UserDto user) {
        quantity += updateQuantity;
        if (quantity <= 0) {
            throw new BusinessException(ReturnNo.FIELD_NOTVALID, "商品数量需>0");
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
}
