package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.controller.dto.CustomerDto;
import cn.edu.xmu.oomall.customer.controller.vo.CouponActVo;
import cn.edu.xmu.oomall.customer.controller.vo.CustomerVo;
import cn.edu.xmu.oomall.customer.dao.CouponActDao;
import cn.edu.xmu.oomall.customer.dao.bo.CouponAct;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.service.CouponActService;
import cn.edu.xmu.oomall.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

@RestController
@RequestMapping(value = "/platforms/{did}", produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
@Slf4j
public class AdminCustomerController {

    private final CustomerService customerService;
    private final CouponActService couponActService;


    /**
     * 管理员封禁顾客
     * author Fengjianhao
     * @param customerId
     * @param user
     */
    @PutMapping("/customers/{customerId}/ban")
    @Audit(departName = "platforms")
    public ReturnObject banCustomer(@PathVariable Long did,@PathVariable Long customerId,
                                    @LoginUser UserDto user) {
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "顾客", customerId, did));
        }
        customerService.banCustomer(customerId, user);
        return new ReturnObject(ReturnNo.OK);
    }


    /**
     * 管理员解封顾客
     * author Fengjianhao
     * @param customerId
     * @param user
     */
    @PutMapping("/customers/{customerId}/release")
    @Audit(departName = "platforms")
    public ReturnObject releaseCustomer(@PathVariable Long did,@PathVariable Long customerId,
                                        @LoginUser UserDto user) {
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "顾客", customerId, did));
        }
        customerService.releaseCustomer(customerId, user);
        return new ReturnObject(ReturnNo.OK);
    }

    /**
     * 平台管理人员查看所有状态优惠券活动详情
     * author Fengjianhao
     * @param actId
     * @return
     */
    @GetMapping("/couponAct/{actId}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject findCouponActById(@PathVariable Long did,@PathVariable Long actId){
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "优惠活动", actId, did));
        }
        CouponAct act = this.couponActService.findCouponActById(actId);
        return new ReturnObject(new CouponActVo(act));
    }

}
