package com.backsocialideas.repository;

import com.backsocialideas.model.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Long> {

    LikePost getLikePostByPostId(Long id);
}
