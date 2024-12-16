//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.controller.dto;

import cn.edu.xmu.javaee.core.validation.NewGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

/**
 * 评论DTO对象
 * @author Shuyang Xing
 **/
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuditCommentDto {

    @NotBlank(message = "审核结果不能为空", groups = {NewGroup.class})
    protected boolean isApproved;

    protected String rejectReason;

    public boolean getIsApproved() { return isApproved; }
    public void setIsApproved(boolean isApproved) { this.isApproved = isApproved; }

    public String getRejectReason() { return rejectReason; }
    public void setRejectReason(String rejectReason) { this.rejectReason = rejectReason; }

}
