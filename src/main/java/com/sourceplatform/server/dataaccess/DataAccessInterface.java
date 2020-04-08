package com.sourceplatform.server.dataaccess;

import java.util.List;

public interface DataAccessInterface {

    /**
     *
     * @param localId local id
     * @param process process
     * @return global key
     */
    String startProcess(String localId, Process process);

    /**
     *
     * @param process target
     */
    void completeProcess(Process process);

    /**
     *
     * @param key target
     * @param preKeys previous process keys
     */
    void linkProcess(String key, List<String> preKeys);

    /**
     *
     * @param key target
     * @param preKeys previous process keys to be added
     */
    void addLinkedProcess(String key, List<String> preKeys);

    /**
     *
     * @param key global key
     * @return target process
     */
    Process queryProcess(String key);

    /**
     *
     * @param key global key
     * @return prev process with depth of 1
     */
    List<Process> prevProcess(String key);


    /**
     *
     * @param key global key
     * @param depth customized depth, no more than 20
     * @return prev process on one branch with given depth
     */
    List<Process> digProcess(String key, int depth);


}
