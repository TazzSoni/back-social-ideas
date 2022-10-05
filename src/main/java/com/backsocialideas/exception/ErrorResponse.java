package com.backsocialideas.exception;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private Collection<String> details;
}
