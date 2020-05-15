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
    void testDisplayName1() throws ContractException {
        try {
            fabricAccess.updateDisplayName("马陆葡萄园");
            String res = fabricAccess.getDisplayName("Org1MSP");
            Assert.isTrue("马陆葡萄园".equals(res), "response not equal");
        } catch (ContractException e) {
            e.printStackTrace();
            throw e;
        }
    }
    @Test
    void testDisplayName2() throws ContractException {
        try {
            fabricAccess.updateDisplayName("上海物流");
            String res = fabricAccess.getDisplayName("Org2MSP");
            Assert.isTrue("上海物流".equals(res), "response not equal");
        } catch (ContractException e) {
            e.printStackTrace();
            throw e;
        }
    }
    @Test
    void testDisplayName3() throws ContractException {
        try {
            fabricAccess.updateDisplayName("四平水果店");
            String res = fabricAccess.getDisplayName("Org3MSP");
            Assert.isTrue("四平水果店".equals(res), "response not equal");
        } catch (ContractException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    void testProcessOrg1() throws ContractException {
        Process process = new Process();
        process.setProcessLocalId("20200505");
        process.setStartTime(new Date().getTime());
        process.setOptionName("葡萄采摘");
        process.setStartPosition("嘉定");
        process.setOwnerOrg("Org1MSP");
        process.setSpec("{\"key\":\"value\"}");
        process.setPreKey(new ArrayList<>());
        try {
            String res = fabricAccess.startProcess(process);
            Assert.isTrue("Org1MSP:20200505".equals(res),"actual: "+res);
        } catch (ContractException e) {
            e.printStackTrace();
            throw e;
        }
        fabricAccess.completeProcess("Org1MSP:20200505",new Date().getTime(),"嘉定");
    }
    @Test
    void testProcessOrg2() throws ContractException {
        Process process = new Process();
        process.setProcessLocalId("20200505");
        process.setStartTime(new Date().getTime());
        process.setOptionName("运输");
        process.setStartPosition("嘉定");
        process.setOwnerOrg("Org2MSP");
        process.setSpec("{\"key\":\"value\"}");
        List<String> pre = new ArrayList<>();
        pre.add("Org1MSP:20200505");
        process.setPreKey(pre);
        try {
            String res = fabricAccess.startProcess(process);
            Assert.isTrue("Org2MSP:20200505".equals(res),"actual: "+res);
        } catch (ContractException e) {
            e.printStackTrace();
            throw e;
        }
        fabricAccess.completeProcess("Org2MSP:20200505",new Date().getTime(),"杨浦");
    }
    @Test
    void testProcessOrg3() throws ContractException {
        Process process = new Process();
        process.setProcessLocalId("20200505");
        process.setStartTime(new Date().getTime());
        process.setOptionName("上架销售");
        process.setStartPosition("杨浦");
        process.setOwnerOrg("Org3MSP");
        process.setSpec("{\"key\":\"value\"}");
        List<String> pre = new ArrayList<>();
        pre.add("Org2MSP:20200505");
        process.setPreKey(pre);
        try {
            String res = fabricAccess.startProcess(process);
            Assert.isTrue("Org3MSP:20200505".equals(res),"actual: "+res);
        } catch (ContractException e) {
            e.printStackTrace();
            throw e;
        }
        fabricAccess.completeProcess("Org3MSP:20200505",new Date().getTime(),"杨浦");
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



}
