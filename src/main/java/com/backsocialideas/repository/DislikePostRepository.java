package com.backsocialideas.repository;

import com.backsocialideas.model.DislikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DislikePostRepository extends JpaRepository<DislikePost, Long> {

    List<DislikePost> getDislikePostByPostId(Long dislikePostId);
}
