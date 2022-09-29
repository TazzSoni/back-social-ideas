package com.backsocialideas.converter;

import com.backsocialideas.dto.*;
import com.backsocialideas.model.CommentEntity;
import com.backsocialideas.model.PostEntity;
import com.backsocialideas.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class Converter {

    private final ModelMapper mapper;

    public UserOutDTO convertUserEntityToOutDTO(UserEntity user){
        return mapper.map(user, UserOutDTO.class);
    }

    public UserEntity convertUserInDTOToEntity(UserInDTO inDTO){
        return UserEntity.builder()
                .email(inDTO.getEmail())
                .name(inDTO.getName())
                .password(inDTO.getPassword())
                .build();
    }

    public UserEntity converterUserUpdateDTOToEntity(Long id, UserUpdateDTO userUpdateDTO) {
                UserEntity entity = mapper.map(userUpdateDTO, UserEntity.class);
                entity.setId(id);
        return entity;
    }

    public List<UserOutDTO> listUserEntityToListOutDTO(List<UserEntity> entityList) {
        return entityList.stream().map(this::convertUserEntityToOutDTO).collect(Collectors.toList());
    }

    public CommentEntity convertCommentInDTOToEntity(CommentInDTO commentInDTO) {
        return mapper.map(commentInDTO, CommentEntity.class);
    }

    public CommentOutDTO convertCommentEntityToOutDTOWithId(Long postId, CommentEntity entity) {
        CommentOutDTO comment = mapper.map(entity, CommentOutDTO.class);
        comment.setPostId(postId);
        return comment;
    }

    public PostEntity convertPostInDTOToEntity(PostInDTO postInDTO) {
        return mapper.map(postInDTO, PostEntity.class);
    }

    public PostOutDTO convertPostEntityToOutDTOWithUserId(Long ownerId, PostEntity entity) {
        PostOutDTO postOutDTO = mapper.map(entity, PostOutDTO.class);
        postOutDTO.setOwnerId(ownerId);
        return postOutDTO;
    }

    public PostDTO  convertPostEntityToDTO(PostEntity entity) {
        return mapper.map(entity, PostDTO.class);
    }

    public List<PostDTO> convertListPostEntityToListDTO(List<PostEntity> postsList) {
        return postsList.stream().map(this::convertPostEntityToDTO).collect(Collectors.toList());
    }

    public List<CommentDTO> convertListCommentEntityToDTO(List<CommentEntity> commentsList) {
        return commentsList.stream().map(this::convertCommentEntityToDTO).collect(Collectors.toList());
    }

    public CommentDTO convertCommentEntityToDTO(CommentEntity entity) {
        return mapper.map(entity, CommentDTO.class);
    }
}
