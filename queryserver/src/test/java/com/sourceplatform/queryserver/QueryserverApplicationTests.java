package com.sourceplatform.queryserver;

import com.sourceplatform.server.dataaccess.Process;
import com.sourceplatform.server.fabric.FabricAccess;
import org.hyperledger.fabric.gateway.ContractException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootTest
class QueryserverApplicationTests {

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
        process.setOptionName("葡萄采摘自运输");
        process.setStartPosition("嘉定");
        process.setOwnerOrg("Org1MSP");
        process.setSpec("{\"key\":\"value\"}");
        process.setPreKey(new ArrayList<>());
        try {
            String res = fabricAccess.startProcess(process);
            Assert.isTrue("Org1MSP:0001".equals(res),"actual: "+res);
        } catch (ContractException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    void testCompleteProcess() throws ContractException {
        fabricAccess.completeProcess("Org1MSP:0001",new Date().getTime(),"杨浦");
        Process process = fabricAccess.queryProcess("Org1MSP:0001");
        System.out.println(process);
        Assert.isTrue("杨浦".equals(process.getCompletePosition()),"response not as expected");
        Assert.isTrue(process.getState() == 1,"response not as expected");
    }

    @Test
    void testStartProcess2() throws ContractException {
        Process process = new Process();
        process.setProcessLocalId("0002");
        process.setStartTime(new Date().getTime());
        process.setOptionName("加工直供");
        process.setStartPosition("杨浦");
        process.setOwnerOrg("Org1MSP");
        process.setSpec("{\"key\":\"value\"}");
        List<String> pre = new ArrayList<>();
        pre.add("Org1MSP:0001");
        process.setPreKey(pre);
        try {
            String res = fabricAccess.startProcess(process);
            Assert.isTrue("Org1MSP:0002".equals(res),"actual: "+res);
        } catch (ContractException e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Test
    void testQueryPre() throws ContractException {
        Process process = new Process();
        process.setProcessLocalId("0003");
        process.setStartTime(new Date().getTime());
        process.setOptionName("销售");
        process.setStartPosition("杨浦");
        process.setOwnerOrg("Org1MSP");
        process.setSpec("{\"key\":\"value\"}");
        List<String> pre = new ArrayList<>();
        pre.add("Org1MSP:0001");
        process.setPreKey(pre);

        String res = fabricAccess.startProcess(process);
        Assert.isTrue("Org1MSP:0003".equals(res),"actual: "+res);

        List<Process> resList1 = fabricAccess.prevProcess(res);
        System.out.println("1 -- "+ resList1);
        Assert.isTrue(resList1.size() == 1, "Prev check 1 failed");

        fabricAccess.addLinkedProcess(res, Collections.singletonList("Org1MSP:0002"));
        List<Process> resList2 = fabricAccess.prevProcess(res);
        System.out.println("2 -- "+ resList2);
        Assert.isTrue(resList2.size() == 2, "Prev check 2 failed");

        fabricAccess.linkProcess(res, Collections.singletonList("Org1MSP:0002"));
        List<Process> resList3 = fabricAccess.prevProcess(res);
        System.out.println("3 -- "+ resList3);
        Assert.isTrue(resList3.size() == 1, "Prev check 3 failed");

        List<Process> resList4 = fabricAccess.digProcess(res,10);
        System.out.println("4 -- "+ resList4);
        Assert.isTrue(resList4.size() == 2, "Prev check 4 failed");
    }

    @Test
    void testStartProcess4() throws ContractException {
        Process process = new Process();
        process.setProcessLocalId("0007");
        process.setStartTime(new Date().getTime());
        process.setOptionName("ForTestOnly");
        process.setStartPosition("温州");
        process.setOwnerOrg("Org1MSP");
        process.setSpec("{\"key\":\"value\"}");
        List<String> pre = new ArrayList<>();
        pre.add("Org1MSP:0005");
        pre.add("Org1MSP:0006");
        process.setPreKey(pre);
        try {
            String res = fabricAccess.startProcess(process);
            Assert.isTrue("Org1MSP:0007".equals(res),"actual: "+res);
        } catch (ContractException e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Test
    void testQueryProcess() throws ContractException {
        try {
            Process process = fabricAccess.queryProcess("Org1MSP:0002");
            Assert.notNull(process,"response null");
            System.out.println(process);
        } catch (ContractException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    void testDisplayName() throws ContractException {
        try {
            fabricAccess.updateDisplayName("测试用组织");
            String res = fabricAccess.getDisplayName("Org1MSP");
            Assert.isTrue("测试用组织".equals(res), "response not equal");
        } catch (ContractException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
