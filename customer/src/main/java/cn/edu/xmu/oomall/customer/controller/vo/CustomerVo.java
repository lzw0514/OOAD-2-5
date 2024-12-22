package cn.edu.xmu.oomall.customer.controller.vo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@CopyFrom({Customer.class})
public class CustomerVo {
    private Long id;

    private String userName;

    private String password;

    private String mobile;

    private String name;

    private Byte status;

    private Double point;

    public CustomerVo(Customer customer) {
        super();
        CloneFactory.copy(this, customer);
    }


    public Long getId() {return id;}public void setId(Long id) {
        this.id = id;}
    public String getUserName() {return userName;}public void setUserName(String userName) {this.userName = userName;}
    public String getPassword() {return password;}public void setPassword(String password) {this.password = password;}
    public String getMobile() {return mobile;}public void setMobile(String mobile) {this.mobile = mobile;}
    public String getName() {return name;}public void setName(String name) {this.name = name;}
    public Byte getStatus() {return status;}public void setStatus(Byte status) {this.status = status;}
    public Double getPoint() {return point;}public void setPoint(Double point) {this.point = point;}
}
