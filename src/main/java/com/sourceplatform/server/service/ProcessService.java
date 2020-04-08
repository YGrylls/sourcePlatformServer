package com.sourceplatform.server.service;

import com.sourceplatform.server.dataaccess.DataAccessInterface;
import org.springframework.stereotype.Service;

@Service
public class ProcessService {

    private final DataAccessInterface dao;

    public ProcessService(DataAccessInterface dao) {
        this.dao = dao;
    }
}
