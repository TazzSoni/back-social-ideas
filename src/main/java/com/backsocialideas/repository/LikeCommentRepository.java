package com.backsocialideas.repository;

import com.backsocialideas.model.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    List<LikeComment> getLikeCommentByCommentId(Long postId);
}
