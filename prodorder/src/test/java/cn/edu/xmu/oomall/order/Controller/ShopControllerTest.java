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
public class ShopControllerTest {

    @Autowired
    private MockMvc mvc;
    JwtHelper jwtHelper = new JwtHelper();
    private static String shopToken;

    @BeforeAll
    public static void setup() {
        JwtHelper jwtHelper = new JwtHelper();
        shopToken = jwtHelper.createToken(11L, "MAYDAY商铺", 0L, 1, 3600);
    }

    // 成功查询商户所有订单
    @Test
    public void testGetShopOrderWhenSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shops/{shopId}/orders", 11)
                        .param("page", "1")
                        .param("pageSize", "10")
                        .header("authorization", shopToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[0].id", is(1)));
    }

    // 成功修改订单
    @Test
    public void testChangeShopOrderWhenSuccess() throws Exception {
        OrderDto dto = new OrderDto();
        dto.setMessage("New留言");
        mvc.perform(MockMvcRequestBuilders.put("/shops/{shopId}/orders/{id}", 11, 1)
                        .header("authorization", shopToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
}