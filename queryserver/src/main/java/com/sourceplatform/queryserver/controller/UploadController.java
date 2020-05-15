package com.sourceplatform.queryserver.controller;


import com.sourceplatform.queryserver.dto.ResponseDTO;
import com.sourceplatform.queryserver.exception.ParseException;
import com.sourceplatform.queryserver.service.UploadServiceInterface;
import com.sourceplatform.queryserver.utils.ResponseUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class UploadController {

    private static final long MAX_SIZE = 8 * 1024 * 10;

    private final UploadServiceInterface uploadService;

    public UploadController(UploadServiceInterface uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/start")
    public ResponseDTO batchStart(@RequestParam("file") MultipartFile file){
        if (file.getSize()>MAX_SIZE){
            return ResponseUtil.errRes("FileSizeError");
        }
        try {
            List<String> fail = uploadService.batchStart(file.getBytes());
            return ResponseUtil.sucRes(fail);
        } catch (ParseException | IOException e) {
            return ResponseUtil.errRes("Parse/IOError");
        }
    }

    @PostMapping("/link")
    public ResponseDTO batchLink(@RequestParam("file") MultipartFile file){
        if (file.getSize()>MAX_SIZE){
            return ResponseUtil.errRes("FileSizeError");
        }
        try {
            List<String> fail = uploadService.batchLink(file.getBytes());
            return ResponseUtil.sucRes(fail);
        } catch (ParseException | IOException e) {
            return ResponseUtil.errRes("Parse/IOError");
        }
    }

    @PostMapping("/complete")
    public ResponseDTO batchComplete(@RequestParam("file") MultipartFile file){
        if (file.getSize()>MAX_SIZE){
            return ResponseUtil.errRes("FileSizeError");
        }
        try {
            List<String> fail = uploadService.batchComplete(file.getBytes());
            return ResponseUtil.sucRes(fail);
        } catch (ParseException | IOException e) {
            return ResponseUtil.errRes("Parse/IOError");
        }
    }

}
