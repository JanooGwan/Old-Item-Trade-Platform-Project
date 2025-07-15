package com.example.olditemtradeplatform.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping
    public String home() {
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/writepost")
    public String writePostPage() {
        return "writepost";
    }
}
