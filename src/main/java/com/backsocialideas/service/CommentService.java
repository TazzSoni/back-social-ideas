package com.backsocialideas.service;

import com.backsocialideas.model.CommentEntity;
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
        int value = 0;
        if (entity.getLikes() != null) {
            value = Integer.parseInt(entity.getLikes()) + 1;
        } else {
            value = 1;
        }
        entity.setLikes(value + "");
    }

    private void setDislike(CommentEntity entity) {
        int value = 0;
        if (entity.getDislikes() != null) {
            value = Integer.parseInt(entity.getDislikes()) + 1;
        } else {
            value = 1;
        }
        entity.setDislikes(value + "");
    }
}
