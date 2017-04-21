package com.whn.waf.common.exception.resolver;

import com.whn.waf.common.context.WafApplicationContext;
import com.whn.waf.common.exception.handler.AbstractRestErrorHandler;
import com.whn.waf.common.exception.support.ResponseErrorMessage;
import com.whn.waf.common.support.WafJsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现对异常进行处理，以 JSON 响应异常信息
 */
public class WafRestErrorResolver implements WafErrorResolver, Ordered {

    protected static final Map<Class, AbstractRestErrorHandler> handlerMap = new HashMap<>();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void addHandler(Class throwableClass, AbstractRestErrorHandler handler) {
        Assert.notNull(throwableClass);
        Assert.notNull(handler);

        handlerMap.put(throwableClass, handler);
    }

    public AbstractRestErrorHandler getHandler(Class throwableClass) {
        Assert.notNull(throwableClass);

        for (Class clazz = throwableClass; clazz != Throwable.class; clazz = clazz.getSuperclass()) {
            AbstractRestErrorHandler handler = handlerMap.get(clazz);
            if (handler != null)
                return handler;
        }
        throw new RuntimeException("无法找到异常类型为 " + throwableClass + " 的异常处理器。");
    }

    @Override
    public boolean process(Throwable throwable, HttpServletRequest request, HttpServletResponse response) {
        if(throwable!=null){
            logger.error(throwable.getMessage(), throwable);
        }
        return this.resolver(throwable, request, response);
    }

    public boolean resolver(Throwable throwable, HttpServletRequest request, HttpServletResponse response){
        Assert.notNull(throwable);
        Assert.notNull(request);
        Assert.notNull(response);

        ResponseEntity<ResponseErrorMessage> responseEntity = process(throwable, request);
        response.setStatus(responseEntity.getStatusCode().value());

        HttpHeaders headers = responseEntity.getHeaders();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.toSingleValueMap().entrySet()) {
                response.setHeader(entry.getKey(), entry.getValue());
            }
        }
        try {
            Response r = new Response(responseEntity.getBody());
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print(WafJsonMapper.toJson(r));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    protected ResponseEntity<ResponseErrorMessage> process(Throwable throwable, HttpServletRequest request) {
        AbstractRestErrorHandler handler = getHandler(throwable.getClass());
        Assert.notNull(handler);
        return handler.process(throwable, request);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private static class Response {
        public Response(ResponseErrorMessage errorMessage) {
            hostId = errorMessage.getHostId();
            requestId = errorMessage.getRequestId();
            serverTime = errorMessage.getServerTime();
            code = errorMessage.getCode();
            message = errorMessage.getMessage();
            if (WafApplicationContext.isDebugMode()) {
                detail = errorMessage.getDetail();
                cause = errorMessage.getCause();
            }
        }

        public String hostId;
        public String requestId;
        public Date serverTime;
        public String code;
        public String message;
        public String detail;
        public ResponseErrorMessage cause;
    }
}
