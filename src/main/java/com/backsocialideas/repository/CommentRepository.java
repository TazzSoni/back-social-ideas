package com.backsocialideas.repository;

import com.backsocialideas.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity> findById(Long id);

    List<CommentEntity> findByPostsId(Long postId);

    List<CommentEntity> findByUserId(Long userId);
}
