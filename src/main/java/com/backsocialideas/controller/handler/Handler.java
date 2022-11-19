package com.backsocialideas.controller.handler;

import com.backsocialideas.converter.Converter;
import com.backsocialideas.dto.*;
import com.backsocialideas.dto.enums.Stage;
import com.backsocialideas.exception.BadRequestException;
import com.backsocialideas.model.*;
import com.backsocialideas.service.*;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {

    private final Converter converter;
    private final UserService userService;
    private final CommentService commentService;
    private final PostService postService;
    private final LikeDislikeService likeDislikeService;
    private final FileService fileService;
    private final AsksForCooworkerService asksForCooworkerService;

    @Transactional
    public UserOutDTO saveUser(UserInDTO inDTO) throws IOException {
        return converter.convertUserEntityToOutDTO(userService.save(converter.convertUserInDTOToEntity(inDTO)));
    }

    public UserOutDTO getUserById(Long id) throws IOException {
        return converter.convertUserEntityToOutDTO(userService.getOne(id));
    }

    @Transactional
    public UserOutDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) throws IOException {
        return converter.convertUserEntityToOutDTO(userService.update(id, userUpdateDTO));
    }

    public List<UserOutDTO> getAll() {
        return converter.listUserEntityToListOutDTO(userService.getAll());
    }

    public List<AskForCooworkDTO> getAllAskCoorworker() {
        return converter.convertAskForWorkerEntityListToDTO(asksForCooworkerService.findAll());
    }

    @Transactional
    public CommentOutDTO createComment(Long userId, Long postId, CommentInDTO commentInDTO) {
        CommentEntity entity = commentService.save(postId, converter.convertCommentInDTOToEntity(commentInDTO));
        userService.addComment(userId, entity);
        return converter.convertCommentEntityToOutDTOWithId(entity);
    }

    @Transactional
    public PostOutDTO createPost(Long ownerId, PostInDTO postInDTO) throws IOException {
        PostEntity postEntity = postService.save(ownerId, converter.convertPostInDTOToEntity(postInDTO));
        userService.addPost(ownerId, postEntity);
        return converter.convertPostEntityToOutDTOWithUserId(userService.getOne(ownerId), postEntity);
    }

    public List<PostOutDTO> getPostsByuser(Long userId) {
        return converter.convertPostsEntityToOutDTOList(postService.getPostsByUserId(userId));
    }

    public List<CommentDTO> getCommentByPost(Long postId) {
        return converter.convertListCommentEntityToDTO(commentService.getCommnetsByPostId(postId));
    }

    public PostOutDTO likePost(Long userId, Long id) throws NotFoundException {
        if (checkUserLikePost(userId, id)) {
            return null;
        } else if (checkUserDislikePost(userId, id)) {
            deleteDislikePost(userId, id);
            return converter.convertPostEntityToOutDTO(postService.like(id, userId));
        } else {
            return converter.convertPostEntityToOutDTO(postService.like(id, userId));
        }
    }

    public PostOutDTO dislikePost(Long userId, Long id) throws NotFoundException {
        if (checkUserDislikePost(userId, id)) {
            return null;
        } else if (checkUserLikePost(userId, id)) {
            deleteLikePost(userId, id);
            return converter.convertPostEntityToOutDTO(postService.dislike(id, userId));
        } else {
            return converter.convertPostEntityToOutDTO(postService.dislike(id, userId));
        }
    }

    public CommentDTO likeComment(Long userId, Long id) throws NotFoundException {
        if (checkUserLikeComment(userId, id)) {
            return null;
        } else if (checkUserDislikeComment(userId, id)) {
            deleteDislikeComment(userId, id);
            return converter.convertCommentEntityToDTO(commentService.like(id, userId));
        } else {
            return converter.convertCommentEntityToDTO(commentService.like(id, userId));
        }
    }

    public CommentDTO dislikeComment(Long userId, Long id) throws NotFoundException {
        if (checkUserDislikeComment(userId, id)) {
            return null;
        } else if (checkUserLikeComment(userId, id)) {
            deleteLikeComment(userId, id);
            return converter.convertCommentEntityToDTO(commentService.dislike(id, userId));
        } else {
            return converter.convertCommentEntityToDTO(commentService.dislike(id, userId));
        }
    }

    private boolean checkUserLikePost(Long userId, Long postId) {
        UserEntity user = userService.getOne(userId);
        List<LikePost> likePosts = likeDislikeService.getLikeByPostId(postId);
        List<DislikePost> dislikePosts = likeDislikeService.getDislikeByPostId(postId);
        for (LikePost likesUser : user.getLikesPost()) {
            for (LikePost likesBanco : likePosts) {
                if ((likesBanco.getId() == likesUser.getId()) && (likesBanco.getUser().getId() == likesUser.getUser().getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkUserDislikePost(Long userId, Long dislikePostId) {
        UserEntity user = userService.getOne(userId);
        List<DislikePost> dislikePosts = likeDislikeService.getDislikeByPostId(dislikePostId);
        for (DislikePost dislikesUser : user.getDislikesPost()) {
            for (DislikePost dislikesBanco : dislikePosts) {
                if ((dislikesBanco.getId() == dislikesUser.getId()) && (dislikesBanco.getUser().getId() == dislikesUser.getUser().getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkUserLikeComment(Long userId, Long likeCommentId) {
        UserEntity user = userService.getOne(userId);
        List<LikeComment> likeComments = likeDislikeService.getLikeByCommentId(likeCommentId);
        for (LikeComment likeComment : user.getLikesComment()) {
            for (LikeComment likesBancoo : likeComments) {
                if ((likesBancoo.getId() == likeComment.getId()) && (likesBancoo.getUser().getId() == likeComment.getUser().getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkUserDislikeComment(Long userId, Long dislikePostId) {
        UserEntity user = userService.getOne(userId);
        List<DislikeComment> dislikeComments = likeDislikeService.getDislikeByCommentId(dislikePostId);
        for (DislikeComment dislikeComment : user.getDislikesComment()) {
            for (DislikeComment dislikesBanco : dislikeComments) {
                if ((dislikesBanco.getId() == dislikeComment.getId()) && (dislikesBanco.getUser().getId() == dislikeComment.getUser().getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void deleteLikePost(Long userId, Long id) {
        UserEntity user = userService.getOne(userId);
        PostEntity post = postService.getOne(id);
        List<LikePost> likePosts = likeDislikeService.getLikeByPostId(id);
        LikePost likeToDelete = null;
        for (LikePost likesUser : user.getLikesPost()) {
            for (LikePost likesBanco : likePosts) {
                if ((likesBanco.getId() == likesUser.getId()) && (likesBanco.getUser().getId() == likesUser.getUser().getId())) {
                    likeToDelete = likesBanco;
                    likeDislikeService.deleteLikePost(likesBanco.getId());
                }
            }
        }
        if (likeToDelete != null) {
            user.getLikesPost().remove(likeToDelete);
            post.getLikes().remove(likeToDelete);
        }
    }

    private void deleteDislikePost(Long userId, Long id) {
        UserEntity user = userService.getOne(userId);
        PostEntity post = postService.getOne(id);
        List<DislikePost> dislikePosts = likeDislikeService.getDislikeByPostId(id);
        DislikePost dislikeToDelete = null;
        for (DislikePost dislikesUser : user.getDislikesPost()) {
            for (DislikePost dislikesBanco : dislikePosts) {
                if ((dislikesBanco.getId() == dislikesUser.getId()) && (dislikesBanco.getUser().getId() == dislikesUser.getUser().getId())) {
                    dislikeToDelete = dislikesBanco;
                    likeDislikeService.deleteDislikePost(dislikesBanco.getId());
                }
            }
        }
        if (dislikeToDelete != null) {
            user.getDislikesPost().remove(dislikeToDelete);
            post.getDislikes().remove(dislikeToDelete);
        }
    }

    private void deleteLikeComment(Long userId, Long id) {
        UserEntity user = userService.getOne(userId);
        CommentEntity comment = commentService.getOne(id);
        List<LikeComment> likeComments = likeDislikeService.getLikeByCommentId(id);
        LikeComment likeToDelete = null;
        for (LikeComment likeComment : user.getLikesComment()) {
            for (LikeComment likesBanco : likeComments) {
                if ((likesBanco.getId() == likeComment.getId()) && (likesBanco.getUser().getId() == likeComment.getUser().getId())) {
                    likeToDelete = likesBanco;
                    likeDislikeService.deleteLikeComment(likesBanco);
                }
            }
        }
        if (likeToDelete != null) {
            user.getLikesComment().remove(likeToDelete);
            comment.getLikes().remove(likeToDelete);
        }
    }

    private void deleteDislikeComment(Long userId, Long id) {
        UserEntity user = userService.getOne(userId);
        CommentEntity comment = commentService.getOne(id);
        List<DislikeComment> dislikeComments = likeDislikeService.getDislikeByCommentId(id);
        DislikeComment dislikeToDelete = null;
        for (DislikeComment dislikeComment : user.getDislikesComment()) {
            for (DislikeComment dislikesBanco : dislikeComments) {
                if ((dislikesBanco.getId() == dislikeComment.getId()) && (dislikesBanco.getUser().getId() == dislikeComment.getUser().getId())) {
                    dislikeToDelete = dislikesBanco;
                    likeDislikeService.deleteDislikeComment(dislikesBanco);
                }
            }
        }
        if (dislikeToDelete != null) {
            user.getDislikesComment().remove(dislikeToDelete);
            comment.getDislikes().remove(dislikeToDelete);
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentService.deleteComment(id);
    }

    @Transactional
    public void deletePost(Long id) {
        postService.deletePost(id);
    }

    public UserOutDTO setTeacher(Long id) {
        return converter.convertUserEntityToOutDTO(userService.setTeacher(id));
    }

    public UserOutDTO login(LoginDTO login) throws NotFoundException, BadHttpRequest {
        return converter.convertUserEntityToOutDTO(userService.login(login));
    }

    public List<PostOutDTO> postsGetAll() {
        return converter.convertPostsEntityToOutDTOList(postService.gelAll());
    }

    public Page<PostOutDTO> postsGetAllPageable(int page, int size) {
        return converter.convertPagePostEntityToOutDTO(postService.getAllPageable(PageRequest.of(page, size)));
    }

    public Page<PostOutDTO> searchPost(String keyWord) {
        return converter.convertPagePostEntityToOutDTO(postService.searchPageable(keyWord));
    }

    public ResponseEntity<byte[]> getFileResponse(Long imageId) {
        ProfileImageEntity fileEntity = fileService.getProfileImageById(imageId);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf(fileEntity.getContentType()));
        header.setContentLength(fileEntity.getData().length);
        header.set("Content-Disposition", "attachment; filename=" + fileEntity.getName());

        return new ResponseEntity<>(fileEntity.getData(), header, HttpStatus.OK);
    }

    @Transactional
    public PostOutDTO updatePost(Long postId, PostInDTO postUpdateDTO) throws IOException {
        return converter.convertPostEntityToOutDTO(postService.update(postId, postUpdateDTO));
    }

    public PostOutDTO updateStatusPost(Long postId, Stage stage) {
        return converter.convertPostEntityToOutDTO(postService.updateStatus(postId, stage));
    }

    public PostOutDTO setPostCooworker(Long postId, Long userId) {
        return converter.convertPostEntityToOutDTO(postService.setPostCooworker(postId, userId));
    }

    public PostOutDTO deletePostCooworker(Long postId) {
        return converter.convertPostEntityToOutDTO(postService.deletePostCooworker(postId));
    }

    public Page<PostOutDTO> searchPostsByUserName(String userName) {
        List<Long> userIds = userService.searchUserByName(userName);
        return converter.convertPagePostEntityToOutDTO(postService.searchByUserNamePageable(userIds));
    }

    public AskForCooworkDTO askForPostCooworker(Long postId, Long userRequestId) {
        PostEntity postEntity = postService.getOne(postId);
        AsksForCooworker pedido = asksForCooworkerService.getByPost(postId);
        if (postEntity.getCooworker() == null) {
            if (pedido == null) {
                return converter.convertAskForWorkerEntityToDTO(asksForCooworkerService.save(postId, postEntity.getUser().getId(), userRequestId));
            } else {
                throw new BadRequestException("Post já tem solicitação");
            }
        } else {
            throw new BadRequestException("Post já tem colaborador");
        }
    }

    public List<AskForCooworkDTO> requestsForCooworker(Long userid) {

        List<AskForCooworkDTO> retorno = converter.convertAskForWorkerEntityListToDTO(asksForCooworkerService.getByUserOwner(userid));
        retorno.stream().forEach(a -> a.setUserRequestName(userService.getOne(a.getUserRequestId()).getName()));
        return retorno;
    }

    public AskForCooworkDTO getRequestCooworkerByPost(Long postId) {
        AsksForCooworker entity = asksForCooworkerService.getByPost(postId);
        AskForCooworkDTO retorno = converter.convertAskForWorkerEntityToDTO(entity);
        if (retorno != null) {
            retorno.setUserRequestName(userService.getOne(entity.getUserRequestId()).getName());
        }
        return retorno;
    }

    public void deleteAksCooworker(Long id) {
        asksForCooworkerService.deleteById(id);
    }

    public RateDTO getRateuser(Long userid) {
        return userService.getRateUser(userid);
    }

    public Page<PostOutDTO> postsGetAllTeacherPageable(int page, int size) {
            return converter.convertPagePostEntityToOutDTO(postService.getAllTeacherPageable(PageRequest.of(page, size)));
    }
}
