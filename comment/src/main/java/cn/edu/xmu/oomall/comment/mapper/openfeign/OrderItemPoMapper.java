//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.comment.mapper.openfeign;

import cn.edu.xmu.oomall.comment.mapper.openfeign.po.OrderItemPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemPoMapper extends JpaRepository<OrderItemPo, Long> {

}
