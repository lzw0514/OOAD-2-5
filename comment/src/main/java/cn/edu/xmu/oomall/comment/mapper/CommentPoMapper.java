//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.mapper;

import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentPoMapper extends JpaRepository<CommentPo, Long> {

    Page<CommentPo> findCommentByProductId(Long productId, Pageable pageable);
}
