package com.backsocialideas.dto;

import com.backsocialideas.dto.enums.Stage;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostOutDTO {

    private Long id;
    private Long ownerId;
    private Stage stage;
    private String post;
}
