package com.sourceplatform.queryserver.service;

import com.sourceplatform.server.dataaccess.DataAccessInterface;
import org.springframework.stereotype.Service;

@Service
public class ProcessService implements ProcessServiceInterface{

    private final DataAccessInterface dao;

    public ProcessService(DataAccessInterface dao) {
        this.dao = dao;
    }
}
