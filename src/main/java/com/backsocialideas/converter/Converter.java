package com.backsocialideas.converter;

import com.backsocialideas.dto.*;
import com.backsocialideas.model.CommentEntity;
import com.backsocialideas.model.PostEntity;
import com.backsocialideas.model.ProfileImageEntity;
import com.backsocialideas.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class Converter {

    private final ModelMapper mapper;

    public UserOutDTO convertUserEntityToOutDTO(UserEntity user) {
        UserOutDTO outDTO = mapper.map(user, UserOutDTO.class);
        outDTO.setIdProfileImage((user.getProfileImageEntity() == null) ? null : String.valueOf(user.getProfileImageEntity().getId()));
        return outDTO;
    }

    public UserEntity convertUserInDTOToEntity(UserInDTO inDTO) throws IOException {
        return UserEntity.builder()
                .email(inDTO.getEmail())
                .name(inDTO.getName())
                .password(inDTO.getPassword())
                .profileImageEntity(
                        (inDTO.getProfileImage() == null)? null :setProfileImage(inDTO.getProfileImage())
                )
                .build();
    }

    public ProfileImageEntity setProfileImage(MultipartFile profileImage) throws IOException {
        if(profileImage == null){
            return null;
        }else{
            return ProfileImageEntity.builder()
                    .name(StringUtils.cleanPath(profileImage.getOriginalFilename()))
                    .contentType(profileImage.getContentType())
                    .data(profileImage.getBytes())
                    .size(profileImage.getSize())
                    .build();
        }
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

    public PostOutDTO convertPostEntityToOutDTOWithUserId(UserEntity owner, PostEntity entity) {
        PostOutDTO postOutDTO = mapper.map(entity, PostOutDTO.class);
        postOutDTO.setUser(convertUserEntityToPostUserDTO(owner));
        setRatePostOutDTo(postOutDTO, entity);
        return postOutDTO;
    }

    private PostUserDTO convertUserEntityToPostUserDTO(UserEntity owner) {
        return mapper.map(owner, PostUserDTO.class);
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

    public List<PostOutDTO> convertPostsEntityToOutDTOList(List<PostEntity> entityList) {
        return entityList.stream().map(this::convertPostEntityToOutDTO).collect(Collectors.toList());
    }

    public PostOutDTO convertPostEntityToOutDTO(PostEntity entity) {
        PostOutDTO outDTO = mapper.map(entity, PostOutDTO.class);
        outDTO.setRate(RateDTO.builder()
                .like(entity.getLikes().size())
                .dislike(entity.getDislikes().size())
                .build());
        outDTO.setComment(entity.getComment().stream().map(this::convertCommentEntityToDTO).collect(Collectors.toList()));
        return outDTO;
    }

    public Page<PostOutDTO> convertPagePostEntityToOutDTO(Page<PostEntity> page) {

        return new PageImpl<>(convertPostsEntityToOutDTOList(page.getContent()), page.getPageable(), page.getTotalElements());
    }
}
