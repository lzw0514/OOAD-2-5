package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.customer.CustomerTestApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(classes = CustomerTestApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdminCustomerControllerTest {

    @Autowired
    private MockMvc mvc;
    private static String adminToken;


    @BeforeAll
    public static void setup(){
        JwtHelper jwtHelper = new JwtHelper();
        adminToken = jwtHelper.createToken(0L, "13088admin", 0L, 1, 3600);
    }

    // 管理员封禁顾客成功
    @Test
    public void banCustomerWhenSuccess() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.put("/platforms/{did}/customers/{customerId}/ban",0,5)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

    // 非平台管理员封禁顾客
    @Test
    public void banCustomerWhenGivenNotPlatform() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.put("/platforms/{did}/customers/{customerId}/ban",1,5)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_OUTSCOPE.getErrNo())));
    }
    // 管理员封禁顾客失败，顾客状态不允许
    @Test
    public void banCustomerWhenStateNotAllow() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.put("/platforms/{did}/customers/{customerId}/ban",0,7)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.STATENOTALLOW.getErrNo())));
    }

    // 管理员解禁顾客成功
    @Test
    public void releaseCustomerWhenSuccess() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.put("/platforms/{did}/customers/{customerId}/release",0,7)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
    // 非平台管理员解禁顾客
    @Test
    public void releaseCustomerWhenGivenNotPlatform() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.put("/platforms/{did}/customers/{customerId}/release",1,7)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_OUTSCOPE.getErrNo())));
    }

    // 管理员解禁顾客失败，顾客状态不允许
    @Test
    public void releaseCustomer_StateNotAllow() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.put("/platforms/{did}/customers/{customerId}/release",0,1)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.STATENOTALLOW.getErrNo())));
    }

    // 平台管理人员查看优惠券活动详情
    @Test
    public void testFindCuponActDetailWhenSuccess() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/platforms/{did}/couponAct/{actId}",0,1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("新年折扣")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.description", is("庆祝新年，全场商品享受8折优惠")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtBegin", is("2024-01-01T00:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtEnd", is("2024-01-31T23:59:59")));
    }
    // 非平台管理员查看优惠券活动详情
    @Test
    public void FindCuponActDetailWhenGivenNotPlatform() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/platforms/{did}/couponAct/{actId}",1,1)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_OUTSCOPE.getErrNo())));
    }

}
