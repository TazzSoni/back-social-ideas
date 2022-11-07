package com.backsocialideas.service;

import com.backsocialideas.converter.Converter;
import com.backsocialideas.dto.LoginDTO;
import com.backsocialideas.dto.RateDTO;
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
import org.springframework.transaction.annotation.Transactional;

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
        checkEmailExistis(entity.getEmail());
        return repository.save(entity);
    }

    public UserEntity saveDirectly(UserEntity entity){
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

    private void checkEmailExistis(String email) {
       List<UserEntity> usersEmail = repository.findAllByEmailEquals(email);
       if(usersEmail.size() > 0){
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

    public List<Long> searchUserByName(String userName) {
        return repository.searchByUserName("%"+userName+"%");
    }


    @Transactional
    public void atualizaLevel(Long userId) {
        UserEntity user = repository.getOne(userId);
        List<PostEntity> posts = user.getPosts();
        int totalLikesPost = posts.stream().mapToInt(post -> post.getLikes().size()).sum();
        int totalDislikesPost = posts.stream().mapToInt(post -> post.getDislikes().size()).sum();
        List<CommentEntity> comments = user.getComments();
        int totalLikesComm = comments.stream().mapToInt(comment -> comment.getLikes().size()).sum();
        int totalDislikesComm = comments.stream().mapToInt(comment -> comment.getDislikes().size()).sum();
        int totalLikes = totalLikesPost + totalLikesComm;
        int totalDislikes = totalDislikesPost + totalDislikesComm;
        user.setLevel(totalDislikes == 0 ? totalLikes : totalLikes / totalDislikes);
        repository.save(user);
    }

    public RateDTO getRateUser(Long userid) {
        RateDTO retorno = RateDTO.builder().build();
        UserEntity user = repository.getOne(userid);
        List<PostEntity> posts = user.getPosts();
        int totalLikesPost = posts.stream().mapToInt(post -> post.getLikes().size()).sum();
        int totalDislikesPost = posts.stream().mapToInt(post -> post.getDislikes().size()).sum();
        List<CommentEntity> comments = user.getComments();
        int totalLikesComm = comments.stream().mapToInt(comment -> comment.getLikes().size()).sum();
        int totalDislikesComm = comments.stream().mapToInt(comment -> comment.getDislikes().size()).sum();
        retorno.setLike(totalLikesPost + totalLikesComm);
        retorno.setDislike(totalDislikesPost + totalDislikesComm);
        return retorno;
    }
}
