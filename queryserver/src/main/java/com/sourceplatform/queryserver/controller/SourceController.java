package com.sourceplatform.queryserver.controller;


import com.sourceplatform.queryserver.dto.KeyRequest;
import com.sourceplatform.queryserver.dto.ResponseDTO;
import com.sourceplatform.queryserver.service.SourceServiceInterface;
import com.sourceplatform.queryserver.utils.ResponseUtil;
import com.sourceplatform.server.dataaccess.Process;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SourceController {

    private Log logger = LogFactory.getLog(SourceController.class);

    private final SourceServiceInterface sourceService;

    public SourceController(SourceServiceInterface sourceService) {
        this.sourceService = sourceService;
    }

    @PostMapping("/query")
    public ResponseDTO queryProcess(@RequestBody KeyRequest key){
        if(StringUtils.isEmpty(key.getKey())){
            return ResponseUtil.errRes("KeyEmpty");
        }
        try {
            Process process = sourceService.queryProcess(key.getKey());
            return ResponseUtil.sucRes(process);
        } catch (ContractException e) {
            logger.error("QueryProcess", e);
            return ResponseUtil.errRes("TransactionError");
        }
    }

    @PostMapping("/prev")
    public ResponseDTO prevProcess(@RequestBody KeyRequest key){
        if(StringUtils.isEmpty(key.getKey())){
            return ResponseUtil.errRes("KeyEmpty");
        }
        try {
            List<Process> process = sourceService.prevProcess(key.getKey());
            return ResponseUtil.sucRes(process);
        } catch (ContractException e) {
            logger.error("PrevProcess", e);
            return ResponseUtil.errRes("TransactionError");
        }
    }

    @PostMapping("/dig")
    public ResponseDTO digProcess(@RequestBody KeyRequest key){
        if(StringUtils.isEmpty(key.getKey())){
            return ResponseUtil.errRes("KeyEmpty");
        }
        try {
            List<Process> process = sourceService.digProcess(key.getKey());
            return ResponseUtil.sucRes(process);
        } catch (ContractException e) {
            logger.error("DigProcess", e);
            return ResponseUtil.errRes("TransactionError");
        }
    }
}
