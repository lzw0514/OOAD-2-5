//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.mapper.openfeign;

import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.oomall.comment.dao.bo.Shop;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("shop-service")
public interface ShopPoMapper {

    @GetMapping("/shops/{id}")
    InternalReturnObject<Shop> getShopById(@PathVariable Long id);

    /*@GetMapping("/shops/{shopId}/templates/{id}")
    InternalReturnObject<Template> getTemplateById(@PathVariable Long shopId, @PathVariable Long id);*/

}
