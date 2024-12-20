package cn.edu.xmu.oomall.customer.controller.vo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.model.vo.IdNameTypeVo;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.dao.bo.Coupon;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@CopyFrom({Customer.class})
public class CustomerVo {
    private Long Id;

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

    // Getter and Setter methods
    public Long getId() {return Id;}public void setId(Long id) {Id = id;}
    public String getUserName() {return userName;}public void setUserName(String userName) {this.userName = userName;}
    public String getPassword() {return password;}public void setPassword(String password) {this.password = password;}
    public String getMobile() {return mobile;}public void setMobile(String mobile) {this.mobile = mobile;}
    public String getName() {return name;}public void setName(String name) {this.name = name;}
    public Byte getStatus() {return status;}public void setStatus(Byte status) {this.status = status;}
    public Double getPoint() {return point;}public void setPoint(Double point) {this.point = point;}
}
