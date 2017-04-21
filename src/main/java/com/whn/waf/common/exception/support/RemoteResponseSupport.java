package com.whn.waf.common.exception.support;

import org.springframework.http.ResponseEntity;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/14.
 */
public interface RemoteResponseSupport {
    public ResponseEntity<ResponseErrorMessage> getRemoteResponseEntity();
}
