package com.backsocialideas.service;

import com.backsocialideas.model.PostEntity;
import com.backsocialideas.model.UserEntity;
import com.backsocialideas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserEntity save(UserEntity entity){
        return repository.save(entity);
    }

    public UserEntity update(UserEntity entity){
        return repository.save(entity);
    }

    public List<UserEntity> getAll() {
       return repository.findAll();
    }

    public UserEntity getOne(Long id) {
        return repository.getOne(id);
    }

    public void addPost(Long ownerId, PostEntity postEntity) {
        UserEntity userEntity = repository.getOne(ownerId);
        userEntity.getPosts().add(postEntity);
        repository.save(userEntity);
    }
}
