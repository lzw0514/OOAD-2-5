package cn.edu.xmu.oomall.customer.service;

import cn.edu.xmu.oomall.customer.dao.CouponActDao;
import cn.edu.xmu.oomall.customer.dao.CouponDao;
import cn.edu.xmu.oomall.customer.dao.CustomerDao;
import cn.edu.xmu.oomall.customer.dao.bo.CouponAct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * author Liuzhiwen
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Slf4j
public class CouponActService {

    private final CouponDao couponDao;
    private final CouponActDao couponActDao;
    private final CustomerDao customerDao;


    /**
     * 根据id查找优惠券活动
     * @param couponActId
     * @return
     */
    public CouponAct findCouponActById(Long couponActId) {
        log.debug("findCouponById: id = {}", couponActId);
        return this.couponActDao.findCouponActById(couponActId);
    }

    /**
     * 根据id查找有效优惠券活动
     * @param couponActId
     * @return
     */
    public CouponAct findValidCouponActById(Long couponActId) {
        log.debug("findCouponById: id = {}", couponActId);
        return this.couponActDao.findValidCouponActById(couponActId);
    }

    /**
     * 查找可用的优惠券活动
     * @param page
     * @param pageSize
     * @return
     */

    public List<CouponAct> retrieveOnlineCouponAct(Integer page, Integer pageSize) {
        log.debug("findCouponAct");
        return couponActDao.retrieveOnlineCouponAct(page, pageSize);
    }
}
