package com.sourceplatform.server.dataaccess;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Process {
    public String processLocalId;
    public String ownerOrg;
    public String optionName;
    public long startTime;
    public long completeTime;
    public String startPosition;
    public String completePosition;
    public List<String> preKey;

    //0 for INPROCESS
    //1 for COMPLETE
    public int state;

    @JsonProperty("class")
    public String clazz;
    public String key;
}
