package com.swoqe.newsstand.controller;

import com.swoqe.newsstand.controller.dto.NewsCreationRequest;
import com.swoqe.newsstand.entities.PublicationEntity;
import com.swoqe.newsstand.service.GenreService;
import com.swoqe.newsstand.service.PublicationService;
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
@RequestMapping("/publication")
public class PublicationController {

    private final PublicationService publicationService;
    private final GenreService genreService;

    @GetMapping("/{id}")
    private String getPublicationPage(@PathVariable("id") UUID id, Model model) {
        PublicationEntity publication = publicationService.findById(id);
        model.addAttribute("publication", publication);
        return "publication";
    }

    @GetMapping("/new")
    public String getNewPublicationPage(Model model) {
        model.addAttribute("publication", new NewsCreationRequest());
        model.addAttribute("genres", this.genreService.findAll());
        return "new_publication";
    }

    @PostMapping()
    public String createNewPublication(
            @ModelAttribute("publication") @Valid NewsCreationRequest publication,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors())
            return "new_publication";

        this.publicationService.saveByRequest(publication);
        redirectAttributes.addFlashAttribute("info", "New Publication has been successfully updated!");
        return "redirect:/";
    }
}
