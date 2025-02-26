package org.forum.model.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String password;
    private String confirmPassword;
    private String username;
    private String token;
}
