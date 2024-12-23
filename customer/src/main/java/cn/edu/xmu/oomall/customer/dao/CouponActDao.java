package cn.edu.xmu.oomall.customer.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import cn.edu.xmu.oomall.customer.mapper.CouponActPoMapper;
import cn.edu.xmu.oomall.customer.mapper.po.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
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
public class CouponActDao {

    private final CouponActPoMapper couponActPoMapper;

    private final CouponDao couponDao;
    private final static String KEY = "CA%d";



    /**
     * 根据Id找优惠券活动
     * @param couponActId
     * @return
     * @throws RuntimeException
     */
    public CouponAct findCouponActById(Long couponActId) throws RuntimeException {
        Optional<CouponActPo> ret = this.couponActPoMapper.findById(couponActId);
        if (ret.isPresent()) {
            CouponActPo po = ret.get();
            CouponActFactory factory=new CouponActFactory();
            return factory.build(po);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "优惠券活动", couponActId));
        }
    }

    /**
     * 根据Id找有效的优惠券活动
     * @param couponActId
     * @return
     * @throws RuntimeException
     */
    public CouponAct findValidCouponActById(Long couponActId) throws RuntimeException {
        Optional<CouponActPo> ret = this.couponActPoMapper.findByIdAndStatus(couponActId,(byte) 1);
        if (ret.isPresent()) {
            CouponActPo po = ret.get();
            CouponActFactory factory=new CouponActFactory();
            return factory.build(po);
        } else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "优惠券活动", couponActId));
        }
    }


    /**
     * 查询所有可用的优惠券活动
     * @param page
     * @param pageSize
     * @return
     */
    public List<CouponAct> retrieveOnlineCouponAct(Integer page, Integer pageSize) {
        List<CouponAct> couponActList;
        Pageable pageable = PageRequest.of(page-1, pageSize);
        Page<CouponActPo> poPage = couponActPoMapper.findByStatus((byte) 1, pageable);
        if (!poPage.isEmpty()) {
            CouponActFactory factory=new CouponActFactory();
            couponActList = poPage.stream()
                    .map(po -> factory.build(po))
                    .collect(Collectors.toList());
        }
        else {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, "无可用的优惠券活动");
        }
        log.debug("retrieveOnlineCouponAct: couponActList size = {}", couponActList.size());
        return couponActList;
    }

    /**
     * 通过 Switch 判断产生何种类型的bo
     */
    public class CouponActFactory {
        public CouponAct build(CouponActPo po) {
            CouponAct bo;
            switch (po.getConstraintType()) {
                case 1:
                    bo = CloneFactory.copy(new CouponActConstraintTotal(), po);
                    break;
                case 2:
                    bo = CloneFactory.copy(new CouponActConstraintUser(), po);
                    break;
                default:
                    throw new BusinessException(ReturnNo.COUPONACT_NOT_TYPE, String.format(ReturnNo.COUPONACT_NOT_TYPE.getMessage()));
            }
           this.build(bo);
            return bo;
        }

        public CouponAct build(CouponAct bo){
            bo.setCouponActDao(CouponActDao.this);
            bo.setCouponDao(couponDao);
            return bo;
        }
    }


    /**
     * 保存不同类型的子类
     * @param bo
     * @param user
     * @return
     * @throws RuntimeException
     */
    public String save(CouponAct bo, UserDto user) throws RuntimeException {
        bo.setId(null);
        bo.setGmtModified(LocalDateTime.now());
        bo.setModifier(user);
        CouponActPo po=null;
        if (bo instanceof CouponActConstraintTotal) {
            CouponActConstraintTotal obj = (CouponActConstraintTotal) bo;
            po = CloneFactory.copy(new CouponActPo(), obj);
        } else if (bo instanceof CouponActConstraintUser) {
            CouponActConstraintUser obj = (CouponActConstraintUser) bo;
            po = CloneFactory.copy(new CouponActPo(), obj);
        }
        else{
            throw new BusinessException(ReturnNo.COUPONACT_NOT_TYPE, String.format(ReturnNo.COUPONACT_NOT_TYPE.getMessage()));
        }
        CouponActPo save = couponActPoMapper.save(po);
        return String.format(KEY, save.getId());
    }

    /**
     * 插入不同类型的子类
     * @param bo
     * @param user
     * @return
     * @throws RuntimeException
     */
    public CouponAct insert(CouponAct bo, UserDto user) throws RuntimeException {
        bo.setId(null);
        bo.setGmtCreate(LocalDateTime.now());
        bo.setCreator(user);
        CouponActPo Po=null;
        if (bo instanceof CouponActConstraintTotal) {
            CouponActConstraintTotal obj = (CouponActConstraintTotal) bo;
            Po = CloneFactory.copy(new CouponActPo(), obj);
        } else if (bo instanceof CouponActConstraintUser) {
            CouponActConstraintUser obj = (CouponActConstraintUser) bo;
            Po = CloneFactory.copy(new CouponActPo(), obj);
        }else{
            throw new BusinessException(ReturnNo.COUPONACT_NOT_TYPE, String.format(ReturnNo.COUPONACT_NOT_TYPE.getMessage()));
        }
        CouponActPo save = this.couponActPoMapper.save(Po);
        bo.setId(save.getId());
        return bo;
    }
}
