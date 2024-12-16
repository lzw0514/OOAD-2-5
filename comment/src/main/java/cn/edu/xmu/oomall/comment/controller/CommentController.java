//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.controller;

import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.comment.controller.dto.AuditCommentDto;
import cn.edu.xmu.oomall.comment.controller.dto.CommentDto;
import cn.edu.xmu.oomall.comment.controller.vo.*;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.oomall.comment.dao.bo.*;
import cn.edu.xmu.oomall.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论控制器
 * @author Shuyang Xing
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
@Slf4j
public class CommentController{

    private final CommentService commentService;

    // 查看商品全部评论
    @GetMapping("/products/{productId}/comments")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject getCommentsByProduct(@PathVariable Long productId,
                                             @RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "5") Integer pageSize) {
        List<Comment> comments = this.commentService.retrieveCommentByProduct(productId, page, pageSize);
        return new ReturnObject(new PageVo<>(comments.stream().map(bo -> CommentVo.builder().id(bo.getId()).content(bo.getContent()).build()).collect(Collectors.toList()), page, pageSize));
    }

    // 根据Id查找评论
    @GetMapping("/comments/{commentId}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject getCommentById(@PathVariable Long commentId) {
        Comment comment = this.commentService.findCommentById(commentId);
        return new ReturnObject(new CommentVo(comment));
    }

    // 顾客撰写评论
    @PostMapping("/orderItem/{id}/comment")
    public ReturnObject createComment(@PathVariable Long orderitemId,
                                      @LoginUser UserDto user,
                                      @RequestBody CommentDto commentDto) {
        FirstComment firstComment = CloneFactory.copy(new FirstComment(), commentDto);
        FirstComment newComment = commentService.createComment(orderitemId, firstComment, user);;
        return new ReturnObject(new CommentVo(newComment));
    }

    // 商户回复评论
    @PostMapping("/shops/{shopId}//comments/{commentId}/replies")
    public ReturnObject createReply(@PathVariable Long commentId,
                                    @LoginUser UserDto user,
                                    @PathVariable Long shopId,
                                    @RequestBody CommentDto commentDto) {
        ReplyComment replyComment = CloneFactory.copy(new ReplyComment(), commentDto);
        ReplyComment newComment = commentService.createReply(shopId, commentId, replyComment, user);;
        return new ReturnObject(new CommentVo(newComment));
    }

    // 管理员审核评论
    @PutMapping("/comments/{commentId}/reviews")
    public ReturnObject auditComment(@PathVariable Long commentId,
                                     @LoginUser UserDto user,
                                     @RequestBody AuditCommentDto auditCommentDto) {
        boolean isApproved = auditCommentDto.getIsApproved();
        String rejectReason = auditCommentDto.getRejectReason();
        String res = commentService.auditReply(commentId, isApproved, rejectReason, user);;
        return new ReturnObject(ReturnNo.OK);
    }

    // 点赞评论
    @PutMapping("/comments/{commentId}/likes/{num}")
    public ReturnObject likeComment(@PathVariable Long commentId,
                                    @PathVariable int num,
                                    @LoginUser UserDto user,
                                    @RequestBody AuditCommentDto auditCommentDto) {
        String res = commentService.likeReply(commentId, num, user);;
        return new ReturnObject(ReturnNo.OK);
    }
}
