package com.sourceplatform.server.fabric;

import com.sourceplatform.server.dataaccess.DataAccessInterface;
import com.sourceplatform.server.dataaccess.Process;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FabricAccess implements DataAccessInterface {
    @Override
    public String startProcess(String localId, Process process) {
        return null;
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
    public Process queryProcess(String key) {
        return null;
    }

    @Override
    public List<Process> prevProcess(String key) {
        return null;
    }

    @Override
    public List<Process> digProcess(String key, int depth) {
        return null;
    }
}
