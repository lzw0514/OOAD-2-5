//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.customer.mapper;

import cn.edu.xmu.oomall.customer.mapper.po.CouponActPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponActPoMapper extends JpaRepository<CouponActPo, Long> {

    Page<CouponActPo> findByStatus(Byte status, Pageable pageable);
    Optional<CouponActPo> findByIdAndStatus(Long Id,Byte status);
}
