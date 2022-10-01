package com.backsocialideas.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateDTO {

    private int like;
    private int dislike;
}
