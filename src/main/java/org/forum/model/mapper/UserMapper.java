package org.forum.model.mapper;

import org.forum.model.dto.UserDto;
import org.forum.model.entity.User;

public class UserMapper {
    public static User toEntity(UserDto user) {
        return new User(
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
