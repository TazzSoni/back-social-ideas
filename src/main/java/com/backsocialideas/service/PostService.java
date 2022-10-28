package com.backsocialideas.service;

import com.backsocialideas.dto.enums.Stage;
import com.backsocialideas.exception.RecordNotFoundException;
import com.backsocialideas.model.DislikePost;
import com.backsocialideas.model.LikePost;
import com.backsocialideas.model.PostEntity;
import com.backsocialideas.model.UserEntity;
import com.backsocialideas.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;
    private final UserService userService;
    private final LikeDislikeService likeDislikeService;

    public PostEntity save(Long ownerId, PostEntity entity){
        entity.setStage(Stage.POSTED);
        entity.setUser(userService.getOne(ownerId));
        PostEntity entitySaved = repository.save(entity);
        return entitySaved;
    }

    public PostEntity getOne(Long postId) {
        return repository.findById(postId).orElseThrow(() -> new RecordNotFoundException("Post não encontrado!"));
    }

    public List<PostEntity> getPostsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }


    public PostEntity like(Long id, Long userId) {
        PostEntity entity = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Post não encontrado!"));
        setLike(entity, userId);
        return repository.save(entity);
    }

    public PostEntity dislike(Long id, Long userId) {
        PostEntity entity = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Post não encontrado!"));
        setDislike(entity, userId);
        return repository.save(entity);
    }

    private void setLike(PostEntity entity, Long userId) {
        LikePost likePost = LikePost.builder().build();
        likeDislikeService.savePostLike(likePost);
        UserEntity user = userService.getOne(userId);
        user.getLikesPost().add(likePost);
        userService.save(user);
        entity.getLikes().add(likePost);
    }

    private void setDislike(PostEntity entity, Long userId) {
        DislikePost dislikePost = DislikePost.builder().build();
        likeDislikeService.savePostDislike(dislikePost);
        UserEntity user = userService.getOne(userId);
        user.getDislikesPost().add(dislikePost);
        userService.save(user);
        entity.getDislikes().add(dislikePost);
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }

    public List<PostEntity> gelAll() {
        return repository.findAll();
    }

    public List<PostEntity> getAllPageable() {
        return repository.findAll();
    }

    public List<PostEntity> searchPageable(String keyWord) {
        return repository.findPostEntityByTituloLike(keyWord);
    }
}
