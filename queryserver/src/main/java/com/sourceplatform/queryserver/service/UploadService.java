package com.sourceplatform.queryserver.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sourceplatform.queryserver.exception.ParseException;
import com.sourceplatform.server.dataaccess.DataAccessInterface;
import com.sourceplatform.server.dataaccess.Process;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UploadService implements UploadServiceInterface {

    private Log logger = LogFactory.getLog(UploadService.class);

    private final DataAccessInterface dao;

    public UploadService(DataAccessInterface dao) {
        this.dao = dao;
    }


    @Override
    public List<String> batchStart(byte[] JSONBytes) throws ParseException {
        Map<String, Process> map = startParse(JSONBytes);
        List<String> fail = new ArrayList<>();
        for(Map.Entry<String, Process> e : map.entrySet()){
            Process p = e.getValue();
            p.setProcessLocalId(e.getKey());
            try {
                dao.startProcess(p);
            } catch (ContractException ex) {
                fail.add(e.getKey());
            }
        }
        return fail;
    }

    @Override
    public List<String> batchLink(byte[] JSONBytes) throws ParseException {
        Map<String, Set<String>> map = linkParse(JSONBytes);
        List<String> fail = new ArrayList<>();
        for(Map.Entry<String, Set<String>> e:map.entrySet()){
            Set<String> set = e.getValue();
            try{
                dao.linkProcess(e.getKey(), new ArrayList<>(set));
            } catch (ContractException ex) {
                fail.add(e.getKey());
            }
        }
        return fail;
    }

    @Override
    public List<String> batchComplete(byte[] JSONBytes) throws ParseException {
        Map<String, Process> map = completeParse(JSONBytes);
        List<String> fail = new ArrayList<>();
        for(Map.Entry<String, Process> e : map.entrySet()){
            Process p = e.getValue();
            try {
                dao.completeProcess(e.getKey(),p.getCompleteTime(),p.getCompletePosition());
            } catch (ContractException ex) {
                fail.add(e.getKey());
            }
        }
        return fail;
    }


    // localId - process
    private Map<String, Process> startParse(byte[] JSONBytes) throws ParseException {
        try {
            Map<String, Process> map = JSON.parseObject(new String(JSONBytes), new TypeReference<Map<String,Process>>(){});
            return map;
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }

    // globalId - preKey
    private Map<String, Set<String>> linkParse(byte[] JSONBytes) throws ParseException {
        try {
            Map<String, Set<String>> map = JSON.parseObject(new String(JSONBytes), new TypeReference<Map<String,Set<String>>>(){});
            return map;
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }

    // globalId - process
    private Map<String, Process> completeParse(byte[] JSONBytes) throws ParseException {
        try {
            Map<String, Process> map = JSON.parseObject(new String(JSONBytes), new TypeReference<Map<String,Process>>(){});
            return map;
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }
}
