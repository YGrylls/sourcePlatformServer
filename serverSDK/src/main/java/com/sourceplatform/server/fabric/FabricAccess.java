package com.sourceplatform.server.fabric;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sourceplatform.server.dataaccess.DataAccessInterface;
import com.sourceplatform.server.dataaccess.Process;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Component
public class FabricAccess implements DataAccessInterface {
    @Resource(name = "fabricContract")
    private Contract contract;

    @Override
    public String startProcess(Process process) throws ContractException {
        if(StringUtils.isEmpty(process.getOptionName()) ||
        StringUtils.isEmpty(process.getStartPosition()) ||
        StringUtils.isEmpty(process.getProcessLocalId()) ||
        process.getStartTime() == null) {
            throw new ContractException("Required field null");
        }
        try {
            return transaction("StartProcess",
                    String.class,
                    process.getProcessLocalId(),
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
    public void completeProcess(@NotNull String key, @NotNull Long completeTime, @NotNull String completePosition) throws ContractException {
        try {
            transaction("CompleteProcess", key, completeTime.toString(), completePosition);
        } catch (Exception e){
            throw new ContractException("CompleteProcess error", e);
        }
    }


    @Override
    public void linkProcess(@NotNull String key, @NotNull List<String> preKeys) throws ContractException {
        try {
            transaction("LinkProcess", key, listToString(preKeys));
        } catch (Exception e){
            throw new ContractException("LinkProcess error", e);
        }
    }

    @Override
    public void addLinkedProcess(@NotNull String key, @NotNull List<String> preKeys) throws ContractException {
        if(preKeys.size() == 0){
            return;
        }
        try {
            transaction("AddLinkedProcess", key,  listToString(preKeys));
        } catch (Exception e){
            throw new ContractException("AddLinkedProcess error", e);
        }

    }

    @Override
    public Process queryProcess(@NotNull String key) throws ContractException {
        try {
            return transaction("QueryProcess", Process.class, key);
        } catch (Exception e) {
            throw new ContractException("QueryProcess error", e);
        }
    }

    @Override
    public List<Process> prevProcess(@NotNull String key) throws ContractException {
        try {
            return transactionReturnList("PrevProcess", key);
        } catch (Exception e) {
            throw new ContractException("PrevProcess error", e);
        }
    }

    @Override
    public List<Process> digProcess(@NotNull String key, @NotNull Integer depth) throws ContractException {
        try {
            return transactionReturnList("DigProcess", key, depth.toString());
        } catch (Exception e) {
            throw new ContractException("DigProcess error", e);
        }
    }

    @Override
    public void updateDisplayName(@NotNull String displayName) throws ContractException {
        try {
            transaction("UpdateDisplayName", displayName);
        } catch (Exception e){
            throw new ContractException("UpdateDisplayName error", e);
        }
    }

    @Override
    public String getDisplayName(String orgMspId) throws ContractException {
        try {
            return transaction("GetDisplayName", String.class, orgMspId);
        } catch (Exception e){
            throw new ContractException("GetDisplayName error", e);
        }
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

    /**
     * create a transaction and submit it to the chaincode which has no response entity
     * @param functionName the function's name in the chaincode
     * @param args chaincode function args, Strings
     * @throws InterruptedException fabric exception
     * @throws TimeoutException fabric exception
     * @throws ContractException fabric exception
     */
    private void transaction(String functionName, String... args) throws InterruptedException, TimeoutException, ContractException {
        contract.createTransaction(functionName).submit(args);
    }


    /**
     * create a transaction and submit it to the chaincode
     * @param functionName the function's name in the chaincode
     * @param args chaincode function args, Strings
     * @return response
     * @throws InterruptedException fabric exception
     * @throws TimeoutException fabric exception
     * @throws ContractException fabric exception
     */
    private List<Process> transactionReturnList(String functionName, String... args) throws InterruptedException, TimeoutException, ContractException {
        byte[] respByte = contract.createTransaction(functionName).submit(args);
        return JSON.parseObject(new String(respByte), new TypeReference<List<Process>>(){});
    }

    private String listToString(List<String> list){
        if(list == null || list.size() == 0){
            return "[]";
        }
        return JSON.toJSONString(list);
    }
}
