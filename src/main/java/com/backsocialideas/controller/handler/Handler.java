package com.backsocialideas.controller.handler;

import com.backsocialideas.converter.Converter;
import com.backsocialideas.dto.*;
import com.backsocialideas.model.*;
import com.backsocialideas.service.CommentService;
import com.backsocialideas.service.LikeDislikeService;
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
    private final LikeDislikeService likeDislikeService;

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

    public PostDTO likePost(Long userId, Long id) throws NotFoundException {
        if (checkUserLikePost(userId, id)) {
            return null;
        } else {
            return converter.convertPostEntityToDTO(postService.like(id, userId));
        }
    }

    public PostDTO dislikePost(Long userId, Long id) throws NotFoundException {
        if (checkUserDislikePost(userId, id)) {
            return null;
        } else {
            return converter.convertPostEntityToDTO(postService.dislike(id));
        }
    }

    public CommentDTO likeComment(Long userId, Long id) throws NotFoundException {
        if (checkUserLikeComment(userId, id)) {
            return null;
        } else {
            return converter.convertCommentEntityToDTO(commentService.like(id));
        }
    }

    public CommentDTO dislikeComment(Long userId, Long id) throws NotFoundException {
        if (checkUserDislikeComment(userId, id)) {
            return null;
        } else {
            return converter.convertCommentEntityToDTO(commentService.dislike(id));
        }
    }

    private boolean checkUserLikePost(Long userId, Long postId) {
        UserEntity user = userService.getOne(userId);
        LikePost likePost = likeDislikeService.getLikeByPostId(postId);
        return user.getLikesPost().stream().anyMatch(like -> like.equals(likePost));
    }

    private boolean checkUserDislikePost(Long userId, Long dislikePostId) {
        UserEntity user = userService.getOne(userId);
        DislikePost dislikePost = likeDislikeService.getDislikeByPostId(dislikePostId);
        return user.getLikesPost().stream().anyMatch(like -> like.equals(dislikePost));
    }

    private boolean checkUserLikeComment(Long userId, Long likeCommentId) {
        UserEntity user = userService.getOne(userId);
        LikeComment likeComment = likeDislikeService.getLikeByCommentId(likeCommentId);
        return user.getLikesPost().stream().anyMatch(like -> like.equals(likeComment));
    }

    private boolean checkUserDislikeComment(Long userId, Long dislikePostId) {
        UserEntity user = userService.getOne(userId);
        DislikeComment dislikeComment = likeDislikeService.getDislikeByCommentId(dislikePostId);
        return user.getLikesPost().stream().anyMatch(like -> like.equals(dislikeComment));
    }
}
