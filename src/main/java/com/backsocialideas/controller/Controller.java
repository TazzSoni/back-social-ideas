package com.backsocialideas.controller;

import com.backsocialideas.controller.handler.Handler;
import com.backsocialideas.dto.*;
import io.swagger.annotations.Api;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
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
public class Controller {

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
    public void update(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        handler.updateUser(id, userUpdateDTO);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserOutDTO>> getAll() {
        return new ResponseEntity<>(handler.getAll(), HttpStatus.OK);
    }

    @PostMapping("/comment/{postId}/{userId}")
    public ResponseEntity<CommentOutDTO> createComment(@PathVariable("postId") Long postId, @PathVariable("userId") Long userId, @RequestBody CommentInDTO commentInDTO) {
        return new ResponseEntity<>(handler.createComment(userId, postId, commentInDTO), HttpStatus.CREATED);
    }

    @PostMapping("/post/{ownerId}")
    public ResponseEntity<PostOutDTO> createPost(@PathVariable("ownerId") Long ownerId, @RequestBody PostInDTO postInDTO) {
        return new ResponseEntity<>(handler.createPost(ownerId, postInDTO), HttpStatus.CREATED);
    }

    @GetMapping("/post")
    public ResponseEntity<List<PostOutDTO>> postsGetAll() {
        return new ResponseEntity<>(handler.postsGetAll(), HttpStatus.OK);
    }

    @GetMapping("/post/pageable")
    public ResponseEntity<Page<PostOutDTO>> postsGetAllPageable(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(handler.postsGetAllPageable(page, size), HttpStatus.OK);
    }

    @GetMapping("/post/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(handler.getPostsByuser(userId), HttpStatus.OK);
    }

    @GetMapping("/comment/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(handler.getCommentByPost(postId), HttpStatus.OK);
    }

    @PutMapping("/post/like/{postId}")
    public ResponseEntity<PostOutDTO> likePost(@PathVariable("postId") Long id, @RequestParam Long userId) throws NotFoundException {
        return new ResponseEntity<>(handler.likePost(userId, id), HttpStatus.OK);
    }

    @PutMapping("/post/dislike/{postId}")
    public ResponseEntity<PostOutDTO> dislikePost(@PathVariable("postId") Long id, @RequestParam Long userId) throws NotFoundException {
        return new ResponseEntity<>(handler.dislikePost(userId, id), HttpStatus.OK);
    }

    @PutMapping("/comment/like/{id}")
    public ResponseEntity<CommentDTO> likeComment(@PathVariable("id") Long id, @RequestParam Long userId) throws NotFoundException {
        return new ResponseEntity<>(handler.likeComment(userId, id), HttpStatus.OK);
    }

    @PutMapping("/comment/dislike/{id}")
    public ResponseEntity<CommentDTO> dislikeComment(@PathVariable("id") Long id, @RequestParam Long userId) throws NotFoundException {
        return new ResponseEntity<>(handler.dislikeComment(userId, id), HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id) {
        handler.deleteUser(id);
    }

    @DeleteMapping("/post/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") Long id) {
        handler.deletePost(id);
    }

    @DeleteMapping("/comment/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("id") Long id) {
        handler.deleteComment(id);
    }

    @PatchMapping("/user/teacher/{id}")
    public ResponseEntity<UserOutDTO> setTeacher(@PathVariable("id") Long id) {
        return new ResponseEntity<>(handler.setTeacher(id), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserOutDTO> login(@RequestBody LoginDTO login) throws NotFoundException, BadHttpRequest {
        return new ResponseEntity<>(handler.login(login), HttpStatus.OK);
    }

    @PostMapping("/post/search")
    public ResponseEntity<Page<PostOutDTO>> searchPost(@RequestParam String keyWord) {
        return new ResponseEntity<>(handler.searchPost(keyWord), HttpStatus.OK);
    }
}
