package com.backsocialideas.service;

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
}
