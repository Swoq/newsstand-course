package com.swoqe.newsstand.controller;

import com.swoqe.newsstand.entities.PublisherEntity;
import com.swoqe.newsstand.service.DefaultPublisherService;
import com.swoqe.newsstand.utils.PageLink;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherCatalogController {

    private final DefaultPublisherService publisherService;

    @GetMapping
    private String getCatalogPage(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String textSearch,
            @RequestParam(required = false) String sortProperty,
            @RequestParam(required = false) String sortOrder,
            Model model
    ) {

        PageLink pageLink = PageLink.createPageLink(pageSize, page, textSearch, sortProperty, sortOrder);
        Page<PublisherEntity> publishersPage = publisherService.getPublishersByPage(pageLink);
        model.addAttribute("currentPage", publishersPage.getNumber());
        model.addAttribute("totalPages", publishersPage.getTotalPages());
        model.addAttribute("publishers", publishersPage.getContent());
        return "publishers_catalog";
    }



}
