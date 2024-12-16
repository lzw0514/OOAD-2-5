package cn.edu.xmu.oomall.customer.mapper;

import cn.edu.xmu.oomall.customer.mapper.po.CustomerPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerPoMapper extends JpaRepository<CustomerPo, Integer> {
    Optional<CustomerPo> findByUserName(String userName);
    Optional<CustomerPo> findByMobile(String mobile);

    CustomerPo findById(Long id);
}
