package com.backsocialideas.service;

import com.backsocialideas.dto.enums.Stage;
import com.backsocialideas.model.PostEntity;
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


    public PostEntity like(Long id) throws NotFoundException {
        PostEntity entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Post não encontrado!"));
        setLike(entity);
        return repository.save(entity);
    }

    public PostEntity dislike(Long id) throws NotFoundException {
        PostEntity entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Post não encontrado!"));
        setDislike(entity);
        return repository.save(entity);
    }

    private void setLike(PostEntity entity) {
        int value = 0;
        if (entity.getLikes() != null) {
            value = Integer.parseInt(entity.getLikes()) + 1;
        } else {
            value = 1;
        }
        entity.setLikes(value + "");
    }

    private void setDislike(PostEntity entity) {
        int value = 0;
        if (entity.getDislikes() != null) {
            value = Integer.parseInt(entity.getDislikes()) + 1;
        } else {
            value = 1;
        }
        entity.setDislikes(value + "");
    }
}
