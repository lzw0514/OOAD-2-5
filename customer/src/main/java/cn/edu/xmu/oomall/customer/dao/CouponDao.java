package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import cn.edu.xmu.oomall.customer.mapper.*;
import cn.edu.xmu.javaee.core.util.CloneFactory;
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
@RefreshScope
@RequiredArgsConstructor
@Slf4j
public class CouponDao {

    private final CouponPoMapper couponPoMapper;
    private final static String KEY = "C%d";


    /**
     * 根据Id找优惠券
     * @param couponId
     * @return
     */
    public Coupon findCouponById(Long couponId) throws RuntimeException {
        Optional<CouponPo> ret = couponPoMapper.findById(couponId);
        if (ret.isPresent()) {
            CouponPo po = ret.get();
            Coupon res = CloneFactory.copy(new Coupon(), po);
            res.setCouponDao(this);
            return res;
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "优惠券", couponId));
        }
    }

    /**
     * 根据customerId找顾客最近一次领取的优惠券
     * @param customerId
     * @return
     */
    public Coupon findLatestReceivedCouponByCustomerAndAct(Long customerId,Long CouponActId) throws RuntimeException {
        Optional<CouponPo> ret = couponPoMapper.findTopByCustomerIdAndActIdOrderByGmtReceiveDesc(customerId,CouponActId);
        if (ret.isPresent()) {
            CouponPo po = ret.get();
            Coupon res = CloneFactory.copy(new Coupon(), po);
            res.setCouponDao(this);
            return res;
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, "顾客无优惠券");
        }
    }

    /**
     * 根据顾客Id找优惠券
     * @param customerId
     * @param page
     * @param pageSize
     * @return
     */
    public List<Coupon> retrieveCouponByCustomer(Long customerId, Integer page, Integer pageSize) throws RuntimeException {
        List<Coupon> couponList;
        Pageable pageable = PageRequest.of(page-1, pageSize);
        Page<CouponPo> poPage = couponPoMapper.findCouponByCustomerId(customerId, pageable);
        if (!poPage.isEmpty()) {
            couponList = poPage.stream()
                    .map(po -> CloneFactory.copy(new Coupon(), po))
                    .collect(Collectors.toList());
        }
        else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, "顾客无优惠券");
        }
        log.debug("retrieveCommentByProduct: couponList size = {}", couponList.size());
        return couponList;
    }


    /**
     * 查找顾客领取某活动优惠券的数量
     * @param actId
     * @param customerId
     * @return
     */
    public Long countCouponByActAndCustomer(Long actId, Long customerId) {
        return couponPoMapper.countByActIdAndCustomerId(actId, customerId);
    }


    /**
     * 保存优惠券
     * @param coupon
     * @param user
     * @return
     */
    public String save(Coupon coupon, UserDto user) throws RuntimeException {
        CouponPo po = CloneFactory.copy(new CouponPo(), coupon);
        coupon.setGmtModified(LocalDateTime.now());
        coupon.setModifier(user);
        couponPoMapper.save(po);
        return String.format(KEY, coupon.getId());
    }

    /**
     * 插入优惠券
     * @param bo
     * @param user
     * @return
     */
    public Coupon insert(Coupon bo, UserDto user) throws RuntimeException {
        bo.setCustomerId(user.getId());
        bo.setId(null);
        bo.setGmtCreate(LocalDateTime.now());
        bo.setCreator(user);
        CouponPo Po = CloneFactory.copy(new CouponPo(), bo);
        CouponPo save = this.couponPoMapper.save(Po);
        bo.setId(save.getId());
        return bo;
    }
}
