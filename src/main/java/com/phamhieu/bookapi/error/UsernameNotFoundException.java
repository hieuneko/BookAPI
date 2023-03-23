package com.phamhieu.bookapi.error;

import static java.lang.String.format;

public class UsernameNotFoundException extends NotFoundException{

    public UsernameNotFoundException(final String username, Object... args) {
        super("User has username: %s not be found", username);
    }
}
