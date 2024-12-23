package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.controller.dto.CartDto;
import cn.edu.xmu.oomall.customer.controller.vo.*;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import cn.edu.xmu.oomall.customer.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;




    /**
     * 查看顾客购物车列表
     * author Wangzening
     * @param user
     */
    @GetMapping("/carts")
    @Transactional(propagation = Propagation.REQUIRED)
    @Audit(departName = "customers")
    public ReturnObject getCartByCustomer(@LoginUser UserDto user,
                                          @RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        List<CartItem> carts = this.cartService.retrieveCartItemByCustomer(user.getId(), page, pageSize);
        List<CartItemVo> vos = carts.stream().map(o -> CloneFactory.copy(new CartItemVo(), o)).collect(Collectors.toList());
        return new ReturnObject(new PageVo<>(vos, page, pageSize));

    }

    /**
     * 顾客将商品加入购物车
     * author Liuzhiwen
     * @param user
     */
    @PostMapping("/carts")
    @Audit(departName = "customers")
    public ReturnObject addCartItem(@RequestBody CartDto cartDto,
                                    @LoginUser UserDto user) {
        CartItem cartItem=CloneFactory.copy(new CartItem(),cartDto);
        CartItem newCartItem = cartService.addCartItem(cartItem, user);
        return new ReturnObject(new CartItemVo(newCartItem));
    }

    /**
     * 顾客修改购物车内的商品数量
     * author Wangzening
     * @param user
     */
    @PutMapping("/cartItems/{cartItemId}")
    @Audit(departName = "customers")
    public ReturnObject updateCartItem(@PathVariable Long cartItemId,
                                       @LoginUser UserDto user,
                                       @RequestBody CartDto cartDto) {
        cartService.updateCartItem(cartItemId, cartDto.getQuantity(), user);
        return new ReturnObject(ReturnNo.OK);
    }


    /**
     * 顾客删除购物车项
     * author Wangzening
     * @param user
     */
    @DeleteMapping("/cartItems/{cartItemId}")
    @Audit(departName = "customers")
    public ReturnObject deleteAddress(@PathVariable Long cartItemId,
                                      @LoginUser UserDto user) {
        cartService.deleteCartItem(cartItemId, user);
        return new ReturnObject(ReturnNo.OK);
    }
}
