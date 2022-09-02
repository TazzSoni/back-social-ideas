package com.backsocialideas.converter;

import com.backsocialideas.dto.UserInDTO;
import com.backsocialideas.dto.UserOutDTO;
import com.backsocialideas.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserConverter {

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
}
