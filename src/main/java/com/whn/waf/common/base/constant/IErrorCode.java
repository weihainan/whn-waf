package com.whn.waf.common.base.constant;

import org.springframework.http.HttpStatus;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/10.
 */
public interface IErrorCode {

    HttpStatus getHttpStatus();

    String getCode();

    String getMessage();
}

