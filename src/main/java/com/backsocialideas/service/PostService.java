package com.backsocialideas.service;

import com.backsocialideas.dto.enums.Stage;
import com.backsocialideas.model.DislikePost;
import com.backsocialideas.model.LikePost;
import com.backsocialideas.model.PostEntity;
import com.backsocialideas.model.UserEntity;
import com.backsocialideas.repository.PostRepository;
import javassist.NotFoundException;
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
        return repository.getOne(postId);
    }

    public List<PostEntity> getPostsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }


    public PostEntity like(Long id, Long userId) throws NotFoundException {
        PostEntity entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Post não encontrado!"));
        setLike(entity, userId);
        return repository.save(entity);
    }

    public PostEntity dislike(Long id) throws NotFoundException {
        PostEntity entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Post não encontrado!"));
        setDislike(entity);
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

    private void setDislike(PostEntity entity) {
        DislikePost dislikePost = DislikePost.builder().build();
        likeDislikeService.savePostDislike(dislikePost);
        entity.getDislikes().add(dislikePost);
    }
}
