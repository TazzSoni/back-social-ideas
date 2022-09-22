package com.backsocialideas.service;

import com.backsocialideas.model.PostEntity;
import com.backsocialideas.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    public PostEntity save(PostEntity entity){
        return repository.save(entity);
    }
}
