package org.forum.exception;

public class MailException extends ForumException {
    public MailException(String message, String viewName) {
        super(message, viewName);
    }

    public MailException(String message, Throwable cause, String viewName) {
        super(message, cause, viewName);
    }
}
