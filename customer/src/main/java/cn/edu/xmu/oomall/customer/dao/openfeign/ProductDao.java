package cn.edu.xmu.oomall.customer.dao.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductDao {

    @GetMapping("/product/{id}/isGroupPurchase")
    boolean isGroupPurchase(@PathVariable("id") int productId);

    @GetMapping("/product/{id}/isPreSale")
    boolean isPreSale(@PathVariable("id") int productId);
}
