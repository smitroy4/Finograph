package com.finograph.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class TestController {
    public String healthCheck(){
        return "FINOGRAPH backend is running!";
    }
}
