package com.swoqe.newsstand.controller.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class PublisherCreationRequest {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max=255, message = "Name must be less than 255 symbols")
    private String officialName;

    @NotBlank(message = "You must provide some description")
    @Column(columnDefinition = "text")
    private String description;

    private String imageUrl;

    @NotBlank(message = "Contacts are mandatory")
    @Email
    private String contactEmail;

    @NotBlank(message = "Contacts are mandatory")
    private String contactPhone;

    @NotNull(message = "Provide information about subscription")
    private BigDecimal price;

    @Positive(message = "Must be positive integer number")
    private Long periodInDays;
}
