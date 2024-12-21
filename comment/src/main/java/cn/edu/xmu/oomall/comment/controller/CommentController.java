//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.controller;

import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.comment.controller.dto.CommentDto;
import cn.edu.xmu.oomall.comment.controller.dto.ReportCommentDto;
import cn.edu.xmu.oomall.comment.controller.vo.*;
import cn.edu.xmu.oomall.comment.dao.CommentDao;
import cn.edu.xmu.oomall.comment.dao.bo.*;
import cn.edu.xmu.oomall.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
@Slf4j
public class CommentController{

    private final CommentService commentService;
    private final CommentDao commentDao;


    /**
     * 顾客提交评论
     * author Liuzhiwen
     * @param orderitemId
     * @return
     */
    @PostMapping("/orderItem/{orderitemId}/comment")
    @Transactional(propagation = Propagation.REQUIRED)
    @Audit(departName = "customers")
    public ReturnObject createComment(@PathVariable Long orderitemId,
                                      @LoginUser UserDto user,
                                      @RequestBody CommentDto commentDto) {
        FirstComment firstComment = CloneFactory.copy(new FirstComment(), commentDto);
        FirstComment newComment = commentService.createComment(orderitemId, firstComment, user);
        return new ReturnObject(ReturnNo.CREATED,new CommentVo(newComment));
    }

    /**
     * 顾客提交追评
     * author Liuzhiwen
     * @param commentId
     * @return
     */
    @PostMapping("/comment/{commentId}/Addcomment")
    @Transactional(propagation = Propagation.REQUIRED)
    @Audit(departName = "customers")
    public ReturnObject creatAddComment(@PathVariable Long commentId,
                                      @LoginUser UserDto user,
                                      @RequestBody CommentDto commentDto) {
        AddComment addComment = CloneFactory.copy(new AddComment(), commentDto);
        AddComment newComment = commentService.createAddComment(commentId, addComment, user);
        return new ReturnObject(ReturnNo.CREATED,new CommentVo(newComment));
    }

    /**
     *  商户回复评论
     * @param shopId
     * @param commentId
     * author Wuyuzhu
     * @return
     */
    @PostMapping("/shops/{shopId}/comments/{commentId}/replies")
    @Transactional(propagation = Propagation.REQUIRED)
    @Audit(departName = "shops")
    public ReturnObject createReply(@PathVariable("commentId") Long commentId,
                                    @PathVariable("shopId") Long shopId,
                                    @LoginUser UserDto user,
                                    @RequestBody CommentDto commentDto) {
        ReplyComment replyComment = CloneFactory.copy(new ReplyComment(), commentDto);
        ReplyComment newComment = commentService.createReply(shopId, commentId, replyComment, user);
        log.debug("createnewReply:newReply={}",newComment);
        return new ReturnObject(ReturnNo.CREATED,new CommentVo(newComment));
    }


    /**
     * 顾客提交举报
     * author Liuzhiwen
     * @param commentId
     * @return
     */
    @PutMapping("/comment/{commentId}/report")
    @Transactional(propagation = Propagation.REQUIRED)
    @Audit(departName = "customers")
    public ReturnObject createReport(@PathVariable Long commentId,
                                      @LoginUser UserDto user,
                                      @RequestBody ReportCommentDto reportcommentDto) {
        String reportReason=reportcommentDto.getReportReason();
        commentService.reportComment(commentId, reportReason, user);
        return new ReturnObject(ReturnNo.OK);
    }


}
