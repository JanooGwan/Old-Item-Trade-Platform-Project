package com.example.olditemtradeplatform.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

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

    @GetMapping("/post/{postId}")
    public String viewPost(@PathVariable Long postId) {
        return "forward:/viewpost.html";
    }

    @GetMapping("/editpost/{postId}")
    public String editPost(@PathVariable Long postId) {
        return "forward:/editpost.html";
    }

    @GetMapping("/mypage/{memberId}")
    public String myPage(@PathVariable Long memberId) { return "mypage"; }
}
