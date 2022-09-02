package com.backsocialideas.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInDTO {

    @NotBlank(message = "The name could not be null or empty.")
    private String name;
    @NotBlank(message = "The email could not be null or empty.")
    private String email;
    @NotBlank(message = "The password could not be null or empty.")
    private String password;

}
