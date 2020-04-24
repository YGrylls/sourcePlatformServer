package com.sourceplatform.server;

import com.google.common.collect.Lists;
import com.sourceplatform.server.dataaccess.Process;
import com.sourceplatform.server.fabric.FabricAccess;
import org.hyperledger.fabric.gateway.ContractException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;

@SpringBootTest
class ServerApplicationTests {

    @Autowired
    private FabricAccess fabricAccess;

    @Test
    void contextLoads() {
    }

    @Test
    void testStartProcess() throws ContractException {
        Process process = new Process();
        process.setProcessLocalId("0001");
        process.setStartTime(new Date().getTime());
        process.setOptionName("测试启动");
        process.setStartPosition("嘉定");
        process.setOwnerOrg("Org1MSP");
        process.setSpec("{\"key\":\"value\"}");
        process.setPreKey(new ArrayList<>());
        try {
            String res = fabricAccess.startProcess("0001", process);
            Assert.isTrue("Org1MSP:0001".equals(res),"actual: "+res);
        } catch (ContractException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    void testQueryProcess() throws ContractException {
        try {
            Process process = fabricAccess.queryProcess("Org1MSP:0001");
            Assert.notNull(process,"response null");
            System.out.println(process);
        } catch (ContractException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
