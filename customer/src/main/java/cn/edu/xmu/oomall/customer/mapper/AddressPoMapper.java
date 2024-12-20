//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.customer.mapper;

import cn.edu.xmu.oomall.customer.mapper.po.AddressPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressPoMapper extends JpaRepository<AddressPo, Long> {
    Page<AddressPo> findAddressListByCustomerId(Long customerId, Pageable pageable);

    Optional<AddressPo> findByisDefaultAndCustomerId(Boolean isDefault, Long customerId);

    Long countByCustomerId(Long customerId);
}
