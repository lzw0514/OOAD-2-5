/*
package cn.edu.xmu.oomall.prodorder.Controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.comment.prodorderTestApplication;
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

*/
/**
 * @author Liuzhiwen
 *//*

@SpringBootTest(classes = prodorderTestApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UnauthorizedControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void retrieveProductComments () throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/products/{productId}/comments", 1559)
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list.length()", is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '1')].content", hasItem("东西很好")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '3')].content", hasItem("客服态度很好，但商品质量差。")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '6')].content", hasItem("感谢您的支持。")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.list[?(@.id == '7')].content", hasItem("抱歉给您带来不好的体验。")));
    }

    //成功根据评论Id找到评论
    @Test
    void testFindValidCommentDetailsByIdWhenSuccess () throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/comment/{commentId}", 1)
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
        this.mvc.perform(MockMvcRequestBuilders.get("/comment/{commentId}", 2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())));
    }


}*/
