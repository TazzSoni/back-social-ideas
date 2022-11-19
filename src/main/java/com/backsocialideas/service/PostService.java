package com.backsocialideas.service;

import com.backsocialideas.converter.Converter;
import com.backsocialideas.dto.PostInDTO;
import com.backsocialideas.dto.enums.Stage;
import com.backsocialideas.exception.BadRequestException;
import com.backsocialideas.exception.RecordNotFoundException;
import com.backsocialideas.model.*;
import com.backsocialideas.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;
    private final UserService userService;
    private final LikeDislikeService likeDislikeService;
    private final AsksForCooworkerService asksForCooworkerService;
    private final Converter converter;
    private final FileService fileService;

    public PostEntity save(Long ownerId, PostEntity entity) {
        entity.setStage(Stage.POSTED);
        entity.setUser(userService.getOne(ownerId));
        PostEntity entitySaved = repository.save(entity);
        return entitySaved;
    }

    public PostEntity getOne(Long postId) {
        return repository.findById(postId).orElseThrow(() -> new RecordNotFoundException("Post não encontrado!"));
    }

    public List<PostEntity> getPostsByUserId(Long userId) {
        return repository.findByUserIdOrCooworkerId(userId, userId);
    }


    @Transactional
    public PostEntity like(Long id, Long userId) {
        PostEntity entity = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Post não encontrado!"));
        setLike(entity, userId);
        PostEntity retorno = repository.save(entity);
        userService.atualizaLevel(entity.getUser().getId());
        return retorno;
    }

    @Transactional
    public PostEntity dislike(Long id, Long userId) {
        PostEntity entity = repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Post não encontrado!"));
        setDislike(entity, userId);
        PostEntity retorno =repository.save(entity);
        userService.atualizaLevel(userId);
        return retorno;
    }

    private void setLike(PostEntity entity, Long userId) {
        LikePost likePost = LikePost.builder().build();
        likeDislikeService.savePostLike(likePost);
        UserEntity user = userService.getOne(userId);
        user.getLikesPost().add(likePost);
        userService.saveDirectly(user);
        entity.getLikes().add(likePost);
    }

    private void setDislike(PostEntity entity, Long userId) {
        DislikePost dislikePost = DislikePost.builder().build();
        likeDislikeService.savePostDislike(dislikePost);
        UserEntity user = userService.getOne(userId);
        user.getDislikesPost().add(dislikePost);
        userService.saveDirectly(user);
        entity.getDislikes().add(dislikePost);
    }

    public void deletePost(Long id) {
        repository.deleteById(id);
    }

    public List<PostEntity> gelAll() {
        return repository.findAll();
    }

    public Page<PostEntity> getAllPageable(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<PostEntity> searchPageable(String keyWord) {
        return repository.findPostEntityByTituloLikeAndPostLike(PageRequest.of(0, 100), "%" + keyWord + "%");
    }


    public PostEntity update(Long postId, PostInDTO postUpdateDTO) throws IOException {
        PostEntity postEntity = repository.getOne(postId);
        if (postUpdateDTO.getPost() != null) {
            postEntity.setPost(postUpdateDTO.getPost());
        }
        if (postUpdateDTO.getTitulo() != null) {
            postEntity.setTitulo(postUpdateDTO.getTitulo());
        }
        if (postUpdateDTO.getTags() == null){
            postEntity.setTags(null);
        } else {
            postEntity.setTags(setPostTags(postUpdateDTO.getTags()));
        }
        if(postUpdateDTO.getFile() != null){
            postEntity.setFile(converter.setProfileImage(postUpdateDTO.getFile()));
        }else{
            if ((postEntity.getFile() != null)) {
                fileService.deleteById(postEntity.getFile().getId());
            }
            postEntity.setFile(null);
        }
        return repository.save(postEntity);
    }

    private List<Tags> setPostTags(List<String> tags) {
       return tags.stream().map(t -> Tags.builder().desTag(t).build()).collect(Collectors.toList());
    }

    public PostEntity updateStatus(Long postId, Stage stage) {
        PostEntity postEntity = repository.getOne(postId);

        postEntity.setStage(stage);

        return repository.save(postEntity);
    }

    @Transactional
    public PostEntity setPostCooworker(Long postId, Long userId) {
        PostEntity postEntity = repository.getOne(postId);
        if (postEntity.getCooworker() != null) {
            if (postEntity.getCooworker().getId() == userId) {
                throw new BadRequestException("Usuário já é colaborador!");
            }
            throw new BadRequestException("Ideia já possui colaborador!");
        }
        postEntity.setCooworker(userService.getOne(userId));
        asksForCooworkerService.deleteUserRequestIdAndPostId(postId, userId);
        return repository.save(postEntity);
    }

    public PostEntity deletePostCooworker(Long postId) {
        PostEntity postEntity = repository.getOne(postId);
        if (postEntity.getCooworker() != null) {
            postEntity.setCooworker(null);
        } else {
            throw new BadRequestException("Ideia não possui Colaborador");
        }
        return repository.save(postEntity);
    }

    public Page<PostEntity> searchByUserNamePageable(List<Long> userIds) {
        return repository.findByUserIdIn(PageRequest.of(0, 100), userIds);
    }

    public Page<PostEntity> getAllTeacherPageable(PageRequest of) {
        return repository.findAllByPostEntityByUser_Teacher(of);
    }
}
