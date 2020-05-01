package com.sourceplatform.queryserver.service;


import com.sourceplatform.server.dataaccess.DataAccessInterface;
import com.sourceplatform.server.dataaccess.Process;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceService implements SourceServiceInterface {
    private Log logger = LogFactory.getLog(SourceService.class);

    private final DataAccessInterface dao;

    private static final int DEFAULT_DEPTH = 10;

    public SourceService(DataAccessInterface dao) {
        this.dao = dao;
    }

    @Override
    public List<Process> prevProcess(String key) throws ContractException {
        return dao.prevProcess(key);

    }


    @Override
    public List<Process> digProcess(String key) throws ContractException {
        return dao.digProcess(key,DEFAULT_DEPTH);
    }

    @Override
    public Process queryProcess(String key) throws ContractException {
        return dao.queryProcess(key);
    }
}
