package com.backsocialideas.service;

import com.backsocialideas.exception.RecordNotFoundException;
import com.backsocialideas.model.*;
import com.backsocialideas.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final PostService postService;
    private final UserService userService;
    private final LikeDislikeService likeDislikeService;

    public CommentEntity save(Long postId, CommentEntity entity) {
        entity.setPosts(postService.getOne(postId));
        return repository.save(entity);
    }

    public List<CommentEntity> getCommnetsByPostId(Long postId) {
        return repository.findByPostsId(postId);
    }

    public List<CommentEntity> getCommnetsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Transactional
    public CommentEntity like(Long id, Long userId) {
        CommentEntity entity = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Comentário não encontrado!"));
        setLike(entity, userId);
        CommentEntity retorno = repository.save(entity);
        userService.atualizaLevel(entity.getUser().getId());
        return retorno;
    }

    @Transactional
    public CommentEntity dislike(Long id, Long userId) {
        CommentEntity entity = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Comentário não encontrado!"));
        setDislike(entity, userId);
        CommentEntity retorno =repository.save(entity);
        userService.atualizaLevel(entity.getUser().getId());
        return retorno;
    }

    private void setLike(CommentEntity entity, Long userId) {
        LikeComment likeComment = LikeComment.builder().build();
        likeDislikeService.saveCommentLike(likeComment);
        UserEntity user = userService.getOne(userId);
        user.getLikesComment().add(likeComment);
        userService.saveDirectly(user);
        entity.getLikes().add(likeComment);
    }

    private void setDislike(CommentEntity entity, Long userId) {
        DislikeComment dislikeComment = DislikeComment.builder().build();
        likeDislikeService.saveCommentDislike(dislikeComment);
        UserEntity user = userService.getOne(userId);
        user.getDislikesComment().add(dislikeComment);
        userService.saveDirectly(user);
        entity.getDislikes().add(dislikeComment);
    }

    public void deleteComment(Long id) {
        repository.deleteById(id);
    }

    public CommentEntity getOne(Long id) {
        return repository.getOne(id);
    }
}
