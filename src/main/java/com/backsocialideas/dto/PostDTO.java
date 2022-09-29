package com.backsocialideas.dto;

import com.backsocialideas.dto.enums.Stage;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Long id;
    private Stage stage;
    private String likes;
    private String dislikes;
    private String post;
}
