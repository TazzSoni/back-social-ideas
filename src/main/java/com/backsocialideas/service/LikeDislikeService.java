package com.backsocialideas.service;

import com.backsocialideas.model.DislikeComment;
import com.backsocialideas.model.DislikePost;
import com.backsocialideas.model.LikeComment;
import com.backsocialideas.model.LikePost;
import com.backsocialideas.repository.DislikeCommentRepository;
import com.backsocialideas.repository.DislikePostRepository;
import com.backsocialideas.repository.LikeCommentRepository;
import com.backsocialideas.repository.LikePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeDislikeService {

    private final LikePostRepository likePostRepository;
    private final DislikePostRepository dislikePostRepository;

    private final LikeCommentRepository likeCommentRepository;
    private final DislikeCommentRepository dislikeCommentRepository;

    public LikePost savePostLike(LikePost like){
        return likePostRepository.save(like);
    }

    public DislikePost savePostDislike(DislikePost dislike){
        return dislikePostRepository.save(dislike);
    }
    public LikeComment saveCommentLike(LikeComment like){
        return likeCommentRepository.save(like);
    }

    public DislikeComment saveCommentDislike(DislikeComment dislike){
        return dislikeCommentRepository.save(dislike);
    }

    public List<LikePost> getLikeByPostId(Long postId) {
        return likePostRepository.getLikePostByPostId(postId);
    }

    public List<DislikePost> getDislikeByPostId(Long dislikePostId) {
        return dislikePostRepository.getDislikePostByPostId(dislikePostId);
    }

    public List<LikeComment> getLikeByCommentId(Long likeCommentId) {
        return likeCommentRepository.getLikeCommentByCommentId(likeCommentId);
    }

    public List<DislikeComment> getDislikeByCommentId(Long dislikePostId) {
        return dislikeCommentRepository.getDislikeCommentByCommentId(dislikePostId);
    }
}
