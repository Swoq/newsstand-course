package com.swoqe.newsstand.service;

import com.swoqe.newsstand.controller.dto.NewsCreationRequest;
import com.swoqe.newsstand.entities.PublicationEntity;

public interface PublicationService extends CrudService<PublicationEntity> {
    void saveByRequest(NewsCreationRequest publication);
}
