//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.customer.mapper;

import cn.edu.xmu.oomall.customer.mapper.po.CouponPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponPoMapper extends JpaRepository<CouponPo, Long> {

    Page<CouponPo> findCouponByCustomerId(Long customerId, Pageable pageable);

    Long countByActIdAndCustomerId(Long actId, Long customerId);
}
