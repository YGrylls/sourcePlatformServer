package com.sourceplatform.server.dataaccess;

import org.hyperledger.fabric.gateway.ContractException;

import java.util.List;

public interface DataAccessInterface {

    /**
     * create a new process
     * @param localId local id
     * @param process process
     * @return global key
     */
    String startProcess(String localId, Process process) throws ContractException;

    /**
     * complete an existing process
     * @param process target
     */
    void completeProcess(Process process);

    /**
     * link a process to its previous ones, if it already has previous linked nodes, they will be replaced
     * @param key target
     * @param preKeys previous process keys
     */
    void linkProcess(String key, List<String> preKeys);

    /**
     * link a process to its previous ones, if it already has previous linked nodes, the newer ones will be appended after
     * @param key target
     * @param preKeys previous process keys to be added
     */
    void addLinkedProcess(String key, List<String> preKeys);

    /**
     * query an existing process
     * @param key global key
     * @return target process
     */
    Process queryProcess(String key) throws ContractException;

    /**
     * query the previous processes of given one
     * @param key global key
     * @return prev process with depth of 1
     */
    List<Process> prevProcess(String key);


    /**
     * query the previous process chain on one branch
     * @param key global key
     * @param depth customized depth, no more than 20
     * @return prev process on one branch with given depth
     */
    List<Process> digProcess(String key, int depth);


}
