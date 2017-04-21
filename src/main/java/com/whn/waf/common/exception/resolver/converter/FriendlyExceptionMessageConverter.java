package com.whn.waf.common.exception.resolver.converter;

import com.whn.waf.common.exception.support.ResponseErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/14.
 */
public abstract class FriendlyExceptionMessageConverter {
    public abstract boolean convert(ResponseEntity<ResponseErrorMessage> responseEntity);

    protected void updateErrorMessage(ResponseEntity<ResponseErrorMessage> responseEntity, String message) {
        ResponseErrorMessage errorMessage = responseEntity.getBody();

        String detail = "Message:" + errorMessage.getMessage();
        String srcDetail = errorMessage.getDetail();
        if (StringUtils.hasText(srcDetail)) {
            detail += "\r\n" + srcDetail;
        }
        errorMessage.setDetail(detail);
        errorMessage.setMessage(message);
    }
}
