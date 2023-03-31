package com.phamhieu.bookapi;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class FirebaseInitializerTest {

    @Test
    void shouldInitializer_OK() throws IOException {
        final InputStream serviceAccount = FirebaseInitializer.class.getResourceAsStream("/BookApiAccountKeyTest.json");
        final GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        final FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
        assertNotNull(FirebaseApp.getApps());
    }

    @Test
    public void testEmptyAppName() {
        assertThrows(IllegalStateException.class, FirebaseApp::initializeApp);
    }
}