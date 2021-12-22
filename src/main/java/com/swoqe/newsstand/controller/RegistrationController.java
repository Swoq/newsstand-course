package com.swoqe.newsstand.controller;

import com.swoqe.newsstand.controller.dto.RegistrationRequest;
import com.swoqe.newsstand.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegistrationController {

    private final AuthService authService;

    @GetMapping("/registration")
    private String getRegistrationPage(Model model) {
        model.addAttribute("request", new RegistrationRequest());
        return "registration";
    }

    @PostMapping("/registration")
    private String performRegistration(@ModelAttribute("request") @Valid RegistrationRequest request,
                                       BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors())
            return "registration";

        if(!request.getPassword().equals(request.getMatchingPassword())) {
            model.addAttribute("matchError", "Passwords should be the same!");
            return "registration";
        }
        authService.signUpUser(request);
        return "redirect:/login";
    }
}
