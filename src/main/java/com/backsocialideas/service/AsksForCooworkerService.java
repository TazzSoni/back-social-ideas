package com.backsocialideas.service;

import com.backsocialideas.model.AsksForCooworker;
import com.backsocialideas.model.PostEntity;
import com.backsocialideas.repository.AsksForCooworkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AsksForCooworkerService {

    private final AsksForCooworkerRepository repository;

    public AsksForCooworker save(Long postId, Long userOwnerId, Long userRequestId) {
        return repository.save(
                AsksForCooworker.builder()
                        .postId(postId)
                        .userOwnerId(userOwnerId)
                        .userRequestId(userRequestId)
                        .build());
    }

    public List<AsksForCooworker> getByUserOwner(Long userid) {
        return repository.getAllByUserOwnerId(userid);
    }

    public void deleteUserRequestIdAndPostId(Long postId, Long userId) {
        AsksForCooworker entity = repository.getByPostIdAndUserRequestId(postId, userId);
        repository.delete(entity);
    }
}
