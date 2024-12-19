package cn.edu.xmu.oomall.comment.Controller;

import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JacksonUtil;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.comment.CommentTestApplication;
import cn.edu.xmu.oomall.comment.controller.dto.CommentDto;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(classes = CommentTestApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;
    JwtHelper jwtHelper = new JwtHelper();
    private static String customerToken;
    private static String shopToken;


    @BeforeAll
    public static void setup(){
        JwtHelper jwtHelper = new JwtHelper();
        customerToken = jwtHelper.createToken(514L, "张三", 0L, 1, 3600);
        shopToken = jwtHelper.createToken(11L, "MAYDAY商铺", 0L, 1, 3600);

    }

    //成功创建首评
    @Test
    public void testCreateFirstCommentWhenSuccess() throws Exception {
        CommentDto dto = new CommentDto();
        dto.setContent("商品质量很好，我很喜欢");
        dto.setCreatorId(514l);
        mvc.perform(MockMvcRequestBuilders.post("/orderItem/{orderitemId}/comment", 12343)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", is("商品质量很好，我很喜欢")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.type", is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status", is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.shopId", is(11)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(1559)));
    }

    //创建首评失败，订单下已有一个评论
    @Test
    public void testCreateFirstCommentWhenOrderItemAlreadyHasComment() throws Exception {
        CommentDto dto = new CommentDto();
        dto.setContent("商品质量很好，我很喜欢");
        dto.setCreatorId(514l);
        mvc.perform(MockMvcRequestBuilders.post("/orderItem/{orderitemId}/comment", 12345)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno",  is(ReturnNo.COMMENT_UPPER_LIMIT.getErrNo())));
    }

    //创建首评失败，订单不存在
    @Test
    public void testCreateFirstCommentWhenOrderItemNotExist() throws Exception {
        CommentDto dto = new CommentDto();
        dto.setContent("商品质量很好，我很喜欢");
        dto.setCreatorId(514l);
        mvc.perform(MockMvcRequestBuilders.post("/orderItem/{orderitemId}/comment", 12346)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())));
    }
    //成功创建追评
    @Test
    public void testCreateAddCommentWhenSuccess() throws Exception {
        CommentDto dto = new CommentDto();
        dto.setContent("商品质量很好，我很喜欢");
        dto.setCreatorId(514l);
        mvc.perform(MockMvcRequestBuilders.post("/comment/{commentId}/Addcomment", 4)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", is("商品质量很好，我很喜欢")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.parentId", is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status", is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.type", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.shopId", is(11)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(1600)));
    }

    //创建追评失败，首评下已有一个追评
    @Test
    public void testCreateAddCommentWhenFirstCommentAlreadyHasAddComment() throws Exception {
        CommentDto dto = new CommentDto();
        dto.setContent("商品质量很好，我很喜欢");
        dto.setCreatorId(514l);
        mvc.perform(MockMvcRequestBuilders.post("/comment/{commentId}/Addcomment", 1)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno",  is(ReturnNo.COMMENT_UPPER_LIMIT.getErrNo())));
    }

    //创建追评失败，首评状态不正常
    @Test
    public void testCreateAddCommentWhenFirstCommentAbnormal() throws Exception {
        CommentDto dto = new CommentDto();
        dto.setContent("商品质量很好，我很喜欢");
        dto.setCreatorId(514l);
        mvc.perform(MockMvcRequestBuilders.post("/comment/{commentId}/Addcomment", 10)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno",  is(ReturnNo.COMMENT_NOT_PUBLISHED.getErrNo())));
    }

    //创建追评失败，首评不存在
    @Test
    public void testCreateAddCommentWhenFirstCommentNotExist() throws Exception {
        CommentDto dto = new CommentDto();
        dto.setContent("商品质量很好，我很喜欢");
        dto.setCreatorId(514l);
        mvc.perform(MockMvcRequestBuilders.post("/comment/{commentId}/Addcomment", 100)
                        .header("authorization",  customerToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())));
    }

    //成功创建回复（首评的回复）
    @Test
    public void testCreateReplyCommentWhenSuccessByFirstComment() throws Exception {
        CommentDto dto = new CommentDto();
        dto.setContent("商品质量很好，我很喜欢");
        dto.setCreatorId(514l);
        mvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/comments/{commentId}/replies", 11,4)
                        .header("authorization",  shopToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", is("商品质量很好，我很喜欢")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.type", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status", is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.shopId", is(11)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.parentId", is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(1600)));
    }

    //创建回复失败（非本店铺评论）
    @Test
    public void testCreateReplyCommentWhenCommentNotBelongToShop() throws Exception {
        CommentDto dto = new CommentDto();
        dto.setContent("商品质量很好，我很喜欢");
        dto.setCreatorId(514l);
        mvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/comments/{commentId}/replies", 12,5)
                        .header("authorization",  shopToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno").value(ReturnNo.AUTH_NO_RIGHT.getErrNo()));
    }
    //创建回复失败 首评状态不正常
    @Test
    public void testCreateReplyCommentWhenFirstCommentAbnormal() throws Exception {
        CommentDto dto = new CommentDto();
        dto.setContent("商品质量很好，我很喜欢");
        dto.setCreatorId(514l);
        mvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/comments/{commentId}/replies", 11,10)
                        .header("authorization",  shopToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno").value(ReturnNo.COMMENT_NOT_PUBLISHED.getErrNo()));
    }
    //创建回复失败 首评下已有回复
    @Test
    public void testCreateReplyCommentWhenFirstCommentAlreadyHasReplyComment() throws Exception {
        CommentDto dto = new CommentDto();
        dto.setContent("商品质量很好，我很喜欢");
        dto.setCreatorId(514l);
        mvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/comments/{commentId}/replies", 11,1)
                        .header("authorization",  shopToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno",  is(ReturnNo.COMMENT_UPPER_LIMIT.getErrNo())));
    }

    //成功创建回复（追评的回复）
    @Test
    public void testCreateReplyCommentWhenSuccessByAddComment() throws Exception {
        CommentDto dto = new CommentDto();
        dto.setContent("商品质量很好，我很喜欢");
        dto.setCreatorId(514l);
        mvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/comments/{commentId}/replies", 11,9)
                        .header("authorization",  shopToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", is("商品质量很好，我很喜欢")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.type", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status", is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.shopId", is(11)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.parentId", is(9)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(1600)));


    }


    //创建回复失败 追评状态不正常
    @Test
    public void testCreateReplyCommentWhenAddCommentAbnormal() throws Exception {
        CommentDto dto = new CommentDto();
        dto.setContent("商品质量很好，我很喜欢");
        dto.setCreatorId(514l);
        mvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/comments/{commentId}/replies", 11,2)
                        .header("authorization",  shopToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno").value(ReturnNo.COMMENT_NOT_PUBLISHED.getErrNo()));
    }
    //创建回复失败 追评下已有回复
    @Test
    public void testCreateReplyCommentWhenAddCommentAlreadyHasReplyComment() throws Exception {
        CommentDto dto = new CommentDto();
        dto.setContent("商品质量很好，我很喜欢");
        dto.setCreatorId(514l);
        mvc.perform(MockMvcRequestBuilders.post("/shops/{shopId}/comments/{commentId}/replies", 11,3)
                        .header("authorization",  shopToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Objects.requireNonNull(JacksonUtil.toJson(dto))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno",  is(ReturnNo.COMMENT_UPPER_LIMIT.getErrNo())));
    }
}
