package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.*;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * author Liuzhiwen
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Slf4j
public class CouponService {

    private final CouponDao couponDao;
    private final CouponActDao couponActDao;

    /**
     * 根据id查找优惠券
     * @param couponId
     * @return
     */
    public Coupon findCouponById(Long couponId) {
        log.debug("findCouponById: id = {}", couponId);
        return couponDao.findCouponById(couponId);
    }

    /**
     * 顾客查看自己的优惠券列表
     * @param customerId
     * @param page
     * @param pageSize
     * @return
     */

    public List<Coupon> findCouponByCustomerId(Long customerId, Integer page, Integer pageSize) {
        log.debug("findCouponByCustomerId: customerId = {}", customerId);
        return couponDao.retrieveCouponByCustomer(customerId, page, pageSize);
    }

    /**
     * 顾客领取一张某活动的优惠券
     * @param couponActId
     * @param user
     * @return
     */
    public Coupon claimCoupon(Long couponActId, UserDto user) {
        CouponAct couponAct = couponActDao.findCouponActById(couponActId);
        return couponAct.isClaimable(user);
    }
}
