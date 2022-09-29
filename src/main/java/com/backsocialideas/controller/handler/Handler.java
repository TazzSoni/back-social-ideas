package com.backsocialideas.controller.handler;

import com.backsocialideas.converter.Converter;
import com.backsocialideas.dto.*;
import com.backsocialideas.model.CommentEntity;
import com.backsocialideas.model.PostEntity;
import com.backsocialideas.service.CommentService;
import com.backsocialideas.service.PostService;
import com.backsocialideas.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {

    private final Converter converter;
    private final UserService userService;
    private final CommentService commentService;
    private final PostService postService;

    public UserOutDTO saveUser(UserInDTO inDTO) {
        return converter.convertUserEntityToOutDTO(userService.save(converter.convertUserInDTOToEntity(inDTO)));
    }

    public void updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        userService.update(converter.converterUserUpdateDTOToEntity(id, userUpdateDTO));
    }

    public List<UserOutDTO> getAll() {
        return converter.listUserEntityToListOutDTO(userService.getAll());
    }

    public CommentOutDTO createComment(Long userId, Long postId, CommentInDTO commentInDTO) {
        CommentEntity entity = commentService.save(postId, converter.convertCommentInDTOToEntity(commentInDTO));
        userService.addComment(userId, entity);
        return converter.convertCommentEntityToOutDTOWithId(entity);
    }

    public PostOutDTO createPost(Long ownerId, PostInDTO postInDTO) {
        PostEntity postEntity = postService.save(ownerId, converter.convertPostInDTOToEntity(postInDTO));
        userService.addPost(ownerId, postEntity);
        return converter.convertPostEntityToOutDTOWithUserId(ownerId, postEntity);
    }

    public List<PostDTO> getPostsByuser(Long userId) {
        return converter.convertListPostEntityToListDTO(postService.getPostsByUserId(userId));
    }

    public List<CommentDTO> getCommentByPost(Long postId) {
        return converter.convertListCommentEntityToDTO(commentService.getCommnetsByPostId(postId));
    }

    public PostDTO likePost(Long id) throws NotFoundException {
        return converter.convertPostEntityToDTO(postService.like(id));
    }

    public PostDTO dislikePost(Long id) throws NotFoundException {
        return converter.convertPostEntityToDTO(postService.dislike(id));
    }

    public CommentDTO likeComment(Long id) throws NotFoundException {
        return converter.convertCommentEntityToDTO(commentService.like(id));
    }

    public CommentDTO dislikeComment(Long id) throws NotFoundException {
        return converter.convertCommentEntityToDTO(commentService.dislike(id));
    }
}
