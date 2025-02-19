package org.forum.model.dto;

import java.time.LocalDateTime;

public record PostInfoDto(
        Long id,
        String title,
        String content,
        LocalDateTime createDate,
        String author,
        Long commentCount
) {
}