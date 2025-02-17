package org.forum.exception;

public class DatabaseException extends ForumException{
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
