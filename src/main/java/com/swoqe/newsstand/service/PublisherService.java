package com.swoqe.newsstand.service;

import com.swoqe.newsstand.controller.dto.PublisherCreationRequest;
import com.swoqe.newsstand.entities.PublisherEntity;

public interface PublisherService extends CrudService<PublisherEntity> {
    void saveByRequest(PublisherCreationRequest publisher);
}
