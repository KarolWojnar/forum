package org.forum.model.dto;

import java.time.LocalDateTime;

public record PostInfoDto(
        Long id,
        String title,
        String content,
        boolean isActive,
        LocalDateTime createDate,
        String author,
        Long commentCount
) {
}