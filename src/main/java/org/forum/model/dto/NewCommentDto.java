package org.forum.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewCommentDto {
        private String content;
        private Long parentId;
}
