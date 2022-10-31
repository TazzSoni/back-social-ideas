package com.backsocialideas.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

    private String name;
    private String email;
    private String password;
    private MultipartFile profileImage;
}
