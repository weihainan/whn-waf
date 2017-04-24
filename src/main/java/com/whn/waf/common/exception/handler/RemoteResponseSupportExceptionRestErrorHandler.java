package com.whn.waf.common.exception.handler;

import com.whn.waf.common.context.WafProperties;
import com.whn.waf.common.exception.support.RemoteResponseSupport;
import com.whn.waf.common.exception.support.ResponseErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/14.
 */
public class RemoteResponseSupportExceptionRestErrorHandler extends AbstractRestErrorHandler {

    //是否转化远程处理异常http status
    //默认为true进行转化(转为500)
    //false则是把原始http status透传给用户
    private static final String WAF_EXCEPTION_REMOTE_HTTPSTATUS_PROXY = "waf.exception.remote.httpstatus.proxy";


    private final HttpStatus httpStatus;
    private final boolean httpStatusProxy;

    public RemoteResponseSupportExceptionRestErrorHandler(HttpStatus httpStatus) {
        this.httpStatus      = httpStatus;
        this.httpStatusProxy = Boolean.valueOf(WafProperties.getProperty(WAF_EXCEPTION_REMOTE_HTTPSTATUS_PROXY, "true"));
    }

    @Override
    public ResponseEntity<ResponseErrorMessage> process(Throwable throwable, HttpServletRequest request) {

        RemoteResponseSupport rrs = (RemoteResponseSupport) throwable;
        ResponseEntity<ResponseErrorMessage> remoteResponseEntity = rrs.getRemoteResponseEntity();
        ResponseErrorMessage errorMessage;
        HttpStatus httpStatus = getHttpStatus(throwable, request);
        if (remoteResponseEntity != null) {
            ResponseErrorMessage remoteBody = remoteResponseEntity.getBody();
            errorMessage = remoteBody.clone();
            errorMessage.setDetail(appendStackTrace(remoteBody.getDetail(), throwable));
            errorMessage.setCause(remoteBody);
            updateRemoteErrorMessage(errorMessage, request);
            if ( !httpStatusProxy ){
                httpStatus = remoteResponseEntity.getStatusCode();
            }
        } else {
            errorMessage = super.getBody(throwable, request);
        }
        HttpHeaders httpHandlers = getHttpHandlers(throwable, request);
        return new ResponseEntity<>(errorMessage, httpHandlers, httpStatus);
    }

    @Override
    protected String getCode(Throwable throwable, HttpServletRequest request) {
        return "WAF/" + httpStatus.getReasonPhrase().toUpperCase();
    }

    @Override
    protected HttpStatus getHttpStatus(Throwable throwable, HttpServletRequest request) {
        return httpStatus;
    }
}
