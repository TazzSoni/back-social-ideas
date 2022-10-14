package com.backsocialideas.service;

import com.backsocialideas.dto.LoginDTO;
import com.backsocialideas.exception.RecordNotFoundException;
import com.backsocialideas.model.CommentEntity;
import com.backsocialideas.model.PostEntity;
import com.backsocialideas.model.UserEntity;
import com.backsocialideas.repository.UserRepository;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public static void deleteDisLikePost(UserEntity user, Long id) {

    }

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
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException("User não encontrado"));
    }

    public void addPost(Long ownerId, PostEntity postEntity) {
        UserEntity userEntity = repository.findById(ownerId).orElseThrow(() -> new RecordNotFoundException("User não encontrado"));
        userEntity.getPosts().add(postEntity);
        repository.save(userEntity);
    }

    public void addComment(Long ownerId, CommentEntity commentEntity) {
        UserEntity userEntity = repository.findById(ownerId).orElseThrow(() -> new RecordNotFoundException("User não encontrado"));
        userEntity.getComments().add(commentEntity);
        repository.save(userEntity);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    public UserEntity setTeacher(Long id) {
        UserEntity teacher = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("User não encontrado"));
        teacher.setTeacher(true);
        return repository.save(teacher);
    }

    public UserEntity login(LoginDTO login) throws BadHttpRequest {
        UserEntity user = repository.getByEmail(login.getEmail()).orElseThrow(() -> new RecordNotFoundException("Email não encontrado"));
        if(user.getPassword().equals(login.getPassword())) {
            return user;
        }else{
            throw new BadHttpRequest();
        }
    }
}
