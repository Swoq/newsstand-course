package com.swoqe.newsstand.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HealthController {

    @GetMapping("/healthz")
    @ResponseStatus(HttpStatus.OK)
    private void healthzProbe() {}

}
