package com.swoqe.newsstand.controller;

import com.swoqe.newsstand.controller.dto.PublisherCreationRequest;
import com.swoqe.newsstand.entities.PublisherEntity;
import com.swoqe.newsstand.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/publisher")
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping("/{id}")
    private String getPublisherPage(@PathVariable("id") UUID id, Model model) {
        PublisherEntity publisher = publisherService.findById(id);
        model.addAttribute("publisher", publisher);
        return "publisher";
    }

    @GetMapping("/request")
    private String getPublisherCreationPage(Model model) {
        model.addAttribute("publisher", new PublisherCreationRequest());
        return "new_publisher";
    }

    @PostMapping("/request")
    private String createPublisherRequest(@ModelAttribute("publisher") @Valid PublisherCreationRequest publisher,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors())
            return "new_publisher";

        this.publisherService.saveByRequest(publisher);
        redirectAttributes.addFlashAttribute("info", "New Publisher has been successfully updated!");
        return "redirect:/account";
    }
}
