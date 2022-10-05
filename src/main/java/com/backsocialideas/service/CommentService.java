package com.backsocialideas.service;

import com.backsocialideas.exception.RecordNotFoundException;
import com.backsocialideas.model.CommentEntity;
import com.backsocialideas.model.DislikeComment;
import com.backsocialideas.model.LikeComment;
import com.backsocialideas.model.UserEntity;
import com.backsocialideas.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public CommentEntity like(Long id, Long userId) {
        CommentEntity entity = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Comentário não encontrado!"));
        setLike(entity, userId);
        return repository.save(entity);
    }

    public CommentEntity dislike(Long id, Long userId) {
        CommentEntity entity = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Comentário não encontrado!"));
        setDislike(entity, userId);
        return repository.save(entity);
    }

    private void setLike(CommentEntity entity, Long userId) {
        LikeComment likeComment = LikeComment.builder().build();
        likeDislikeService.saveCommentLike(likeComment);
        UserEntity user = userService.getOne(userId);
        user.getLikesComment().add(likeComment);
        userService.save(user);
        entity.getLikes().add(likeComment);
    }

    private void setDislike(CommentEntity entity, Long userId) {
        DislikeComment dislikeComment = DislikeComment.builder().build();
        likeDislikeService.saveCommentDislike(dislikeComment);
        UserEntity user = userService.getOne(userId);
        user.getDislikesComment().add(dislikeComment);
        userService.save(user);
        entity.getDislikes().add(dislikeComment);
    }

    public void deleteComment(Long id) {
        repository.deleteById(id);
    }
}
