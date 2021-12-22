package com.swoqe.newsstand.controller;

import com.swoqe.newsstand.entities.GenreEntity;
import com.swoqe.newsstand.entities.PublicationEntity;
import com.swoqe.newsstand.service.DefaultGenreService;
import com.swoqe.newsstand.service.DefaultPublicationsService;
import com.swoqe.newsstand.utils.PageLink;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PublicationsCatalogController {

    private final DefaultPublicationsService publicationsService;
    private final DefaultGenreService genreService;

    @GetMapping
    private String getCatalogPage(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String textSearch,
            @RequestParam(required = false) String sortProperty,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(name = "genres", required = false) Optional<List<UUID>> optGenres,
            Model model
    ) {

        PageLink pageLink = PageLink.createPageLink(pageSize, page, textSearch, sortProperty, sortOrder);
        Page<PublicationEntity> publicationsPage = optGenres.map(genres -> publicationsService.getPublicationsByPage(pageLink, genres))
                .orElseGet(() -> publicationsService.getPublicationsByPage(pageLink));
        List<GenreEntity> allGenres = genreService.findAll();

        model.addAttribute("currentPage", publicationsPage.getNumber());
        model.addAttribute("totalPages", publicationsPage.getTotalPages());
        model.addAttribute("genres", allGenres);
        model.addAttribute("publications", publicationsPage.getContent());
        return "publications_catalog";
    }


}
