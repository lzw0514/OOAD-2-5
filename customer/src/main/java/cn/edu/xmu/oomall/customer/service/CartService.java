package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.*;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Slf4j
public class CartService {


    private final CartDao cartDao;
    private final CustomerDao customerDao;

    /**
     * 根据id查找购物车项
     * author Wangzening
     * @param productName
     */
    public CartItem findCartItemByProdName(String productName) {
        log.debug("findCartItemByProdName: productName = {}", productName);
        return cartDao.findCartItemByProdName(productName);
    }


    /**
     * 顾客查看自己的购物车列表
     * author Wangzening
     * @param customerId
     */
    public List<CartItem> retrieveCartItemByCustomer(Long customerId, Integer page, Integer pageSize) {
        log.debug("findCartItemByCustomerId: customerId = {}", customerId);
        return cartDao.retrieveCartItemByCustomer(customerId, page, pageSize);
    }

    /**
     * 顾客向购物车加入一定数量某种商品
     * author Liuzhiwen
     * @param cartItem
     */
    public CartItem addCartItem(CartItem cartItem, UserDto user) {
        log.debug("addCartItem: cartItem = {}", cartItem);
        Customer customer=customerDao.findCustomerById(user.getId());
        return customer.addCartItemToCart(cartItem,user);
    }

    /**
     * 顾客修改购物车商品数量
     * author Wangzening
     * @param cartItemId
     * @param quantity
     * @param user
     */

    public CartItem updateCartItem(Long cartItemId, Long quantity, UserDto user) {
        CartItem cartItem = cartDao.findCartItemById(cartItemId);
        log.debug("updateCartItem: cartItemId = {}", cartItemId);
        return cartItem.updateItemQuantity(quantity, user);
    }

    /**
     * 顾客删除购物车中的一项
     * author Wangzening
     * @param cartItemId
     * @param user
     */
    public String deleteCartItem(Long cartItemId, UserDto user) {
        log.debug("deleteCartItem: cartItemId = {}", cartItemId);
        return cartDao.delete(cartItemId);
    }
}


