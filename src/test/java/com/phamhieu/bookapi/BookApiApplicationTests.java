package com.phamhieu.bookapi;

import com.phamhieu.bookapi.configuration.JWTWebSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {BookApiApplicationTests.class, FirebaseInitializer.class, JWTWebSecurityConfig.class})
class BookApiApplicationTests {

    @Test
    void contextLoads() {
    }
}
