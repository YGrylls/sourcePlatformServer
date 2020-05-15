package com.sourceplatform.queryserver.exception;

public class ParseException extends Exception {
    public ParseException(String msg){
        super(msg);
    }
    public ParseException(Throwable cause){
        super(cause);
    }
}
