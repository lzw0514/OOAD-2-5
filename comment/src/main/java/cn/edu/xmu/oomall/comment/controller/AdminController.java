package cn.edu.xmu.oomall.comment.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.comment.controller.dto.AuditCommentDto;
import cn.edu.xmu.oomall.comment.controller.vo.CommentVo;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import cn.edu.xmu.oomall.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

@RestController
@RequestMapping(value = "/platforms/{did}", produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final CommentService commentService;

    /**
     * 查询评论详情，所有状态的评论都可查询
     * @param commentId
     * author Wuyuzhu
     * @return
     */
    @GetMapping("/comment/{commentId}")
    @Transactional(propagation = Propagation.REQUIRED)
    @Audit(departName = "platforms")
    public ReturnObject getCommentById(@PathVariable Long did,@PathVariable("commentId") Long commentId) {
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "评论", commentId, did));
        }
        Comment comment = this.commentService.findCommentById(commentId);
        return new ReturnObject(new CommentVo(comment));
    }


    /**
     * 管理员审核评论
     * @param commentId
     * author Liuzhiwen
     * @return
     */
    @PutMapping("/comments/{commentId}/reviews")
    @Audit(departName = "platforms")
    public ReturnObject auditComment(@PathVariable Long did,@PathVariable("commentId") Long commentId,
                                     @LoginUser UserDto user,
                                     @RequestBody AuditCommentDto auditCommentDto) {
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "评论", commentId, did));
        }
        boolean isApproved = auditCommentDto.getIsApproved();
        String rejectReason = auditCommentDto.getRejectReason();
        String res = commentService.auditComment(commentId, isApproved, rejectReason, user);
        return new ReturnObject(ReturnNo.OK);
    }

    /**
     * 管理员审核被举报的评论
     * @param commentId
     * author Liuzhiwen
     * @return
     */
    @PutMapping("/comments/{commentId}/reports")
    @Audit(departName = "platforms")
    public ReturnObject auditReport(@PathVariable Long did,@PathVariable("commentId") Long commentId,
                                     @LoginUser UserDto user,
                                     @RequestBody AuditCommentDto auditCommentDto) {
        if (!PLATFORM.equals(did)) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_OUTSCOPE, String.format(ReturnNo.RESOURCE_ID_OUTSCOPE.getMessage(), "评论", commentId, did));
        }
        boolean isApproved = auditCommentDto.getIsApproved();
        String res = commentService.auditReport(commentId, isApproved,user);
        return new ReturnObject(ReturnNo.OK);
    }
}
