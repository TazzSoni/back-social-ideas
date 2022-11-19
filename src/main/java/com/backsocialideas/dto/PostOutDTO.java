package com.backsocialideas.dto;

import com.backsocialideas.dto.enums.Stage;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostOutDTO {

    private Long id;
    private String titulo;
    private String post;
    private PostUserDTO user;
    private PostUserDTO cooworker;
    private List<String> tags;
    private Stage stage;
    private RateDTO rate;
    private String fileId;
    private String fileName;
    private List<CommentDTO> comment;
}
