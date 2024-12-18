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
import cn.edu.xmu.oomall.comment.dao.CommentDao;
import cn.edu.xmu.oomall.comment.dao.bo.*;
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
public class CommentController{

    private final CommentService commentService;
    private final CommentDao commentDao;


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
        List<CommentVo> commentVoList=comments.stream()
                .map(bo -> CommentVo.builder()
                        .id(bo.getId())
                        .content(bo.getContent())
                        .createtime(bo.getGmtPublish())
                        .build()).
                collect(Collectors.toList());

        return new ReturnObject(new PageVo<>(commentVoList, page, pageSize));
    }

    /**
     * 查询某个评论
     * @param commentId
     * author Wuyuzhu
     * @return
     */
    @GetMapping("/comments/{commentId}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ReturnObject getCommentById(@PathVariable("commentId") Long commentId) {
        Comment comment = this.commentService.findCommentById(commentId);
        return new ReturnObject(new CommentVo(comment));
    }


    /**
     * 顾客提交评论
     * author Liuzhiwen
     * @param orderitemId
     * @return
     */
    @PostMapping("/orderItem/{id}/comment")
    public ReturnObject createComment(@PathVariable Long orderitemId,
                                      @LoginUser UserDto user,
                                      @RequestBody CommentDto commentDto) {
        FirstComment firstComment = CloneFactory.copy(new FirstComment(), commentDto);
        FirstComment newComment = commentService.createComment(orderitemId, firstComment, user);
        return new ReturnObject(new CommentVo(newComment));
    }

    /**
     * 顾客提交追评
     * author Liuzhiwen
     * @param commentId
     * @return
     */
    @PostMapping("/comment/{id}")
    public ReturnObject creatAddComment(@PathVariable Long commentId,
                                      @LoginUser UserDto user,
                                      @RequestBody CommentDto commentDto) {
        AddComment addComment = CloneFactory.copy(new AddComment(), commentDto);
        AddComment newComment = commentService.createAddComment(commentId, addComment, user);
        return new ReturnObject(new CommentVo(newComment));
    }

    /**
     *  商户回复评论
     * @param shopId
     * @param commentId
     * author Wuyuzhu
     * @return
     */
    @PostMapping("/shops/{shopId}/comments/{commentId}/replies")
    public ReturnObject createReply(@PathVariable("commentId") Long commentId,
                                    @PathVariable("shopId") Long shopId,
                                    @LoginUser UserDto user,
                                    @RequestBody CommentDto commentDto) {
        ReplyComment replyComment = CloneFactory.copy(new ReplyComment(), commentDto);
        ReplyComment newComment = commentService.createReply(shopId, commentId, replyComment, user);
        return new ReturnObject(new CommentVo(newComment));
    }

    /**
     * 管理员审核评论
     * @param commentId
     * author Liuzhiwen
     * @return
     */
    @PutMapping("/comments/{commentId}/reviews")
    public ReturnObject auditComment(@PathVariable("commentId") Long commentId,
                                     @LoginUser UserDto user,
                                     @RequestBody AuditCommentDto auditCommentDto) {
        boolean isApproved = auditCommentDto.getIsApproved();
        String rejectReason = auditCommentDto.getRejectReason();
        String res = commentService.auditReply(commentId, isApproved, rejectReason, user);
        return new ReturnObject(ReturnNo.OK);
    }


}
