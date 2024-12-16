package cn.edu.xmu.oomall.customer.controller;
import customer.controller.dto.CartsDto;
import customer.controller.vo.CartsVo;
import cn.edu.xmu.oomall.customer.service.CartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/cart")
public class CartsController {

    @Autowired
    private CartsService cartsService;

    @PostMapping("/add")
    public CartsVo addToCart(@RequestHeader("authorization") String token, @RequestBody CartsDto cartsDto) {
        // 假设通过 token 获取用户id
        int userId = getUserIdFromToken(token);

        try {
            cartsService.addCartItem(userId, cartsDto.getProductId(), cartsDto.getQuantity());
            return new CartsVo(0, "成功", null);  // 返回成功的响应
        } catch (ProductException e) {
            return new CartsVo(e.getErrorCode(), e.getErrorMessage(), null);  // 返回失败的响应
        }
    }

    private int getUserIdFromToken(String token) {
        // 从 token 中解析出用户 ID，这里省略实现细节
        return 1;  // 示例返回1
    }
}
