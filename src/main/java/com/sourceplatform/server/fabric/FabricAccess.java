package com.sourceplatform.server.fabric;

import com.alibaba.fastjson.JSON;
import com.sourceplatform.server.dataaccess.DataAccessInterface;
import com.sourceplatform.server.dataaccess.Process;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Component
public class FabricAccess implements DataAccessInterface {

    private Log logger = LogFactory.getLog(FabricAccess.class);

    @Resource(name = "fabricContract")
    private Contract contract;

    @Override
    public String startProcess(String localId, Process process) throws ContractException {
        if(StringUtils.isEmpty(process.getOptionName()) ||
        StringUtils.isEmpty(process.getOwnerOrg()) ||
        StringUtils.isEmpty(process.getStartPosition()) ||
        process.getStartTime() == null) {
            throw new ContractException("Required field null");
        }
        try {
            return transaction("StartProcess",
                    String.class,
                    process.getProcessLocalId(),
                    process.getOwnerOrg(),
                    process.getOptionName(),
                    process.getStartTime().toString(),
                    process.getStartPosition(),
                    // ["0001","0002"]
                    listToString(process.getPreKey()),
                    process.getSpec());
        } catch (Exception e){
            throw new ContractException("StartProcess error", e);
        }

    }

    @Override
    public void completeProcess(Process process) {

    }

    @Override
    public void linkProcess(String key, List<String> preKeys) {

    }

    @Override
    public void addLinkedProcess(String key, List<String> preKeys) {

    }

    @Override
    public Process queryProcess(String key) throws ContractException {
        try {
            return transaction("QueryProcess", Process.class, key);
        } catch (Exception e) {
            throw new ContractException("QueryProcess error", e);
        }
    }

    @Override
    public List<Process> prevProcess(String key) {
        return null;
    }

    @Override
    public List<Process> digProcess(String key, int depth) {
        return null;
    }

    /**
     * create a transaction and submit it to the chaincode
     * @param functionName the function's name in the chaincode
     * @param responseType the expected response type
     * @param args chaincode function args, Strings
     * @param <T> generic
     * @return response
     * @throws InterruptedException fabric exception
     * @throws TimeoutException fabric exception
     * @throws ContractException fabric exception
     */
    private <T> T transaction(String functionName, Class<T> responseType, String... args) throws InterruptedException, TimeoutException, ContractException {
        byte[] respByte = contract.createTransaction(functionName).submit(args);
        if(responseType.equals(String.class)){
            return (T) new String(respByte);
        }
        return JSON.parseObject(respByte, responseType);
    }

    private String listToString(List<String> list){
        if(list == null || list.size() == 0){
            return "[]";
        }
        return JSON.toJSONString(list);
    }
}
