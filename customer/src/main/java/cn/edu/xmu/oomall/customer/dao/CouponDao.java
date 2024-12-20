package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import cn.edu.xmu.oomall.customer.mapper.*;
import cn.edu.xmu.javaee.core.util.CloneFactory;
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
public class CouponDao {
    private final static Logger logger = LoggerFactory.getLogger(CouponDao.class);
    private final CouponPoMapper couponPoMapper;
    private final static String KEY = "C%d";

    // 根据Id找优惠券
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

    // 根据顾客Id找优惠券
    public List<Coupon> retrieveCouponByCustomer(Long customerId, Integer page, Integer pageSize) throws RuntimeException {
        List<Coupon> couponList;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<CouponPo> poPage = couponPoMapper.findCouponByCustomerId(customerId, pageable);
        if (!poPage.isEmpty()) {
            couponList = poPage.stream()
                    .map(po -> CloneFactory.copy(new Coupon(), po))  // 工厂方法转换为Comment对象
                    .collect(Collectors.toList());
        }
        else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, "顾客无优惠券");
        }
        logger.debug("retrieveCommentByProduct: couponList size = {}", couponList.size());
        return couponList;
    }

    // 查找顾客领取某活动优惠券的数量
    public Long countCouponByActAndCustomer(Long actId, Long customerId) {
        return couponPoMapper.countByActIdAndCustomerId(actId, customerId);
    }

    // 保存优惠券
    public String save(Coupon coupon, UserDto user) throws RuntimeException {
        CouponPo po = CloneFactory.copy(new CouponPo(), coupon);
        coupon.setGmtModified(LocalDateTime.now());
        coupon.setModifier(user);
        couponPoMapper.save(po);
        return String.format(KEY, coupon.getId());
    }

    // 插入优惠券
    public Coupon insert(Coupon coupon, UserDto user) throws RuntimeException {
        CouponPo couponPo = CloneFactory.copy(new CouponPo(), coupon);
        coupon.setCustomerId(user.getId());
        coupon.setGmtCreate(LocalDateTime.now());
        coupon.setCreator(user);
        couponPo.setId(null);
        CouponPo save = this.couponPoMapper.save(couponPo);
        coupon.setId(save.getId());
        return coupon;
    }
}
