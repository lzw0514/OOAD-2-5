package cn.edu.xmu.oomall.customer.dao.bo;


import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.oomall.customer.controller.dto.CustomerDto;
import cn.edu.xmu.oomall.customer.mapper.po.CustomerPo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, doNotUseGetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@CopyFrom({CustomerPo.class, CustomerDto.class})
public class Customer {

    private Long Id;
    private String userName;
    private String password;
    private String mobile;
    private String name;
    private Byte invalid = 0; //被创建时默认值为0
    private Double point;

    /**
     * 共三种状态
     */
    //有效
    @ToString.Exclude
    @JsonIgnore
    public static final Byte VALID = 0;
    //无效
    @ToString.Exclude
    @JsonIgnore
    public static final Byte INVALID = 1;
    //已删除
    @ToString.Exclude
    @JsonIgnore
    public static final Byte BE_DELETED = 2;

    public void release(){
        this.invalid = VALID;
    }

    public void ban(){
        this.invalid = INVALID;
        // 待补充， 取消该顾客相关订单，售后，仲裁
    }

    public void delete() {
        this.invalid = BE_DELETED;
        //待补充， 取消该顾客相关订单，售后，仲裁
    }
}
