package com.backsocialideas.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostInDTO {

    private String titulo;
    private String post;
    private List<String> tags;
    private MultipartFile file;
}
