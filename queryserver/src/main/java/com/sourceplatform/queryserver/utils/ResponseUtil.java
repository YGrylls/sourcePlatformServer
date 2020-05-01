package com.sourceplatform.queryserver.utils;

import com.sourceplatform.queryserver.dto.ResponseDTO;

public class ResponseUtil {
    public static ResponseDTO sucRes(Object data) {
        ResponseDTO dto = new ResponseDTO();
        dto.setData(data);
        return dto;
    }

    public static ResponseDTO errRes(String code, String msg) {
        ResponseDTO dto = new ResponseDTO();
        dto.setErrCode(code);
        dto.setErrMsg(msg);
        return dto;
    }

    public static ResponseDTO errRes(String code) {
        ResponseDTO dto = new ResponseDTO();
        dto.setErrCode(code);
        dto.setErrMsg(code);
        return dto;
    }
}
