package com.backsocialideas.service;

import com.backsocialideas.converter.Converter;
import com.backsocialideas.dto.LoginDTO;
import com.backsocialideas.dto.UserUpdateDTO;
import com.backsocialideas.exception.BadRequestException;
import com.backsocialideas.exception.RecordNotFoundException;
import com.backsocialideas.model.CommentEntity;
import com.backsocialideas.model.PostEntity;
import com.backsocialideas.model.UserEntity;
import com.backsocialideas.repository.UserRepository;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final Converter converter;

    public static void deleteDisLikePost(UserEntity user, Long id) {

    }

    public UserEntity save(UserEntity entity){
        return repository.save(entity);
    }

    public UserEntity update(Long id, UserUpdateDTO userUpdateDTO) throws IOException {
        UserEntity userEntity = repository.getOne(id);
        if(userUpdateDTO.getName() != null) {
            userEntity.setName(userUpdateDTO.getName());
        }
        if(userUpdateDTO.getEmail() != null){
            checkEmailExistis(userUpdateDTO.getEmail(), userEntity.getEmail());
            userEntity.setEmail(userUpdateDTO.getEmail());
        }
        if(userUpdateDTO.getPassword() != null){
            userEntity.setPassword(userUpdateDTO.getPassword());
        }
        if(userUpdateDTO.getProfileImage() != null){
            userEntity.setProfileImageEntity(converter.setProfileImage(userUpdateDTO.getProfileImage()));
        }
        return repository.save(userEntity);
    }

    private void checkEmailExistis(String email, String entityEmail) {
       List<UserEntity> usersEmail = repository.findAllByEmailEquals(email);
       if(usersEmail.size() > 0){
           if(!(usersEmail.get(0).getEmail().equals(entityEmail)))
           throw new BadRequestException("Email já existe");
       }
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
