package cn.edu.xmu.oomall.prodorder.Controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JacksonUtil;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.prodorder.prodorderTestApplication;
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

/**
 * author Liuzhiwen
 */



@SpringBootTest(classes = prodorderTestApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)

public class ShopontrollerTest {

    @Autowired
    private MockMvc mvc;
    JwtHelper jwtHelper = new JwtHelper();
    private static String adminToken;
    private final String SHOP_ORDERS_ID ="shops/{shopId}/orders";
    private final String SHOP_ORDER_ID ="shops/{shopId}/orders/{id}";



    @BeforeAll
    public static void setup(){
        JwtHelper jwtHelper = new JwtHelper();
        adminToken = jwtHelper.createToken(11L, "MAYDAY商铺", 0L, 1, 3600);

    }

    //成功根据评论Id找到评论
    @Test
    void testFindShopProdordersByIdWhenSuccess () throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get(SHOP_ORDERS_ID, 11)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list.length()", is(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '1')].regionId", hasItem(2417)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '2')].regionId", hasItem(67921)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '4')].regionId", hasItem(2417)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '7')].regionId", hasItem(264962)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '10')].regionId", hasItem(2417)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '13')].regionId", hasItem(264962)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '14')].regionId", hasItem(264962)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '15')].regionId", hasItem(67921)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '16')].regionId", hasItem(264962)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '17')].regionId", hasItem(67921)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '20')].regionId", hasItem(264962)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '21')].regionId", hasItem(264962)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '22')].regionId", hasItem(4077)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '23')].regionId", hasItem(264962)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '24')].regionId", hasItem(264962)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '25')].regionId", hasItem(264962)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '26')].regionId", hasItem(264962)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '27')].regionId", hasItem(264962)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '28')].regionId", hasItem(264962)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '29')].regionId", hasItem(2417)));
    }


   /* //根据评论Id没有找到评论
    @Test
    void testFindValidprodorderDetailsByIdWhenFailed () throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get(ADMIN_ORDER_ID, 0,200)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //非平台管理人员查询
    @Test
    void testFindValidprodorderDetailsByIdGivenNotPlatform () throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get(ADMIN_ORDER_ID, 1,1)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_OUTSCOPE.getErrNo())));

    }


    //非平台管理人员审核评论
    @Test
    void auditprodorderGivenNotPlatform() throws Exception {
        AuditORDERDto dto = new AuditprodorderDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_ORDER_REVIEW_ID, 1,11)
                .header("authorization",  adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_OUTSCOPE.getErrNo())));
    }

    //平台管理人员审核首评为通过状态
    @Test
    void auditprodorderWhenFirstprodorderApproved() throws Exception {
        AuditprodorderDto dto = new AuditprodorderDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_prodorder_REVIEW_ID, 0,12)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

    //平台管理人员审核首评为驳回状态
    @Test
    void auditprodorderWhenFirstprodorderRejected() throws Exception {
        AuditprodorderDto dto = new AuditprodorderDto();
        dto.setIsApproved(false);
        dto.setRejectReason("内容含广告信息");
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_prodorder_REVIEW_ID, 0,11)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }


    //平台管理人员审核追评为通过状态
    @Test
    void auditprodorderWhenAddprodorderApproved() throws Exception {
        AuditprodorderDto dto = new AuditprodorderDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_prodorder_REVIEW_ID, 0,13)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
    //平台管理人员审核追评为驳回状态
    @Test
    void auditprodorderWhenAddprodorderRejected() throws Exception {
        AuditprodorderDto dto = new AuditprodorderDto();
        dto.setIsApproved(false);
        dto.setRejectReason("内容含广告信息");
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_prodorder_REVIEW_ID, 0,13)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }


    //平台管理人员审核回复为通过状态
    @Test
    void auditprodorderWhenReplyApproved() throws Exception {
        AuditprodorderDto dto = new AuditprodorderDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_prodorder_REVIEW_ID, 0,14)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
    //平台管理人员审核回复为驳回状态
    @Test
    void auditprodorderWhenReplyRejected() throws Exception {
        AuditprodorderDto dto = new AuditprodorderDto();
        dto.setIsApproved(false);
        dto.setRejectReason("内容含广告信息");
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_prodorder_REVIEW_ID, 0,14)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }



    //非平台管理人员审核举报
    @Test
    void TestReportprodorderGivenNotPlatform() throws Exception {
        AuditprodorderDto dto = new AuditprodorderDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_prodorder_REPORT_ID, 1,20)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_OUTSCOPE.getErrNo())));
    }

    //平台管理人员审核首评举报通过
    @Test
    void TestReportFirstprodorderWhenApproved() throws Exception {
        AuditprodorderDto dto = new AuditprodorderDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_prodorder_REPORT_ID, 0,20)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

    //平台管理人员审核首评举报为驳回
    @Test
    void TestReportFirstprodorderWhenRejected() throws Exception {
        AuditprodorderDto dto = new AuditprodorderDto();
        dto.setIsApproved(false);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_prodorder_REPORT_ID, 0,20)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }


  //平台管理人员审核追评举报为通过状态
    @Test
    void TestReportAddprodorderWhenApproved() throws Exception {
        AuditprodorderDto dto = new AuditprodorderDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_prodorder_REPORT_ID, 0,24)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

    //平台管理人员审核追评举报为驳回状态
    @Test
    void TestReportAddprodorderWhenRejected() throws Exception {
        AuditprodorderDto dto = new AuditprodorderDto();
        dto.setIsApproved(false);
        dto.setRejectReason("内容含广告信息");
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_prodorder_REPORT_ID, 0,24)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }


    //平台管理人员审核回复举报为通过状态
    @Test
    void TestReportReplyprodorderWhenApproved() throws Exception {
        AuditprodorderDto dto = new AuditprodorderDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_prodorder_REPORT_ID, 0,25)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
    //平台管理人员审核回复举报为驳回状态
    @Test
    void estReportReplyprodorderWhenRejected() throws Exception {
        AuditprodorderDto dto = new AuditprodorderDto();
        dto.setIsApproved(false);
        dto.setRejectReason("内容含广告信息");
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_prodorder_REPORT_ID, 0,25)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }*/
}
