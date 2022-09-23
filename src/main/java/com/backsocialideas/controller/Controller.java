package com.backsocialideas.controller;

import com.backsocialideas.controller.handler.Handler;
import com.backsocialideas.dto.UserInDTO;
import com.backsocialideas.dto.UserOutDTO;
import com.backsocialideas.dto.UserUpdateDTO;
import com.backsocialideas.model.UserEntity;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PutMapping("/user/{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO){
        handler.updateUser(id, userUpdateDTO);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserOutDTO>> getAll(){
        return new ResponseEntity<>(handler.getAll(), HttpStatus.OK);
    }
}
