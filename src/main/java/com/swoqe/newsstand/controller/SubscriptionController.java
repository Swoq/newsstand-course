package com.swoqe.newsstand.controller;

import com.swoqe.newsstand.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/{id}")
    private String performSubscription(@PathVariable UUID id) {
        subscriptionService.performSubscription(id);
        return "redirect:/account";
    }

    @PostMapping("/cancel")
    private String cancelSubscription(@RequestParam UUID subscriptionId) {
        subscriptionService.cancelSubscription(subscriptionId);
        return "redirect:/account";
    }

}
