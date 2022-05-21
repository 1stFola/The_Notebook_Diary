package com.technophiles.thenotebook.exceptions;

public class UserNotFoundException extends NoteBookAppException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
