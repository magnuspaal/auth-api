package com.magnus.authapi.auth.dto;

import com.magnus.authapi.controllers.exception.ApiExceptionCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotNull(message = ApiExceptionCode.FIRSTNAME_NULL)
    @Size(min=1, message = ApiExceptionCode.FIRSTNAME_TOO_SHORT)
    @Size(max=50, message = ApiExceptionCode.FIRSTNAME_TOO_LONG)
    private String firstName;

    @NotNull(message = ApiExceptionCode.LASTNAME_NULL)
    @Size(min=1, message = ApiExceptionCode.LASTNAME_TOO_SHORT)
    @Size(max=50, message = ApiExceptionCode.LASTNAME_TOO_LONG)
    private String lastName;

    @NotNull(message = ApiExceptionCode.PASSWORD_NULL)
    @Size(min=6, message = ApiExceptionCode.PASSWORD_TOO_SHORT)
    @Size(max=100, message = ApiExceptionCode.PASSWORD_TOO_LONG)
    private String password;

    @NotNull(message = ApiExceptionCode.EMAIL_NULL)
    @Size(min=3, message = ApiExceptionCode.INVALID_EMAIL)
    @Size(max = 100, message = ApiExceptionCode.INVALID_EMAIL)
    @Email(message = ApiExceptionCode.INVALID_EMAIL)
    private String email;

    @NotNull(message = ApiExceptionCode.USERNAME_NULL)
    @Size(min=1, message = ApiExceptionCode.USERNAME_TOO_SHORT)
    @Size(max=100, message = ApiExceptionCode.USERNAME_TOO_LONG)
    private String username;
}
