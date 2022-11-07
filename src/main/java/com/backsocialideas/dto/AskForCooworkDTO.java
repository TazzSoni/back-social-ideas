package com.backsocialideas.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AskForCooworkDTO {

    private Long id;
    private Long userOwnerId;
    private Long userRequestId;
    private String userRequestName;
}
