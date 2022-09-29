package com.backsocialideas.dto;

import com.backsocialideas.model.CommentEntity;
import com.backsocialideas.model.PostEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOutDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private int level;
    private boolean teacher;
    private List<CommentOutDTO> comments;
    private List<PostDTO> posts;
}
