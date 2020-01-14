package by.babanin.newsportalrest.controller;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    private @NotEmpty String username;
    private @NotEmpty String password;
}
