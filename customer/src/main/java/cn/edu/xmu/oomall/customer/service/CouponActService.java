package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.CouponActDao;
import cn.edu.xmu.oomall.customer.dao.CouponDao;
import cn.edu.xmu.oomall.customer.dao.CustomerDao;
import cn.edu.xmu.oomall.customer.dao.bo.Coupon;
import cn.edu.xmu.oomall.customer.dao.bo.CouponAct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
public class CouponActService {
    private static final Logger logger = LoggerFactory.getLogger(CouponActService.class);

    private final CouponDao couponDao;
    private final CouponActDao couponActDao;
    private final CustomerDao customerDao;

    // 根据id查找优惠券活动
    public CouponAct findCouponActById(Long couponActId) {
        logger.debug("findCouponById: id = {}", couponActId);
        return this.couponActDao.findCouponActById(couponActId);
    }

    // 查找可用的优惠券活动
    public List<CouponAct> findOnlineCouponAct(Integer page, Integer pageSize) {
        logger.debug("findOnlineCouponAct");
        return couponActDao.retrieveOnlineCouponAct(page, pageSize);
    }
}
