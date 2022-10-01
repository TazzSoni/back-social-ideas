package com.backsocialideas.dto;

import com.backsocialideas.dto.enums.Stage;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Long id;
    private Stage stage;
    private RateDTO rate;
    private String post;
}
