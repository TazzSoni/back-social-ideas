package com.backsocialideas.repository;

import com.backsocialideas.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> getByEmail(String email);
    Optional<UserEntity> findById(Long id);
    List<UserEntity> findAllByEmailEquals(String email);

    @Query(value = "select * from users where lower(des_name) like lower(:userName)", nativeQuery = true)
    List<Long> searchByUserName(String userName);
}
