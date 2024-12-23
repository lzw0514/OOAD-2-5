//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.controller.vo.*;
import cn.edu.xmu.oomall.customer.controller.dto.*;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import cn.edu.xmu.oomall.customer.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

/**
 * 优惠活动控制器
 * author Liuzhiwen
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
@Slf4j
public class CouponActController {

    private final CouponActService couponActService;

    /**
     * 获取所有可用的优惠券活动
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/OnlineCouponActs")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject findAllOnlineCouponActivities(@RequestParam(defaultValue = "1") Integer page,
                                                      @RequestParam(defaultValue = "10") Integer pageSize){
        List<CouponAct> acts = this.couponActService.retrieveOnlineCouponAct(page, pageSize);
        List<CouponActVo> vos = acts.stream().map(o -> CloneFactory.copy(new CouponActVo(), o)).collect(Collectors.toList());
        return new ReturnObject(new PageVo<>(vos, page, pageSize));
    }

    /**
     * 查看有效优惠券活动详情
     * @param actId
     * @return
     */
    @GetMapping("/couponAct/{actId}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject findCouponActById(@PathVariable Long actId){
        CouponAct act = this.couponActService.findValidCouponActById(actId);
        return new ReturnObject(new CouponActVo(act));
    }
}
