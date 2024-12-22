package cn.edu.xmu.oomall.comment.controller;


import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.oomall.comment.controller.vo.CommentVo;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import cn.edu.xmu.oomall.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
@Slf4j
public class UnauthorizedController {

    private final CommentService commentService;
    /**
     * 查询商品的全部评论
     * author Liuzhiwen
     * @param productId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/products/{productId}/comments")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject getProductComments   (@PathVariable("productId") Long productId,
                                              @RequestParam(required = false,defaultValue = "1") Integer page,
                                              @RequestParam(required = false,defaultValue = "10") Integer pageSize) {
        List<Comment> comments = this.commentService.retrieveProductComments(productId, page, pageSize);
        List<CommentVo> commentVoList=comments.stream().map(bo -> CommentVo.builder().id(bo.getId()).content(bo.getContent()).GmtPublish(bo.getGmtPublish()).type(bo.getType()).build()).collect(Collectors.toList());
        return new ReturnObject(new PageVo<>(commentVoList, page, pageSize));
    }


    /**
     * 查询有效状态的评论详情
     * @param commentId
     * author Wuyuzhu
     * @return
     */
    @GetMapping("/comment/{commentId}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject getValidCommentDetailsById(@PathVariable("commentId") Long commentId) {
        Comment comment = this.commentService.findValidCommentById(commentId);
        return new ReturnObject(new CommentVo(comment));
    }
}
