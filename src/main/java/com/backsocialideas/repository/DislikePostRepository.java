package com.backsocialideas.repository;

import com.backsocialideas.model.DislikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DislikePostRepository extends JpaRepository<DislikePost, Long> {
}
