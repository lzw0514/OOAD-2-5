package cn.edu.xmu.oomall.customer.controller.dto;

import cn.edu.xmu.javaee.core.validation.NewGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import cn.edu.xmu.javaee.core.aop.CopyFrom;
import jakarta.validation.constraints.NotBlank;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {
    @NotBlank(message = "用户名不能为空", groups = {NewGroup.class})
    private String userName;
    @NotBlank(message = "密码不能为空", groups = {NewGroup.class})
    private String password;
    @NotBlank(message = "联系电话不能为空", groups = {NewGroup.class})
    private String mobile;
    @NotBlank(message = "姓名不能为空", groups = {NewGroup.class})
    private String name;

    public @NotBlank(message = "用户名不能为空", groups = {NewGroup.class}) String getUserName() {
        return userName;
    }

    public void setUserName(@NotBlank(message = "用户名不能为空", groups = {NewGroup.class}) String userName) {this.userName = userName;}

    public @NotBlank(message = "密码不能为空", groups = {NewGroup.class}) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "密码不能为空", groups = {NewGroup.class}) String password) {this.password = password;}

    public @NotBlank(message = "联系方式不能为空", groups = {NewGroup.class}) String getMobile() {
        return mobile;
    }

    public void setMobile(@NotBlank(message = "联系方式不能为空", groups = {NewGroup.class}) String mobile) {this.mobile = mobile;}

    public @NotBlank(message = "姓名不能为空", groups = {NewGroup.class}) String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "姓名不能为空", groups = {NewGroup.class}) String name) {
        this.name = name;
    }
}


