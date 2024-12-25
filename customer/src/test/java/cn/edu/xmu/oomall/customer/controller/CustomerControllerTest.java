package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JacksonUtil;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.customer.CustomerTestApplication;
import cn.edu.xmu.oomall.customer.controller.dto.AddressDto;
import cn.edu.xmu.oomall.customer.controller.dto.CartDto;
import cn.edu.xmu.oomall.customer.controller.dto.CustomerDto;
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



import java.util.Objects;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(classes = CustomerTestApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;
    private static String customerToken;
    private static String customerToken1;
    private static String customerToken2;
    private static String customerToken3;


    @BeforeAll
    public static void setup(){
        JwtHelper jwtHelper = new JwtHelper();
        customerToken = jwtHelper.createToken(1L, "user1", 0L, 1, 3600);
        customerToken1 = jwtHelper.createToken(5L, "user5", 0L, 1, 3600);
        customerToken2 = jwtHelper.createToken(6L, "user6", 0L, 1, 3600);
        customerToken3 = jwtHelper.createToken(7L, "user7", 0L, 1, 3600);

    }


    //返回顾客地址列表
    @Test
    public void testRetrieveAddressByCustomer() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/address")
                        .header("authorization",  customerToken)
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list.length()", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '1')].detailAddress", hasItem("北京市朝阳区建国路88号1001室")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '2')].detailAddress", hasItem("上海市浦东新区张江高科技园区盛大大厦2楼")));
    }

    //返回顾客空地址列表
    @Test
    public void testRetrieveAddressByCustomerWhenNoAddress() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/address")
                        .header("authorization",  customerToken2)
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())));
    }


    //顾客成功添加新地址
    @Test
    public void testAddNewAddressWhenSuccess() throws Exception {
        AddressDto dto = new AddressDto();
        dto.setRegionId(5525l);
        dto.setConsignee("User1");
        dto.setDetailAddress("厦门大学翔安校区");
        dto.setMobile("199564521356");
        this.mvc.perform(MockMvcRequestBuilders.post("/address")
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.regionId", is(5525)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.mobile", is("199564521356")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.detailAddress", is("厦门大学翔安校区")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.beDefault", is(false)));
    }


    //顾客添加新地址失败，地址簿数量超上限
    @Test
    public void testAddNewAddressWhenAddressCountsUpperLimit() throws Exception {
        AddressDto dto = new AddressDto();
        dto.setRegionId(5525l);
        dto.setConsignee("User1");
        dto.setDetailAddress("厦门大学翔安校区");
        dto.setMobile("199564521356");
        this.mvc.perform(MockMvcRequestBuilders.post("/address")
                        .header("authorization",  customerToken1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno",  is(ReturnNo.ADDRESS_OUTLIMIT.getErrNo())));

    }

    //顾客成功修改地址
    @Test
    public void testUpdateAddressWhenSuccess() throws Exception {
        AddressDto dto = new AddressDto();
        dto.setRegionId(5525l);
        dto.setConsignee("User1");
        dto.setDetailAddress("厦门大学翔安校区");
        dto.setMobile("199564521356");
        this.mvc.perform(MockMvcRequestBuilders.put("/address/{addressId}",1)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }


    //顾客成功将地址设为默认地址（顾客已有默认地址）
    @Test
    public void testChangeDefaultAddressWhenHasDefaultAddress() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.put("/address/{addressId}/default",2)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

    //顾客成功将地址设为默认地址（顾客没有默认地址）
    @Test
    public void testChangeDefaultAddressWhenNoHasDefaultAddress() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.put("/address/{addressId}/default",9)
                        .header("authorization",  customerToken1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

    //顾客删除地址
    @Test
    public void testDeleteAddress() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.delete("/address/{addressId}",1)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }



    // 查看顾客购物车列表
    @Test
    public void testRetrieveCartsByCustomer() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/carts")
                        .header("authorization",  customerToken)
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list.length()", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '1')].productId", hasItem(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '2')].productId", hasItem(102)));
    }
    //返回顾客空购物车列表
    @Test
    public void testRetrieveCartsByCustomerWhenNoCarts() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/carts")
                        .header("authorization", customerToken2)
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())));
    }

    // 顾客将商品加入购物车(购物车中无此商品)
    @Test
    public void testAddCartItemWhitoutThisProduct() throws Exception {
        CartDto dto=new CartDto();
        dto.setProductId(103l);
        dto.setQuantity(1l);
        dto.setProductName("五月天精品原装CD");
        dto.setSpec("后青春期的诗");

        this.mvc.perform(MockMvcRequestBuilders.post("/carts")
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(103)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.quantity", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productName", is("五月天精品原装CD")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.spec", is("后青春期的诗")));
    }

    // 顾客将商品加入购物车(购物车中已有此商品)
    @Test
    public void testAddCartItemWhenHaveThisProduct() throws Exception {
        CartDto dto=new CartDto();
        dto.setProductId(102l);
        dto.setQuantity(1l);
        dto.setProductName("闪速U盘");
        dto.setSpec("32GB");
        this.mvc.perform(MockMvcRequestBuilders.post("/carts")
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(102)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productName", is("闪速U盘")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.spec", is("32GB")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.quantity", is(2)));
    }

    // 顾客将商品加入购物车(购物车中已有此商品但规格不同)
    @Test
    public void testAddCartItemWithDiffSpec() throws Exception {
        CartDto dto=new CartDto();
        dto.setProductId(102l);
        dto.setQuantity(1l);
        dto.setProductName("闪速U盘");
        dto.setSpec("24GB");
        this.mvc.perform(MockMvcRequestBuilders.post("/carts")
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(102)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.quantity", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productName", is("闪速U盘")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.spec", is("24GB")));
    }



    // 顾客修改购物车内的商品数量(修改后数量>0)
    @Test
    public void testUpdateCartItemCountWhenSuccess() throws Exception {
        CartDto dto=new CartDto();
        dto.setProductId(101l);
        dto.setQuantity(1l);
        this.mvc.perform(MockMvcRequestBuilders.put("/cartItems/{cartItemId}",1)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

    // 顾客修改购物车中数量(修改后数量<=0，删除该购物车项)
    @Test
    public void testUpdateCartItemCountWhenFailed() throws Exception {
        CartDto dto=new CartDto();
        dto.setProductId(101l);
        dto.setQuantity(-1l);
        this.mvc.perform(MockMvcRequestBuilders.put("/cartItems/{cartItemId}",1)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

    // 顾客删除购物车项
    @Test
    public void testDeleteCartItem() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.delete("/cartItems/{cartItemId}",1)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

    // 查看所有可用的优惠券活动
    @Test
    public void testRetrieveAllOnlineCouponActivities() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/OnlineCouponActs")
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list.length()", is(8)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '1')].name", hasItem("新年折扣")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '2')].name", hasItem("春季大促")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '4')].name", hasItem("圣诞专享")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '5')].name", hasItem("新用户专享")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '6')].name", hasItem("周末狂欢")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '7')].name", hasItem("会员专享")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '9')].name", hasItem("双十一")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '10')].name", hasItem("夏季清仓")));
    }
    // 查看优惠券活动详情
    @Test
    public void testFindCuponActDetailWhenSuccess() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/couponAct/{actId}",1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("新年折扣")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.description", is("庆祝新年，全场商品享受8折优惠")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtBegin", is("2024-01-01T00:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtEnd", is("2024-01-31T23:59:59")));
    }

    // 查看顾客所有的优惠券
    @Test
    public void testGetCustomerAllCoupons() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/customers/{customerId}/coupons",1)
                        .header("authorization",  customerToken)
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list.length()", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '1')].name", hasItem("新年折扣")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '2')].name", hasItem("春季大促")));
    }


    // 查看某一张优惠券详情
    @Test
    public void testGetCuponDetail() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/coupons/{couponId}",1)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.customerId", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.actId", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("新年折扣")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtBegin", is("2024-01-01T00:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtEnd", is("2024-01-31T23:59:59")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status", is(1)));
    }

    // 顾客成功领取优惠券-未领取过(1-限制领取总数,限制顾客两次领取时间间隔 )
    @Test
    public void testClaimCouponConstraintTotalWhenSuccess() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/couponActs/{actId}/coupon",5)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("新用户专享")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtBegin", is("2024-12-01T00:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtEnd", is("2024-12-25T23:59:59")));
    }

    // 顾客成功领取优惠券-两次时间间隔符合(1-限制领取总数,限制顾客两次领取时间间隔 )
    @Test
    public void testClaimCouponConstraintTotalWhenSuccess1() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/couponActs/{actId}/coupon",2)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("春季大促")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtBegin", is("2024-02-01T00:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtEnd", is("2024-02-28T23:59:59")));
    }

    // 顾客领取优惠券失败，超总数(1-限制领取总数,限制顾客两次领取时间间隔)
    @Test
    public void testClaimCouponConstraintTotalWhenUpperTotalLimit() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/couponActs/{actId}/coupon",4)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.COUPON_FINISH.getErrNo())));
    }

    // 顾客领取优惠券失败，两次时间间隔小于规定时间(1-限制领取总数,限制顾客两次领取时间间隔)
    @Test
    public void testClaimCouponConstraintTotalWhenTimeShorterThanRuled() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/couponActs/{actId}/coupon",1)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.COUPON_RECLAIM_INTERVAL.getErrNo())));
    }

    // 顾客领取优惠券失败，优惠活动失效(1-限制领取总数,限制顾客两次领取时间间隔)
    @Test
    public void testClaimCouponConstraintTotalWhenActInvalid() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/couponActs/{actId}/coupon",3)
                        .header("authorization",  customerToken1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.STATENOTALLOW.getErrNo())));
    }

    // 顾客成功领取优惠券(2-限制顾客领取数，不限制领取总数)
    @Test
    public void testClaimCouponConstraintUserWhenSuccess() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/couponActs/{actId}/coupon",9)
                        .header("authorization",  customerToken1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("双十一")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtBegin", is("2024-11-01T00:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtEnd", is("2024-11-11T23:59:59")));

    }

    // 顾客领取优惠券失败，超过可以领取上限(2-限制顾客领取数，不限制领取总数)
    @Test
    public void testClaimCouponConstraintUserWhenUpperLimit() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/couponActs/{actId}/coupon",10)
                        .header("authorization",  customerToken1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.COUPON_UPPER_LIMIT.getErrNo())));
    }

    // 顾客领取优惠券失败，优惠活动失效(2-限制顾客领取数，不限制领取总数)
    @Test
    public void testClaimCouponConstraintUserWhenActInvalid() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/couponActs/{actId}/coupon",8)
                        .header("authorization",  customerToken1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.STATENOTALLOW.getErrNo())));
    }

    // 顾客查看个人信息
    @Test
    public void testFindSelfInfo() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/customer")
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userName", is("user1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.password", is("password1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.mobile", is("12345678901")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.point", is(10.0)));
    }

    // 顾客成功修改个人密码
    @Test
    public void testUpdatePwdWhenSuccess() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.put("/customer/password?newPwd=password2")
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

    // 顾客修改个人密码失败（密码重复）
    @Test
    public void testchangeMyselfInfoWithTheSamePwd() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.put("/customer/password?newPwd=password1")
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CUSTOMER_PASSWORDSAME.getErrNo())));
    }

    // 顾客修改个人密码失败（用户当前状态无权限修改）
    @Test
    public void testchangeMyselfInfoWhenCusStatusAbnormal() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.put("/customer/password?newPwd=password1")
                        .header("authorization",  customerToken3)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.STATENOTALLOW.getErrNo())));
    }


    // 顾客成功修改个人信息
    @Test
    public void testChangeMyselfInfoWhenSuccess() throws Exception {
        CustomerDto dto=new CustomerDto("user1","password1","22345678905","Customer1");
        this.mvc.perform(MockMvcRequestBuilders.put("/customer")
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

    // 顾客修改个人信息失败（电话重复）
    @Test
    public void testChangeMyselfInfoWithTheSameMobile() throws Exception {
        CustomerDto dto=new CustomerDto("user1","password1","12345678901","Customer1");

        this.mvc.perform(MockMvcRequestBuilders.put("/customer")
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CUSTOMER_MOBILEEXIST.getErrNo())));
    }

    // 顾客修改个人信息失败（用户当前状态无权限修改）
    @Test
    public void testUpdatePwdFailedWhenCusStatusAbnormal() throws Exception {
        CustomerDto dto=new CustomerDto("user1","password1","12345678901","Customer1");
        this.mvc.perform(MockMvcRequestBuilders.put("/customer")
                        .header("authorization",  customerToken3)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.STATENOTALLOW.getErrNo())));
    }
}
