package com.magnus.authapi.auth.dto;

import com.magnus.authapi.controllers.error.ApiErrorCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotNull(message = ApiErrorCode.FIRSTNAME_NULL)
    @Size(min=1, message = ApiErrorCode.FIRSTNAME_TOO_SHORT)
    @Size(max=50, message = ApiErrorCode.FIRSTNAME_TOO_LONG)
    private String firstName;

    @NotNull(message = ApiErrorCode.LASTNAME_NULL)
    @Size(min=1, message = ApiErrorCode.LASTNAME_TOO_SHORT)
    @Size(max=50, message = ApiErrorCode.LASTNAME_TOO_LONG)
    private String lastName;

    @NotNull(message = ApiErrorCode.PASSWORD_NULL)
    @Size(min=6, message = ApiErrorCode.PASSWORD_TOO_SHORT)
    @Size(max=100, message = ApiErrorCode.PASSWORD_TOO_LONG)
    private String password;

    @NotNull(message = ApiErrorCode.EMAIL_NULL)
    @Size(min=3, message = ApiErrorCode.INVALID_EMAIL)
    @Size(max = 100, message = ApiErrorCode.INVALID_EMAIL)
    @Email(message = ApiErrorCode.INVALID_EMAIL)
    private String email;

    @NotNull(message = ApiErrorCode.USERNAME_NULL)
    @Size(min=1, message = ApiErrorCode.USERNAME_TOO_SHORT)
    @Size(max=100, message = ApiErrorCode.USERNAME_TOO_LONG)
    private String username;
}
