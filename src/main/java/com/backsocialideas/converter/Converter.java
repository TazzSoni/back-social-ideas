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

    public UserOutDTO convertUserEntityToOutDTO(UserEntity user) {
        return mapper.map(user, UserOutDTO.class);
    }

    public UserEntity convertUserInDTOToEntity(UserInDTO inDTO) {
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

    public CommentOutDTO convertCommentEntityToOutDTOWithId(CommentEntity entity) {
        CommentOutDTO commentOutDTO = mapper.map(entity, CommentOutDTO.class);
        setRateCommentOutDTO(commentOutDTO, entity);
        return commentOutDTO;
    }

    private void setRateCommentOutDTO(CommentOutDTO commentOutDTO, CommentEntity entity) {
        RateDTO rate = RateDTO.builder().build();
        if (entity.getLikes() != null) {
            rate.setLike(entity.getLikes().size());
        }
        if (entity.getDislikes() != null) {
            rate.setDislike(entity.getDislikes().size());
        }
        commentOutDTO.setRate(rate);
    }

    public PostEntity convertPostInDTOToEntity(PostInDTO postInDTO) {
        return mapper.map(postInDTO, PostEntity.class);
    }

    public PostOutDTO convertPostEntityToOutDTOWithUserId(Long ownerId, PostEntity entity) {
        PostOutDTO postOutDTO = mapper.map(entity, PostOutDTO.class);
        postOutDTO.setOwnerId(ownerId);
        setRatePostOutDTo(postOutDTO, entity);
        return postOutDTO;
    }

    private void setRatePostOutDTo(PostOutDTO postOutDTO, PostEntity entity) {
        RateDTO rate = RateDTO.builder().build();
        if (entity.getLikes() != null){
            rate.setLike(entity.getLikes().size());
        }
        if (entity.getDislikes() != null) {
            rate.setDislike(entity.getDislikes().size());
        }
        postOutDTO.setRate(rate);
    }

    public PostDTO convertPostEntityToDTO(PostEntity entity) {
        PostDTO postDTO = mapper.map(entity, PostDTO.class);
        setRatePost(postDTO, entity);
        return postDTO;
    }

    private void setRatePost(PostDTO postDTO, PostEntity entity) {
        RateDTO rate = RateDTO.builder().build();
        if (entity.getLikes() != null) {
            rate.setLike(entity.getLikes().size());
        }
        if (entity.getDislikes() != null) {
            rate.setDislike(entity.getDislikes().size());
        }
        postDTO.setRate(rate);
    }

    public List<PostDTO> convertListPostEntityToListDTO(List<PostEntity> postsList) {
        return postsList.stream().map(this::convertPostEntityToDTO).collect(Collectors.toList());
    }

    public List<CommentDTO> convertListCommentEntityToDTO(List<CommentEntity> commentsList) {
        return commentsList.stream().map(this::convertCommentEntityToDTO).collect(Collectors.toList());
    }

    public CommentDTO convertCommentEntityToDTO(CommentEntity entity) {
        CommentDTO commentDTO = mapper.map(entity, CommentDTO.class);
        setRateComment(commentDTO, entity);
        return commentDTO;
    }

    private void setRateComment(CommentDTO commentDTO, CommentEntity entity) {
        RateDTO rate = RateDTO.builder().build();
        if (entity.getLikes() != null) {
            rate.setLike(entity.getLikes().size());
        }
        if (entity.getDislikes() != null) {
            rate.setDislike(entity.getDislikes().size());
        }
        commentDTO.setRate(rate);
    }
}
