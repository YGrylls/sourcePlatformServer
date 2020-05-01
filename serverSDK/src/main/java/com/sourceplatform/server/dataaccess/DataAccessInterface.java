package com.sourceplatform.server.dataaccess;

import org.hyperledger.fabric.gateway.ContractException;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface DataAccessInterface {

    /**
     * create a new process
     * @param process process
     * @return global key
     */
    String startProcess(Process process) throws ContractException;

    /**
     * complete an existing process
     * @param key global key
     * @param completeTime complete time
     * @param completePosition complete position
     */
    void completeProcess(@NotNull String key, @NotNull Long completeTime, @NotNull String completePosition) throws ContractException;

    /**
     * link a process to its previous ones, if it already has previous linked nodes, they will be replaced
     * @param key target
     * @param preKeys previous process keys
     */
    void linkProcess(@NotNull String key, @NotNull List<String> preKeys) throws ContractException;

    /**
     * link a process to its previous ones, if it already has previous linked nodes, the newer ones will be appended after
     * @param key target
     * @param preKeys previous process keys to be added
     */
    void addLinkedProcess(@NotNull String key, @NotNull List<String> preKeys) throws ContractException;

    /**
     * query an existing process
     * @param key global key
     * @return target process
     */
    Process queryProcess(@NotNull String key) throws ContractException;

    /**
     * query the previous processes of given one
     * @param key global key
     * @return prev process with depth of 1
     */
    List<Process> prevProcess(@NotNull String key) throws ContractException;


    /**
     * query the previous process chain on one branch
     * @param key global key
     * @param depth customized depth, no more than 20
     * @return prev process on one branch with given depth
     */
    List<Process> digProcess(@NotNull String key, @NotNull Integer depth) throws ContractException;


    /**
     * Update display name for your org
     * NOTE: if display name of your org is never defined, msp id will be returned when query process
     * @param displayName new display name
     */
    void updateDisplayName(@NotNull String displayName) throws ContractException;


    /**
     * Get your org's display name
     * @return display name
     */
    String getDisplayName(String orgMspId) throws ContractException;


}
