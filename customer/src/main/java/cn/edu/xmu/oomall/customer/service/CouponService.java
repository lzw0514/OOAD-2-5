package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
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
public class CouponService {
    private static final Logger logger = LoggerFactory.getLogger(CouponService.class);

    private final CouponDao couponDao;
    private final CouponActDao couponActDao;

    // 根据id查找优惠券
    public Coupon findCouponById(Long couponId) {
        logger.debug("findCouponById: id = {}", couponId);
        return couponDao.findCouponById(couponId);
    }

    // 顾客查看自己的优惠券列表
    public List<Coupon> findCouponByCustomerId(Long customerId, Integer page, Integer pageSize) {
        logger.debug("findCouponByCustomerId: customerId = {}", customerId);
        return couponDao.retrieveCouponByCustomer(customerId, page, pageSize);
    }

    // 顾客领取一张某活动的优惠券
    public Coupon receiveCoupon(Long couponActId, UserDto user) {
        CouponAct couponAct = couponActDao.findCouponActById(couponActId);
        return couponAct.judgeClaimable(user);
    }
}
