package com.sourceplatform.queryserver.service;

import com.sourceplatform.queryserver.exception.ParseException;

import java.util.List;

public interface UploadServiceInterface {
    List<String> batchStart(byte[] JSONBytes) throws ParseException;
    List<String> batchLink(byte[] JSONBytes) throws ParseException;
    List<String> batchComplete(byte[] JSONBytes) throws ParseException;
}
