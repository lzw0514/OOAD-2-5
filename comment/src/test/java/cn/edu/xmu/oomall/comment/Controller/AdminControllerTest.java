package cn.edu.xmu.oomall.comment.Controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JacksonUtil;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.comment.CommentTestApplication;
import cn.edu.xmu.oomall.comment.controller.dto.AuditCommentDto;
import cn.edu.xmu.oomall.comment.controller.dto.CommentDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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


@SpringBootTest(classes = CommentTestApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdminControllerTest {

    @Autowired
    private MockMvc mvc;
    JwtHelper jwtHelper = new JwtHelper();
    private static String adminToken;
    private final String ADMIN_COMMENT_ID ="/platforms/{did}/comment/{commentId}";
    private final String ADMIN_COMMENT_REVIEW_ID ="/platforms/{did}/comments/{commentId}/reviews";
    private final String ADMIN_COMMENT_REPORT_ID ="/platforms/{did}/comments/{commentId}/reports";


    @BeforeAll
    public static void setup(){
        JwtHelper jwtHelper = new JwtHelper();
        adminToken = jwtHelper.createToken(0L, "13088admin", 0L, 1, 3600);

    }

    //成功根据评论Id找到评论
    @Test
    void testFindValidCommentDetailsByIdWhenSuccess () throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get(ADMIN_COMMENT_ID, 0,1)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", is("东西很好")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.type", is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.creatorId", is(514)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(1559)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.shopId", is(11)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtPublish", is("2024-12-18T18:29:26")));
    }


    //根据评论Id没有找到评论
    @Test
    void testFindValidCommentDetailsByIdWhenFailed () throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get(ADMIN_COMMENT_ID, 0,200)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //非平台管理人员查询
    @Test
    void testFindValidCommentDetailsByIdGivenNotPlatform () throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get(ADMIN_COMMENT_ID, 1,1)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_OUTSCOPE.getErrNo())));

    }


    //非平台管理人员审核评论
    @Test
    void auditCommentGivenNotPlatform() throws Exception {
        AuditCommentDto dto = new AuditCommentDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_COMMENT_REVIEW_ID, 1,11)
                .header("authorization",  adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_OUTSCOPE.getErrNo())));
    }

    //平台管理人员审核首评为通过状态
    @Test
    void auditCommentWhenFirstCommentApproved() throws Exception {
        AuditCommentDto dto = new AuditCommentDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_COMMENT_REVIEW_ID, 0,12)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

    //平台管理人员审核首评为驳回状态
    @Test
    void auditCommentWhenFirstCommentRejected() throws Exception {
        AuditCommentDto dto = new AuditCommentDto();
        dto.setIsApproved(false);
        dto.setRejectReason("内容含广告信息");
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_COMMENT_REVIEW_ID, 0,11)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }


    //平台管理人员审核追评为通过状态
    @Test
    void auditCommentWhenAddCommentApproved() throws Exception {
        AuditCommentDto dto = new AuditCommentDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_COMMENT_REVIEW_ID, 0,13)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
    //平台管理人员审核追评为驳回状态
    @Test
    void auditCommentWhenAddCommentRejected() throws Exception {
        AuditCommentDto dto = new AuditCommentDto();
        dto.setIsApproved(false);
        dto.setRejectReason("内容含广告信息");
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_COMMENT_REVIEW_ID, 0,13)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }


    //平台管理人员审核回复为通过状态
    @Test
    void auditCommentWhenReplyApproved() throws Exception {
        AuditCommentDto dto = new AuditCommentDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_COMMENT_REVIEW_ID, 0,14)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
    //平台管理人员审核回复为驳回状态
    @Test
    void auditCommentWhenReplyRejected() throws Exception {
        AuditCommentDto dto = new AuditCommentDto();
        dto.setIsApproved(false);
        dto.setRejectReason("内容含广告信息");
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_COMMENT_REVIEW_ID, 0,14)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }



    //非平台管理人员审核举报
    @Test
    void TestReportCommentGivenNotPlatform() throws Exception {
        AuditCommentDto dto = new AuditCommentDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_COMMENT_REPORT_ID, 1,20)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_OUTSCOPE.getErrNo())));
    }

    //平台管理人员审核首评举报通过
    @Test
    void TestReportFirstCommentWhenApproved() throws Exception {
        AuditCommentDto dto = new AuditCommentDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_COMMENT_REPORT_ID, 0,20)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

    //平台管理人员审核首评举报为驳回
    @Test
    void TestReportFirstCommentWhenRejected() throws Exception {
        AuditCommentDto dto = new AuditCommentDto();
        dto.setIsApproved(false);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_COMMENT_REPORT_ID, 0,20)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }


  //平台管理人员审核追评举报为通过状态
    @Test
    void TestReportAddCommentWhenApproved() throws Exception {
        AuditCommentDto dto = new AuditCommentDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_COMMENT_REPORT_ID, 0,24)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }

    //平台管理人员审核追评举报为驳回状态
    @Test
    void TestReportAddCommentWhenRejected() throws Exception {
        AuditCommentDto dto = new AuditCommentDto();
        dto.setIsApproved(false);
        dto.setRejectReason("内容含广告信息");
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_COMMENT_REPORT_ID, 0,24)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }


    //平台管理人员审核回复举报为通过状态
    @Test
    void TestReportReplyCommentWhenApproved() throws Exception {
        AuditCommentDto dto = new AuditCommentDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_COMMENT_REPORT_ID, 0,25)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
    //平台管理人员审核回复举报为驳回状态
    @Test
    void estReportReplyCommentWhenRejected() throws Exception {
        AuditCommentDto dto = new AuditCommentDto();
        dto.setIsApproved(false);
        dto.setRejectReason("内容含广告信息");
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_COMMENT_REPORT_ID, 0,25)
                        .header("authorization",  adminToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())));
    }
}
