package com.backsocialideas.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "Email não pode ser nulo ou vazio")
    private String email;
    @NotBlank(message = "senha não pode ser nula ou vazia")
    private String password;
}
