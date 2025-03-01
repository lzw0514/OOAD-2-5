package cn.edu.xmu.oomall.customer.controller.vo;

import cn.edu.xmu.javaee.core.aop.CopyFrom;
import cn.edu.xmu.javaee.core.util.CloneFactory;
import cn.edu.xmu.oomall.customer.dao.bo.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Liuzhiwen
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CopyFrom({Address.class})
@Slf4j
public class AddressVo {

    private Long id;

    private Long regionId;

    private String mobile;

    private String consignee;

    private String detailAddress;

    private boolean beDefault;

    public AddressVo(Address address) {
        super();
        CloneFactory.copy(this, address);
    }

    public Long getId() {return id;}public void setId(Long id) {this.id = id;}
    public String getConsignee() {return consignee;}public void setConsignee(String consignee) {this.consignee = consignee;}
    public Long getRegionId() {return regionId;}public void setRegionId(Long regionId) {this.regionId = regionId;}
    public String getMobile() {return mobile;}public void setMobile(String cityCode) {this.mobile = cityCode;}
    public String getDetailAddress() {return detailAddress;}public void setDetailAddress(String detailAddress) {this.detailAddress = detailAddress;}
    public boolean isBeDefault() {return beDefault;}public void setBeDefault(boolean beDefault) {
        this.beDefault = beDefault;}

}
