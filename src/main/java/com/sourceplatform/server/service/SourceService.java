package com.sourceplatform.server.service;


import com.sourceplatform.server.dataaccess.DataAccessInterface;
import org.springframework.stereotype.Service;

@Service
public class SourceService implements SourceServiceInterface {

    private final DataAccessInterface dao;

    public SourceService(DataAccessInterface dao) {
        this.dao = dao;
    }
}
