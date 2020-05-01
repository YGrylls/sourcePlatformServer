package com.sourceplatform.queryserver.aop;

import com.sourceplatform.queryserver.service.SourceService;
import com.sourceplatform.queryserver.utils.ResponseUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class ExceptionHandler {
    private Log logger = LogFactory.getLog(ExceptionHandler.class);

    @Pointcut("execution(* com.sourceplatform.queryserver.controller..*.*(..))")
    public void exceptionCatcher() {

    }

    @Around("exceptionCatcher()")
    public Object handleException(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (Throwable t) {
            logger.error("Uncaught exception",t);
            return ResponseUtil.errRes("InternalServerError", t.getMessage());
        }
    }

}
