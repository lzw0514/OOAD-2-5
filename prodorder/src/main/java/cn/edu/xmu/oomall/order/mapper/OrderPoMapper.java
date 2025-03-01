//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.order.mapper;

import cn.edu.xmu.oomall.order.mapper.po.OrderItemPo;
import cn.edu.xmu.oomall.order.mapper.po.OrderPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderPoMapper extends JpaRepository<OrderPo, Long> {

    OrderPo findOrderById(Long Id);

    List<OrderPo> findByShopId(Long shopId);

    @Query("select o from OrderItemPo o where o.orderId = :orderId")
    List<OrderItemPo> findItemByOrderId(@Param("orderId") Long orderId);
}
