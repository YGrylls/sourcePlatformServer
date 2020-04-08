package com.sourceplatform.server.service;


import com.sourceplatform.server.dataaccess.DataAccessInterface;
import org.springframework.stereotype.Service;

@Service
public class SourceProcess {

    private final DataAccessInterface dao;

    public SourceProcess(DataAccessInterface dao) {
        this.dao = dao;
    }
}
