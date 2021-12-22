package com.swoqe.newsstand.controller;

import com.swoqe.newsstand.entities.UserEntity;
import com.swoqe.newsstand.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AuthService authService;

    @GetMapping()
    private String getAccountPage(Model model) {
        UserEntity user = authService.getCurrentUser()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        model.addAttribute("user", user);
        user.getSubscriptions().sort((o1, o2) -> (-1) * Boolean.compare(o1.isActive(), o2.isActive()));
        return "account";
    }

    @PostMapping("/replenish")
    private String replenishAccount(@RequestParam("amount") BigDecimal amount) {
        authService.replenishAccount(amount);
        return "redirect:/account";
    }
}
