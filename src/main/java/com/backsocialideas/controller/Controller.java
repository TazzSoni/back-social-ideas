package com.backsocialideas.controller;

import com.backsocialideas.controller.handler.Handler;
import com.backsocialideas.dto.UserInDTO;
import com.backsocialideas.dto.UserOutDTO;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/social-ideas")
@RequiredArgsConstructor
@Api(value = "API REST")
@CrossOrigin(origins = "*")
public class Controller {

    private final Handler handler;

    @PostMapping("/user")
    public ResponseEntity<UserOutDTO> saveUser(@RequestBody UserInDTO inDTO){
        return new ResponseEntity<>(handler.saveUser(inDTO), HttpStatus.CREATED);
    }
}
