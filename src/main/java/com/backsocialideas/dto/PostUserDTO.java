package com.backsocialideas.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostUserDTO {

    private Long id;
    private String name;
    private int level;
}
