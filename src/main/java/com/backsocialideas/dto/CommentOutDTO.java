package com.backsocialideas.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentOutDTO {

    private Long id;
    private String comment;
    private String likes;
    private String dislikes;
    private Long postId;
}
