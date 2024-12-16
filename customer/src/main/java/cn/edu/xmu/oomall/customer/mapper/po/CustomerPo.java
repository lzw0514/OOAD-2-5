package cn.edu.xmu.oomall.customer.mapper.po;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "region_region")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@CopyFrom({Customer.class})
public class CustomerPo {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String userName;
    private String password;
    private String mobile;
    private String name;
    private Byte invalid;
    private Double point;
}
