package com.backsocialideas.controller;

import com.backsocialideas.controller.handler.Handler;
import com.backsocialideas.dto.*;
import com.backsocialideas.exception.BadRequestException;
import io.swagger.annotations.Api;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/social-ideas")
@RequiredArgsConstructor
@Api(value = "API REST")
@CrossOrigin(origins = "*")
public class UserController {

    private final Handler handler;

    @PostMapping(path = "/user", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UserOutDTO> saveUser(@ModelAttribute UserInDTO inDTO) throws IOException {
            return new ResponseEntity<>(handler.saveUser(inDTO), HttpStatus.CREATED);
    }

    @GetMapping("/user/image/{imageId}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable("imageId") Long imageId) {
        return handler.getFileResponse(imageId);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserOutDTO> update(@PathVariable("id") Long id, @ModelAttribute UserUpdateDTO userUpdateDTO) throws IOException {
        return new ResponseEntity<>(handler.updateUser(id, userUpdateDTO), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserOutDTO>> getAll() {
        return new ResponseEntity<>(handler.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id) {
        handler.deleteUser(id);
    }

    @PatchMapping("/user/teacher/{id}")
    public ResponseEntity<UserOutDTO> setTeacher(@PathVariable("id") Long id) {
        return new ResponseEntity<>(handler.setTeacher(id), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserOutDTO> login(@RequestBody LoginDTO login) throws NotFoundException, BadHttpRequest {
        return new ResponseEntity<>(handler.login(login), HttpStatus.OK);
    }

}
