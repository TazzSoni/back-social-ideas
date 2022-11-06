package com.backsocialideas.repository;


import com.backsocialideas.model.AsksForCooworker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsksForCooworkerRepository extends JpaRepository<AsksForCooworker, Long> {
    List<AsksForCooworker> getAllByUserOwnerId(Long userId);
}
