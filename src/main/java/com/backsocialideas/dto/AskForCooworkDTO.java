package com.backsocialideas.dto;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AskForCooworkDTO {

    private Long id;
    private Long userOwnerId;
    private Long userRequestId;
}
