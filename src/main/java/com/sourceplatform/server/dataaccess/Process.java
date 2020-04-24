package com.sourceplatform.server.dataaccess;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Process {
    public String processLocalId;
    public String ownerOrg;
    public String optionName;
    public Long startTime;
    public Long completeTime;
    public String startPosition;
    public String completePosition;
    public List<String> preKey;
    public String spec;

    //0 for INPROCESS
    //1 for COMPLETE
    public int state;

    @JsonProperty("class")
    public String clazz;
    public String key;

    public Process() {
    }

    public String getProcessLocalId() {
        return processLocalId;
    }

    public void setProcessLocalId(String processLocalId) {
        this.processLocalId = processLocalId;
    }

    public String getOwnerOrg() {
        return ownerOrg;
    }

    public void setOwnerOrg(String ownerOrg) {
        this.ownerOrg = ownerOrg;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Long completeTime) {
        this.completeTime = completeTime;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public String getCompletePosition() {
        return completePosition;
    }

    public void setCompletePosition(String completePosition) {
        this.completePosition = completePosition;
    }

    public List<String> getPreKey() {
        return preKey;
    }

    public void setPreKey(List<String> preKey) {
        this.preKey = preKey;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Process{" +
                "processLocalId='" + processLocalId + '\'' +
                ", ownerOrg='" + ownerOrg + '\'' +
                ", optionName='" + optionName + '\'' +
                ", startTime=" + startTime +
                ", completeTime=" + completeTime +
                ", startPosition='" + startPosition + '\'' +
                ", completePosition='" + completePosition + '\'' +
                ", preKey=" + preKey +
                ", spec='" + spec + '\'' +
                ", state=" + state +
                ", clazz='" + clazz + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
