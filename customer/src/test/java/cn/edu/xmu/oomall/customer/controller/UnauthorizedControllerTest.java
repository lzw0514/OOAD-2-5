package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JacksonUtil;
import cn.edu.xmu.oomall.customer.controller.dto.CustomerDto;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import org.junit.jupiter.api.Test;
import cn.edu.xmu.oomall.customer.CustomerTestApplication;
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
public class UnauthorizedControllerTest {

    @Autowired
    private MockMvc mvc;

    // 顾客注册成功
    @Test
    public void customerRegister_Success () throws Exception {
        CustomerDto dto = new CustomerDto("user11","password11","11223344556","Customer11");
        this.mvc.perform(MockMvcRequestBuilders.post("/customer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userName", is("user11")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.password", is("password11")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.mobile", is("11223344556")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", is("Customer11")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.point", is(0.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status", is(1)));
    }

    // 顾客注册失败，已有用户名
    @Test
    public void customerRegister_NAMEEXIST () throws Exception {
        CustomerDto dto = new CustomerDto("user1","password11","11223344556","Customer11");
        this.mvc.perform(MockMvcRequestBuilders.post("/customer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CUSTOMER_NAMEEXIST.getErrNo())));
    }

    // 顾客注册失败，已有电话号码
    @Test
    public void customerRegister_MOBILEEXIST () throws Exception {
        CustomerDto dto = new CustomerDto("user11","password11","12345678901","Customer11");
        this.mvc.perform(MockMvcRequestBuilders.post("/customer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CUSTOMER_MOBILEEXIST.getErrNo())));
    }
}