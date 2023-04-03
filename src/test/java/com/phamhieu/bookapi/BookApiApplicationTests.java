package com.phamhieu.bookapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {BookApiApplicationTests.class, FirebaseInitializer.class})
class BookApiApplicationTests {

    @Test
    void contextLoads() {
    }
}
