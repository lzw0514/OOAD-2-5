package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.mapper.CartItemPoMapper;
import cn.edu.xmu.oomall.customer.mapper.po.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * author Liuzhiwen
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class CartDao {

    private final CartItemPoMapper cartItemPoMapper;
    private final static String KEY = "CI%d";


    /**
     * 根据Id找购物车项
     * @param cartItemId
     */
    public CartItem findCartItemById(Long cartItemId) throws RuntimeException {
        Optional<CartItemPo> ret = this.cartItemPoMapper.findById(cartItemId);
        if (ret.isPresent()) {
            CartItemPo po = ret.get();
            return this.build(po);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "购物车项", cartItemId));
        }
    }



    /**
     * 查找顾客购物车列表
     * @param customerId
     */
    public List<CartItem> retrieveCartItemByCustomer(Long customerId, Integer page, Integer pageSize) throws RuntimeException {
        List<CartItem> cartItemList;
        Pageable pageable = PageRequest.of(page-1, pageSize);
        Page<CartItemPo> poPage = cartItemPoMapper.findByCustomerId(customerId, pageable);
        if (!poPage.isEmpty()) {
            cartItemList = poPage.stream()
                    .map(po -> CloneFactory.copy(new CartItem(), po))
                    .collect(Collectors.toList());
        }
        else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, "顾客购物车为空");
        }
        log.debug("retrieveCartItemsByProduct: cartItemList size = {}", cartItemList.size());
        return cartItemList;
    }


    /**
     * 查找顾客购物车中某商品的购物车项
     * @param productId
     * @param customerId
     */
    public CartItem findCartItemByProductAndCustomer(Long productId, Long customerId) {
        Optional<CartItemPo> ret = cartItemPoMapper.findByProductIdAndCustomerId(productId, customerId);
        if (ret.isPresent()) {
            CartItemPo po = ret.get();
            return this.build(po);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "用户购物车不包括产品", productId));
        }
    }


    /**
     * 保存购物车项
     * @param cartItem
     * @param user
     */

    public String save(CartItem cartItem, UserDto user) throws RuntimeException {
        cartItem.setGmtModified(LocalDateTime.now());
        cartItem.setModifier(user);
        CartItemPo po = CloneFactory.copy(new CartItemPo(), cartItem);
        CartItemPo save=this.cartItemPoMapper.save(po);
        return String.format(KEY, save.getId());
    }

    /**
     * 插入购物车项
     * @param bo
     * @param user
     */
    public CartItem insert(CartItem bo, UserDto user) throws RuntimeException {
        bo.setId(null);
        bo.setGmtCreate(LocalDateTime.now());
        bo.setCreator(user);
        CartItemPo Po = CloneFactory.copy(new CartItemPo(), bo);
        CartItemPo save = this.cartItemPoMapper.save(Po);
        bo.setId(save.getId());
        return bo;
    }


    /**
     * 根据Id物理删除
     * @param id
     */
    public String delete(Long id) {
        this.cartItemPoMapper.deleteById(id);
        return String.format(KEY, id);
    }
    /**
     * 获得bo对象
     * @param po
     * @return
     */
    private CartItem build(CartItemPo po){
        CartItem ret = CloneFactory.copy(new CartItem(), po);
        this.build(ret);
        return ret;
    }

    /**
     * 赋予bo对象权限
     * @param bo
     */
    private CartItem build(CartItem bo){
        bo.setCartDao(this);
        return bo;
    }

}

