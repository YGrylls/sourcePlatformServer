package com.sourceplatform.queryserver.service;


import com.sourceplatform.server.dataaccess.Process;
import org.hyperledger.fabric.gateway.ContractException;

import java.util.List;

public interface SourceServiceInterface {

    List<Process> prevProcess(String key) throws ContractException;
    List<Process> digProcess(String key) throws ContractException;
    Process queryProcess(String key) throws ContractException;

}
