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
public class UserUpdateDTO {

    private String name;
    private String email;
    private String password;
    private int level;
    private boolean teacher;
    private List<CommentEntity> comments;
    private List<PostEntity> posts;
}
