package com.phamhieu.bookapi.error;

public class UsernameNotFoundException extends NotFoundException{

    public UsernameNotFoundException(final String username) {
        super("User has username: %s not be found", username);
    }
}
