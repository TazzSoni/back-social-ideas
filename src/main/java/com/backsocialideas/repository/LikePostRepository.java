package com.backsocialideas.repository;

import com.backsocialideas.model.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Long> {

    List<LikePost> getLikePostByPostId(Long id);
}
