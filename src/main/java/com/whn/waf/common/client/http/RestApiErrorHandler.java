package com.whn.waf.common.client.http;

import com.fasterxml.jackson.core.JsonParseException;
import com.whn.waf.common.exception.WafException;
import com.whn.waf.common.exception.WafResourceAccessException;
import com.whn.waf.common.exception.support.ErrorMessage;
import com.whn.waf.common.exception.support.ResponseErrorMessage;
import com.whn.waf.common.support.WafJsonMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.util.Date;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/14.
 */
class RestApiErrorHandler extends DefaultResponseErrorHandler {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        WafClientHttpResponse wafResponse = (WafClientHttpResponse) response;
        ResponseErrorMessage errorMessage = null;
        HttpHeaders headers = null;
        HttpStatus statusCode;

        headers = wafResponse.getHeaders();
        statusCode = wafResponse.getStatusCode();
        String responseText = null;
        try {
            responseText = IOUtils.toString(wafResponse.getBody(), "UTF-8");
            errorMessage = WafJsonMapper.parse(responseText, ResponseErrorMessage.class);
        }catch(JsonParseException ex){
            if (statusCode.value()==404) {
                String detail = "REST api \"" + wafResponse.getHttpMethod() + " " + wafResponse.getUri() + " 无法访问。";
                throw new WafException("WAF/API_NOT_FOUND", "waf.er.resourceAccess.apiNotFound.exception", detail, HttpStatus.NOT_FOUND);
            }
            String detail = "exception:"+ex.getClass()+",将 REST api \"" + wafResponse.getHttpMethod() + " " + wafResponse.getUri() + "\" 的响应内容 \"" + responseText + "\" 解析为Json发生异常。";
            log.error(detail, ex);
            throw new WafException("WAF/INTERNAL_SERVER_ERROR", "waf.er.resourceAccess.parse.exception", detail, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }catch (IOException ex) {
            String detail = "exception:"+ex.getClass()+",将 REST api \"" + wafResponse.getHttpMethod() + " " + wafResponse.getUri() + "\" 的响应内容 \"" + responseText + "\" 转换为 " + ErrorMessage.class.toString() + " 发生异常。";
            log.error(detail, ex);
            throw new WafException("WAF/INTERNAL_SERVER_ERROR", "waf.er.resourceAccess.parse.exception", detail, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }catch (NullPointerException ex){
            errorMessage = new ResponseErrorMessage();
            errorMessage.setHostId("");
            errorMessage.setRequestId("");
            errorMessage.setServerTime(new Date());
            errorMessage.setThrowable(ex);
            errorMessage.setCode("WAF/INTERNAL_SERVER_ERROR");
            errorMessage.setMessage("response body is empty!");
            errorMessage.setDetail("");
            errorMessage.setCause(errorMessage);
            throw new WafResourceAccessException(wafResponse.getHttpMethod(), wafResponse.getUri(), new ResponseEntity<>(errorMessage, headers, statusCode));
        }
        log.warn("Remote rest api response error. Response content: ", responseText);
        throw new WafResourceAccessException(wafResponse.getHttpMethod(), wafResponse.getUri(), new ResponseEntity<>(errorMessage, headers, statusCode));
    }
}
