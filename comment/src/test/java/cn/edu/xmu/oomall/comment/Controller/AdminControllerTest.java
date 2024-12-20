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
    private final String ADMIN_COMMENT_REVIEW_ID ="/platforms/{did}/comments/{commentId}/reviews";
    private final String ADMIN_COMMENT_ID ="/platforms/{did}/comments/{commentId}";

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", is("这款商品很好，质量不错！")))
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



    //
    @Test
    void auditCommentGivenNotPlatform() throws Exception {
        AuditCommentDto dto = new AuditCommentDto();
        dto.setIsApproved(true);
        mvc.perform(MockMvcRequestBuilders.put(ADMIN_COMMENT_ID, 1,11)
                .header("authorization",  adminToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_OUTSCOPE.getErrNo())));
    }
}
