package com.backsocialideas.controller.handler;

import com.backsocialideas.converter.Converter;
import com.backsocialideas.dto.*;
import com.backsocialideas.model.PostEntity;
import com.backsocialideas.model.UserEntity;
import com.backsocialideas.service.CommentService;
import com.backsocialideas.service.PostService;
import com.backsocialideas.service.UserService;
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

    public CommentOutDTO createComment(Long postId, CommentInDTO commentInDTO) {
        return converter.convertCommentEntityToOutDTO(commentService.save(postId, converter.convertCommentInDTOToEntity(commentInDTO)));
    }

    public PostOutDTO createPost(Long ownerId, PostInDTO postInDTO) {
        PostEntity postEntity = postService.save(converter.convertPostInDTOToEntity(postInDTO));
        userService.addPost(ownerId, postEntity);
        return converter.convertPostEntityToOutDTO(postEntity);
    }
}
