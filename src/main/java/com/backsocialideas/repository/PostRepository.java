package com.backsocialideas.repository;

import com.backsocialideas.model.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Optional<PostEntity> findById(Long id);

    List<PostEntity> findByUserIdOrCooworkerId(Long userId, Long cooworker);

    @Query(value = "select * from post where lower(des_title) like lower(:titulo) or lower(des_post) like lower(:titulo) or oid_version_post in (select oid_version_post from tags where lower(des_tag) like lower(:titulo))", nativeQuery = true)
    Page<PostEntity> findPostEntityByTituloLikeAndPostLike(Pageable page, String titulo);

    @Query(value = "select * from post where oid_version in :userIds", nativeQuery = true)
    Page<PostEntity> findByUserIdIn(Pageable page, List<Long> userIds);

    @Query(value = "select * from post where oid_version in (select oid_version from users where teacher = true )", nativeQuery = true)
    Page<PostEntity> findAllByPostEntityByUser_Teacher(Pageable page);
}
