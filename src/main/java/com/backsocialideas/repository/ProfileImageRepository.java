package com.backsocialideas.repository;

import com.backsocialideas.model.ProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImageEntity, Long>{

    Optional<ProfileImageEntity> findById(long id);
}
