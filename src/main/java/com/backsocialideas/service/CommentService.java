package com.backsocialideas.service;

import com.backsocialideas.model.CommentEntity;
import com.backsocialideas.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;

    public CommentEntity save(CommentEntity entity){
        return repository.save(entity);
    }
}
