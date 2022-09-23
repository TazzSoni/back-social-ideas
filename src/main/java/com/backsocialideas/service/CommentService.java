package com.backsocialideas.service;

import com.backsocialideas.model.CommentEntity;
import com.backsocialideas.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final PostService postService;

    public CommentEntity save(Long postId, CommentEntity entity){
        entity.setPosts(postService.getOne(postId));
        return repository.save(entity);
    }
}
