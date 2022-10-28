package com.backsocialideas.dto;

import lombok.*;

import java.io.InputStream;
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
    private String idProfileImage;
    private List<CommentOutDTO> comments;
    private List<PostDTO> posts;
}
