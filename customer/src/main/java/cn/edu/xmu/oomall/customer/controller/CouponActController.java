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

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
@Slf4j
public class CouponActController {

    private final CouponActService couponActService;

    // 获取所有可用的优惠券活动
    @GetMapping("/onlineCouponActs")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject findAllOnlineCouponActivities(@RequestParam(defaultValue = "1") Integer page,
                                                      @RequestParam(defaultValue = "5") Integer pageSize){
        List<CouponAct> acts = this.couponActService.findOnlineCouponAct(page, pageSize);
        return new ReturnObject(new PageVo<>(acts.stream().map(bo -> CouponActVo.builder().id(bo.getId()).name(bo.getName()).description(bo.getDescription()).build()).collect(Collectors.toList()), page, pageSize));
    }

    // 查看优惠券活动详情
    @GetMapping("/couponAct/{actId}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject findCouponActById(@PathVariable Long actId){
        CouponAct act = this.couponActService.findCouponActById(actId);
        return new ReturnObject(new CouponActVo(act));
    }
}
