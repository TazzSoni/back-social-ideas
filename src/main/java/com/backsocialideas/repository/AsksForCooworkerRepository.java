package com.backsocialideas.repository;


import com.backsocialideas.model.AsksForCooworker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AsksForCooworkerRepository extends JpaRepository<AsksForCooworker, Long> {
    List<AsksForCooworker> getAllByUserOwnerId(Long userId);

    AsksForCooworker getByPostIdAndUserRequestId(Long postId, Long userRequestId);

    AsksForCooworker getByPostId(Long postId);
}
