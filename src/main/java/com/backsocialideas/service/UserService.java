package com.backsocialideas.service;

import com.backsocialideas.model.UserEntity;
import com.backsocialideas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserEntity save(UserEntity entity){
        return repository.save(entity);
    }

}
