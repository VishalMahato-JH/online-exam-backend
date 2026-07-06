package com.exam.online_exam_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test() {
        return "JWT Protected API Working";
    }

    @GetMapping("/api/admin")
    public String admin() {
        return "Admin Access Success";
    }

    @GetMapping("/api/student")
    public String student() {
        return "Student Access Success";
    }
}