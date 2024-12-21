package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import cn.edu.xmu.oomall.customer.mapper.CouponActPoMapper;
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
public class CouponActDao {
    private final static Logger logger = LoggerFactory.getLogger(CouponActDao.class);
    private final CouponActPoMapper couponActPoMapper;
    private final static String KEY = "CA%d";

    // 根据Id找优惠券活动
    public CouponAct findCouponActById(Long couponActId) throws RuntimeException {
        Optional<CouponActPo> ret = this.couponActPoMapper.findById(couponActId);
        if (ret.isPresent()) {
            CouponActPo po = ret.get();
            // 使用工厂返回不同子类的对象
            CouponAct res = CouponActFactory.build(po);
            res.setCouponActDao(this);
            return res;
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "优惠券活动", couponActId));
        }
    }

    // 查询所有可用的优惠券活动
    public List<CouponAct> retrieveOnlineCouponAct(Integer page, Integer pageSize) {
        List<CouponAct> couponActList;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<CouponActPo> poPage = couponActPoMapper.findByStatus((byte) 1, pageable);
        if (!poPage.isEmpty()) {
            couponActList = poPage.stream()
                    .map(po -> CouponActFactory.build(po))
                    .collect(Collectors.toList());
        }
        else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, "无可用的优惠券活动");
        }
        logger.debug("retrieveOnlineCouponAct: couponActList size = {}", couponActList.size());
        return couponActList;
    }

    // 通过 Switch 判断产生何种类型的bo
    public static class CouponActFactory {
        public static CouponAct build(CouponActPo po) {
            switch (po.getConstraintType()) {
                case 1:
                    return CloneFactory.copy(new CouponActConstraintTotal(), po);
                case 2:
                    return CloneFactory.copy(new CouponActConstraintUser(), po);
                default:
                    throw new IllegalArgumentException("Unknown couponAct type");
            }
        }
    }

    // 保存不同类型的子类
    public String save(CouponAct couponAct, UserDto user) throws RuntimeException {
        CouponActPo po;
        couponAct.setGmtModified(LocalDateTime.now());
        couponAct.setModifier(user);
        if (couponAct instanceof CouponActConstraintTotal) {
            CouponActConstraintTotal obj = (CouponActConstraintTotal) couponAct;
            po = CloneFactory.copy(new CouponActPo(), obj);
        } else if (couponAct instanceof CouponActConstraintUser) {
            CouponActConstraintUser obj = (CouponActConstraintUser) couponAct;
            po = CloneFactory.copy(new CouponActPo(), obj);
        } else{
            throw new IllegalArgumentException("Unknown couponAct type");
        }
        couponActPoMapper.save(po);
        return String.format(KEY, couponAct.getId());
    }

    // 插入不同类型的子类
    public CouponAct insert(CouponAct couponAct, UserDto user) throws RuntimeException {
        CouponActPo couponActPo;
        couponAct.setGmtCreate(LocalDateTime.now());
        couponAct.setCreator(user);
        if (couponAct instanceof CouponActConstraintTotal) {
            CouponActConstraintTotal obj = (CouponActConstraintTotal) couponAct;
            couponActPo = CloneFactory.copy(new CouponActPo(), obj);
        } else if (couponAct instanceof CouponActConstraintUser) {
            CouponActConstraintUser obj = (CouponActConstraintUser) couponAct;
            couponActPo = CloneFactory.copy(new CouponActPo(), obj);
        } else{
            throw new IllegalArgumentException("Unknown couponAct type");
        }
        couponActPo.setId(null);
        CouponActPo save = this.couponActPoMapper.save(couponActPo);
        couponAct.setId(save.getId());
        return couponAct;
    }
}
