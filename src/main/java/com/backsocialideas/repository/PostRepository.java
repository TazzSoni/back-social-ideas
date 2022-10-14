package com.backsocialideas.repository;

import com.backsocialideas.model.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Optional<PostEntity> findById(Long id);

    List<PostEntity> findByUserId(Long id);
}
