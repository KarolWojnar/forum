package org.forum.model.dto;

import lombok.Data;

@Data
public class NewCommentDto {
        private String content;
        private Long parentId;
}
