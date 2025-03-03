package org.forum.exception;

import lombok.Getter;

@Getter
public class ForumException extends RuntimeException {
    private final String viewName;
    public ForumException(String message, String viewName) {
        super(message);
        this.viewName = viewName;
    }
    public ForumException(String message, Throwable cause, String viewName) {
        super(message, cause);
        this.viewName = viewName;
    }
}
