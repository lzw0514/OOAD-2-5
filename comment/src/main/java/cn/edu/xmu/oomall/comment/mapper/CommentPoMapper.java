//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.comment.mapper;

import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentPoMapper extends JpaRepository<CommentPo, Long> {

    @Query("SELECT c FROM CommentPo c WHERE c.productId = :productId AND c.status IN (1, 4)")
    Page<CommentPo> findCommentByProductId(Long productId, Pageable pageable);

    @Query("SELECT c FROM CommentPo c WHERE c.id = :commentId AND c.status IN (1, 4)")
    Optional<CommentPo> findValidCommentById(Long commentId);

    Optional<CommentPo> findByOrderitemId(Long orderitemId);
}
