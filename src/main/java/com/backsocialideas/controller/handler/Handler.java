package com.backsocialideas.controller.handler;

import com.backsocialideas.converter.UserConverter;
import com.backsocialideas.dto.UserInDTO;
import com.backsocialideas.dto.UserOutDTO;
import com.backsocialideas.dto.UserUpdateDTO;
import com.backsocialideas.model.UserEntity;
import com.backsocialideas.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {

    private final UserConverter converter;
    private final UserService service;

    public UserOutDTO saveUser(UserInDTO inDTO) {
        return converter.convertUserEntityToOutDTO(service.save(converter.convertUserInDTOToEntity(inDTO)));
    }

    public void updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        service.update(converter.converterUserUpdateDTOToEntity(id, userUpdateDTO));
    }

    public List<UserOutDTO> getAll() {
        return converter.listUserEntityToListOutDTO(service.getAll());
    }
}
