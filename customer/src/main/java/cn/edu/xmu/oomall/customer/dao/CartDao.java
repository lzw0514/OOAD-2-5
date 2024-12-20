package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.mapper.CartPoMapper;
import cn.edu.xmu.oomall.customer.mapper.po.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RefreshScope
@RequiredArgsConstructor
public class CartDao {
    private final static Logger logger = LoggerFactory.getLogger(CartDao.class);
    private final CartPoMapper cartPoMapper;
    private final static String KEY = "CI%d";

    // 根据Id找购物车项
    public CartItem findCartItemById(Long cartItemId) throws RuntimeException {
        Optional<CartItemPo> ret = this.cartPoMapper.findById(cartItemId);
        if (ret.isPresent()) {
            CartItemPo po = ret.get();
            CartItem res = CloneFactory.copy(new CartItem(), po);
            res.setCartDao(this);
            return res;
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "购物车项", cartItemId));
        }
    }

    // 查找顾客购物车列表
    public List<CartItem> retrieveCartItemByCustomer(Long customerId, Integer page, Integer pageSize) throws RuntimeException {
        List<CartItem> cartItemList;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<CartItemPo> poPage = cartPoMapper.findCartListByCustomerId(customerId, pageable);
        if (!poPage.isEmpty()) {
            cartItemList = poPage.stream()
                    .map(po -> CloneFactory.copy(new CartItem(), po))  // 工厂方法转换为Comment对象
                    .collect(Collectors.toList());
        }
        else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, "顾客购物车为空");
        }
        logger.debug("retrieveCommentByProduct: cartItemList size = {}", cartItemList.size());
        return cartItemList;
    }

    // 查找顾客购物车中某商品的购物车项
    public CartItem findCartItemByProductAndCustomer(Long productId, Long customerId) {
        Optional<CartItemPo> ret = cartPoMapper.findItemByProductIdAndCustomerId(productId, customerId);
        if (ret.isPresent()) {
            CartItemPo po = ret.get();
            CartItem res = CloneFactory.copy(new CartItem(), po);
            res.setCartDao(this);
            return res;
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "用户购物车不包括产品", productId));
        }
    }

    // 保存购物车项
    public String save(CartItem cartItem, UserDto user) throws RuntimeException {
        cartItem.setGmtModified(LocalDateTime.now());
        cartItem.setModifier(user);
        CartItemPo po = CloneFactory.copy(new CartItemPo(), cartItem);
        cartPoMapper.save(po);
        return String.format(KEY, cartItem.getId());
    }

    // 插入购物车项
    public CartItem insert(CartItem cartItem, UserDto user) throws RuntimeException {
        cartItem.setCustomerId(user.getId());
        cartItem.setGmtCreate(LocalDateTime.now());
        cartItem.setCreator(user);
        CartItemPo CartItemPo = CloneFactory.copy(new CartItemPo(), cartItem);
        CartItemPo.setId(null);
        CartItemPo save = this.cartPoMapper.save(CartItemPo);
        cartItem.setId(save.getId());
        return cartItem;
    }

    // 根据Id物理删除
    public String delete(Long id) {
        this.cartPoMapper.deleteById(id);
        return String.format(KEY, id);
    }
}

