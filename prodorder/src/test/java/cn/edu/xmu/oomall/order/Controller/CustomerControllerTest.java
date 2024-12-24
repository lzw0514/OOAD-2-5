package cn.edu.xmu.oomall.order.Controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JacksonUtil;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.order.OrderTestApplication;
import cn.edu.xmu.oomall.order.controller.dto.OrderDto;
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

import static org.hamcrest.CoreMatchers.is;


@SpringBootTest(classes = OrderTestApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;
    JwtHelper jwtHelper = new JwtHelper();
    private static String customerToken;

    @BeforeAll
    public static void setup() {
        JwtHelper jwtHelper = new JwtHelper();
        customerToken = jwtHelper.createToken(514L, "张三", 0L, 1, 3600);
    }

    // 成功获取订单状态
    @Test
    public void testGetOrderStateWhenSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/orders/{orderId}/states",1)
                        .header("authorization", customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("1")));
    }

    // 成功查询订单完整信息
    @Test
    public void testGetCustomerOrderByIdWhenSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/orders/{id}", 1)
                        .header("authorization", customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", is(1)));
    }

    // 成功修改订单
    @Test
    public void testChangeCustomerOrderWhenSuccess() throws Exception {
        OrderDto dto = new OrderDto();
        dto.setAddress("New Address");
        mvc.perform(MockMvcRequestBuilders.put("/orders/{id}", 1)
                        .header("authorization", customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

    // 成功取消订单
    @Test
    public void testDeleteCustomerOrderWhenSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/orders/{id}", 1)
                        .header("authorization", customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
}