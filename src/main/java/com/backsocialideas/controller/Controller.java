package com.backsocialideas.controller;

import com.backsocialideas.controller.handler.Handler;
import com.backsocialideas.dto.*;
import io.swagger.annotations.Api;
import javassist.NotFoundException;
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

    @PostMapping("/comment/{postId}/{userId}")
    public ResponseEntity<CommentOutDTO> createComment(@PathVariable("postId") Long postId, @PathVariable("userId") Long userId, @RequestBody CommentInDTO commentInDTO){
        return new ResponseEntity<>(handler.createComment(userId, postId, commentInDTO), HttpStatus.CREATED);
    }

    @PostMapping("/post/{id}")
    public ResponseEntity<PostOutDTO> createPost(@PathVariable("id") Long ownerId, @RequestBody PostInDTO postInDTO){
        return new ResponseEntity<>(handler.createPost(ownerId, postInDTO), HttpStatus.CREATED);
    }

    @GetMapping("/post/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable("userId") Long userId){
        return new ResponseEntity<>(handler.getPostsByuser(userId), HttpStatus.OK);
    }

    @GetMapping("/comment/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable("postId") Long postId){
        return new ResponseEntity<>(handler.getCommentByPost(postId), HttpStatus.OK);
    }

    @PutMapping("/post/like/{id}")
    public ResponseEntity<PostDTO> likePost(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<>(handler.likePost(id), HttpStatus.OK);
    }

    @PutMapping("/post/dislike/{id}")
    public ResponseEntity<PostDTO> dislikePost(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<>(handler.dislikePost(id), HttpStatus.OK);
    }

    @PutMapping("/comment/like/{id}")
    public ResponseEntity<CommentDTO> likeComment(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<>(handler.likeComment(id), HttpStatus.OK);
    }

    @PutMapping("/comment/dislike/{id}")
    public ResponseEntity<CommentDTO> dislikeComment(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<>(handler.dislikeComment(id), HttpStatus.OK);
    }
}
