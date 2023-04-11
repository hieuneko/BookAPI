package com.phamhieu.bookapi.api.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/social")
    public String login() {
        return "index.html";
    }
}
