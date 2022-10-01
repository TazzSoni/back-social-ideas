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
    private Long ownerId;
    private Stage stage;
    private RateDTO rate;
}
