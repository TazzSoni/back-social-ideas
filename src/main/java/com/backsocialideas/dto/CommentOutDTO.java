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
    private Long postId;
}
