package com.backsocialideas.service;

import com.backsocialideas.model.CommentEntity;
import com.backsocialideas.model.DislikeComment;
import com.backsocialideas.model.LikeComment;
import com.backsocialideas.repository.CommentRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final PostService postService;
    private final LikeDislikeService likeDislikeService;

    public CommentEntity save(Long postId, CommentEntity entity) {
        entity.setPosts(postService.getOne(postId));
        return repository.save(entity);
    }

    public List<CommentEntity> getCommnetsByPostId(Long postId) {
        return repository.findByPostsId(postId);
    }

    public CommentEntity like(Long id) throws NotFoundException {
        CommentEntity entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Comentário não encontrado!"));
        setLike(entity);
        return repository.save(entity);
    }

    public CommentEntity dislike(Long id) throws NotFoundException {
        CommentEntity entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Comentário não encontrado!"));
        setDislike(entity);
        return repository.save(entity);
    }

    private void setLike(CommentEntity entity) {
        LikeComment likeComment = LikeComment.builder().build();
        likeDislikeService.saveCommentLike(likeComment);
        entity.getLikes().add(likeComment);
    }

    private void setDislike(CommentEntity entity) {
        DislikeComment dislikeComment = DislikeComment.builder().build();
        likeDislikeService.saveCommentDislike(dislikeComment);
        entity.getDislikes().add(dislikeComment);
    }
}
