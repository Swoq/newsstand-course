package com.swoqe.newsstand.controller.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
public class NewsCreationRequest {

    @NotBlank(message = "Title is mandatory")
    @Size(min = 1, max=255, message = "Title must be less than 255 symbols")
    private String title;

    @Size(max=2500, message = "Maximum size of description is 2500 symbols")
    @Column(columnDefinition = "text")
    private String description;

    @Size(max=255, message = "Preview must be less than 255 symbols")
    @Column(columnDefinition = "text")
    private String preview;

    private String imageUrl;
    private List<UUID> genresIds;
}
