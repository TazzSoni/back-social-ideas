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
    private String titulo;
    private PostUserDTO user;
    private Stage stage;
    private RateDTO rate;
}
