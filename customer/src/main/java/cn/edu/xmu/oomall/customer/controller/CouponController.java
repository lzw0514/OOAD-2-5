package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.oomall.customer.controller.vo.CouponActVo;
import cn.edu.xmu.oomall.customer.controller.vo.CouponVo;
import cn.edu.xmu.oomall.customer.dao.bo.Coupon;
import cn.edu.xmu.oomall.customer.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠券控制器
 * @author Liuzhiwen
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
@Slf4j
public class CouponController {

    private final CouponService couponService;

    /**
     * 查看顾客优惠券列表
     * @param customerId
     * @param page
     * @param pageSize
     * @return
     */

    @GetMapping("/customers/{customerId}/coupons")
    @Transactional(propagation = Propagation.REQUIRED)
    @Audit(departName = "customers")
    public ReturnObject getCouponsByCustomer(@PathVariable Long customerId,
                                             @RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Coupon> coupons = this.couponService.findCouponByCustomerId(customerId, page, pageSize);
        List<CouponVo> vos = coupons.stream().map(o -> CloneFactory.copy(new CouponVo(), o)).collect(Collectors.toList());
        return new ReturnObject(new PageVo<>(vos, page, pageSize));
    }

    /**
     * 查看某一优惠券详情
     * @param couponId
     * @return
     */
    @GetMapping("/coupons/{couponId}")
    @Audit(departName = "customers")
    public ReturnObject getCouponDetail(@PathVariable Long couponId) {
        Coupon coupon = this.couponService.findCouponById(couponId);
        return new ReturnObject(new CouponVo(coupon));
    }

    /**
     * 顾客领取某活动的一张优惠券
     * @param actId
     * @param user
     * @return
     */

    @PostMapping("/couponActs/{actId}/coupon")
    @Audit(departName = "customers")
    public ReturnObject claimCoupons(@PathVariable Long actId,
                                              @LoginUser UserDto user) {
        Coupon res = this.couponService.claimCoupon(actId, user);
        return new ReturnObject(new CouponVo(res));
    }
}
