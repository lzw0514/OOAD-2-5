//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.customer.mapper;

import cn.edu.xmu.oomall.customer.mapper.po.CartItemPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemPoMapper extends JpaRepository<CartItemPo, Long> {

    Page<CartItemPo> findByCustomerId(Long customerId, Pageable pageable);

    Optional<CartItemPo> findByProductName(String productName);

    Optional<CartItemPo> findByProductIdAndCustomerId(Long productId, Long customerId);
}
