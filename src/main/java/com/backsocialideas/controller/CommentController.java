package com.backsocialideas.controller;

import com.backsocialideas.controller.handler.Handler;
import com.backsocialideas.dto.CommentDTO;
import com.backsocialideas.dto.CommentInDTO;
import com.backsocialideas.dto.CommentOutDTO;
import io.swagger.annotations.Api;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/social-ideas")
@RequiredArgsConstructor
@Api(value = "API REST")
@CrossOrigin(origins = "*")
public class CommentController {

    private final Handler handler;

    @PostMapping("/comment/{postId}/{userId}")
    public ResponseEntity<CommentOutDTO> createComment(@PathVariable("postId") Long postId, @PathVariable("userId") Long userId, @RequestBody CommentInDTO commentInDTO) {
        return new ResponseEntity<>(handler.createComment(userId, postId, commentInDTO), HttpStatus.CREATED);
    }

    @GetMapping("/comment/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(handler.getCommentByPost(postId), HttpStatus.OK);
    }

    @PutMapping("/comment/like/{id}")
    public ResponseEntity<CommentDTO> likeComment(@PathVariable("id") Long id, @RequestParam Long userId) throws NotFoundException {
        return new ResponseEntity<>(handler.likeComment(userId, id), HttpStatus.OK);
    }

    @PutMapping("/comment/dislike/{id}")
    public ResponseEntity<CommentDTO> dislikeComment(@PathVariable("id") Long id, @RequestParam Long userId) throws NotFoundException {
        return new ResponseEntity<>(handler.dislikeComment(userId, id), HttpStatus.OK);
    }

    @DeleteMapping("/comment/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("id") Long id) {
        handler.deleteComment(id);
    }
}
