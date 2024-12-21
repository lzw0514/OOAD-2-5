package cn.edu.xmu.oomall.comment.controller.dto;

import cn.edu.xmu.javaee.core.validation.NewGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportCommentDto {

    @NotBlank(message = "举报原因不能为空", groups = {NewGroup.class})
    protected String reportReason;

    public String getReportReason() { return reportReason; }
    public void setReportReason(String rejectReason) { this.reportReason = reportReason; }
}
