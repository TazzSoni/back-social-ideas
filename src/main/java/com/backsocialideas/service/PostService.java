package com.backsocialideas.service;

import com.backsocialideas.dto.enums.Stage;
import com.backsocialideas.model.PostEntity;
import com.backsocialideas.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    public PostEntity save(PostEntity entity){
        entity.setStage(Stage.POSTED);
        PostEntity entitySaved = repository.save(entity);
        return entitySaved;
    }

    public PostEntity getOne(Long postId) {
        return repository.getOne(postId);
    }
}
