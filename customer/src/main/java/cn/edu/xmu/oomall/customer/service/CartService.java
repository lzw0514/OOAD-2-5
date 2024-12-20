package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.*;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
public class CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private final CartDao cartDao;

    // 根据id查找购物车项
    public CartItem findCartItemById(Long CartItemId) {
        logger.debug("findCartItemById: id = {}", CartItemId);
        return cartDao.findCartItemById(CartItemId);
    }

    // 顾客查看自己的购物车列表
    public List<CartItem> retrieveCartItemByCustomer(Long customerId, Integer page, Integer pageSize) {
        logger.debug("findCartItemByCustomerId: customerId = {}", customerId);
        return cartDao.retrieveCartItemByCustomer(customerId, page, pageSize);
    }

    // 顾客向购物车加入一定数量某种商品
    public CartItem addCartItem(Long productId, Long quantity, UserDto user) {
        CartItem cartItem = cartDao.findCartItemByProductAndCustomer(productId, user.getId());
        logger.debug("addCartItem: productId = {}", productId);
        if(cartItem != null){
            return cartItem.updateItemQuantity(quantity, user);
        }else {
            cartItem = new CartItem();
            cartItem.setCartDao(cartDao);
            return cartItem.addItem(productId, quantity, user);
        }
    }

    // 顾客修改购物车商品数量
    public CartItem updateCartItem(Long cartItemId, Long quantity, UserDto user) {
        CartItem cartItem = cartDao.findCartItemById(cartItemId);
        logger.debug("updateCartItem: cartItemId = {}", cartItemId);
        return cartItem.updateItemQuantity(quantity, user);
    }

    // 顾客删除购物车中的一项
    public String deleteCartItem(Long cartItemId, UserDto user) {
        logger.debug("deleteCartItem: cartItemId = {}", cartItemId);
        return cartDao.delete(cartItemId);
    }
}


