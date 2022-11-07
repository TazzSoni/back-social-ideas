package com.backsocialideas.controller;

import com.backsocialideas.controller.handler.Handler;
import com.backsocialideas.dto.AskForCooworkDTO;
import com.backsocialideas.dto.PostDTO;
import com.backsocialideas.dto.PostInDTO;
import com.backsocialideas.dto.PostOutDTO;
import com.backsocialideas.dto.enums.Stage;
import io.swagger.annotations.Api;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/social-ideas")
@RequiredArgsConstructor
@Api(value = "API REST")
@CrossOrigin(origins = "*")
public class PostController {

    private final Handler handler;

    @PostMapping("/post/{ownerId}")
    public ResponseEntity<PostOutDTO> createPost(@PathVariable("ownerId") Long ownerId, @RequestBody PostInDTO postInDTO) {
        return new ResponseEntity<>(handler.createPost(ownerId, postInDTO), HttpStatus.CREATED);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<PostOutDTO> updatePost(@PathVariable("postId") Long postId, @RequestBody PostInDTO postUpdateDTO){
        return new ResponseEntity<>(handler.updatePost(postId, postUpdateDTO), HttpStatus.OK);
    }

    @PatchMapping("/post/{postId}")
    public ResponseEntity<PostOutDTO> updateStatusPost(@PathVariable("postId") Long postId, Stage stage){
        return new ResponseEntity<>(handler.updateStatusPost(postId, stage), HttpStatus.OK);
    }

    @PatchMapping("/post/ask-for-cooworker{postId}")
    public ResponseEntity<AskForCooworkDTO> askForPostCooworker(@PathVariable("postId") Long postId, @RequestParam Long userRequestId){
        return new ResponseEntity<>(handler.askForPostCooworker(postId, userRequestId), HttpStatus.OK);
    }

    @PatchMapping("/post/set-cooworker{postId}")
    public ResponseEntity<PostOutDTO> setPostCooworker(@PathVariable("postId") Long postId, @RequestParam Long userRequestId){
        return new ResponseEntity<>(handler.setPostCooworker(postId, userRequestId), HttpStatus.OK);
    }

    @GetMapping("/post/request-cooworker{postId}")
    public ResponseEntity<AskForCooworkDTO> getRequestCooworkerByPost(@PathVariable("postId") Long postId){
        return new ResponseEntity<>(handler.getRequestCooworkerByPost(postId), HttpStatus.OK);
    }

    @PatchMapping("/post/delete-coworker{postId}")
    public ResponseEntity<PostOutDTO> deletePostCooworker(@PathVariable("postId") Long postId){
        return new ResponseEntity<>(handler.deletePostCooworker(postId), HttpStatus.OK);
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

    @GetMapping("/post/search-by-user")
    public ResponseEntity<Page<PostOutDTO>> searchPostsByUser(@RequestParam String userName) {
        return new ResponseEntity<>(handler.searchPostsByUserName(userName), HttpStatus.OK);
    }

    @PostMapping("/post/search")
    public ResponseEntity<Page<PostOutDTO>> searchPost(@RequestParam String keyWord) {
        return new ResponseEntity<>(handler.searchPost(keyWord), HttpStatus.OK);
    }

    @PutMapping("/post/like/{postId}")
    public ResponseEntity<PostOutDTO> likePost(@PathVariable("postId") Long id, @RequestParam Long userId) throws NotFoundException {
        return new ResponseEntity<>(handler.likePost(userId, id), HttpStatus.OK);
    }

    @PutMapping("/post/dislike/{postId}")
    public ResponseEntity<PostOutDTO> dislikePost(@PathVariable("postId") Long id, @RequestParam Long userId) throws NotFoundException {
        return new ResponseEntity<>(handler.dislikePost(userId, id), HttpStatus.OK);
    }

    @DeleteMapping("/post/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("id") Long id) {
        handler.deletePost(id);
    }
}
