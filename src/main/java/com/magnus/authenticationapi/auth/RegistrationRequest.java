package com.magnus.authenticationapi.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String username;
}
