package com.backsocialideas.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    private String comment;
    private RateDTO rate;
}
