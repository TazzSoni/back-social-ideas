package com.backsocialideas.repository;

import com.backsocialideas.model.DislikeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DislikeCommentRepository extends JpaRepository<DislikeComment, Long> {

    List<DislikeComment> getDislikeCommentByCommentId(Long id);
}
