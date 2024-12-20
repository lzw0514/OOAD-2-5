package cn.edu.xmu.oomall.customer.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto {
    private Long regionId;

    private String mobile;

    private String consignee;

    private String detailAddress;


    // Getter and Setter methods
    public Long getRegionId() {return regionId;}public void setRegionId(Long regionId) {this.regionId = regionId;}
    public String getMobile() {return mobile;}public void setMobile(String mobile) {this.mobile = mobile;}
    public String getDetailAddress() {return detailAddress;}public void setDetailAddress(String detailAddress) {this.detailAddress = detailAddress;}
    public String getConsignee() {return consignee;}public void setConsignee(String consignee) {this.consignee = consignee;}
}
