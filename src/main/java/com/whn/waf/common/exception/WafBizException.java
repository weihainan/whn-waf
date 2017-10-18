package com.whn.waf.common.exception;

import com.whn.waf.common.base.constant.IErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/10.
 */
public class WafBizException extends WafException {


    private String code;
    private String message;
    private HttpStatus status;
    private String[] args;

    protected WafBizException(String code, String message, HttpStatus status, Throwable cause, String... args) {
        super(code, message, status, cause);
        this.code = code;
        this.message = message;
        this.status = status;
        this.args = args;
    }

    /**
     * code 为 "WAF/INTERNAL_SERVER_ERROR";
     * HttpStatus 为 HttpStatus.INTERNAL_SERVER_ERROR (500);
     * message 为 message
     *
     * @param message 错误消息描述
     */
    public static WafBizException of(String message, String... args) {
        return of("WAF/INTERNAL_SERVER_ERROR", message, HttpStatus.INTERNAL_SERVER_ERROR, null, args);
    }

    /**
     * code 为 "WAF/INTERNAL_SERVER_ERROR";
     * HttpStatus 为 HttpStatus.INTERNAL_SERVER_ERROR (500);
     * message 为 message
     *
     * @param message 错误消息描述
     */
    public static WafBizException of(String message, Throwable cause, String... args) {
        return of("WAF/INTERNAL_SERVER_ERROR", message, HttpStatus.INTERNAL_SERVER_ERROR, cause, args);
    }

    public static WafBizException of(String message, IErrorCode errorCode, String... args) {
        return of(errorCode.getCode(), message, errorCode.getHttpStatus(), null, args);
    }

    public static WafBizException of(IErrorCode errorCode, String... args) {
        return of(errorCode.getCode(), errorCode.getMessage(), errorCode.getHttpStatus(), null, args);
    }

    public static WafBizException of(String code, String message, HttpStatus status, String... args) {
        return of(code, message, status, null, args);
    }

    public static WafBizException of(String message, IErrorCode errorCode, Throwable cause, String... args) {
        if (StringUtils.isBlank(message)) {
            message = errorCode.getMessage();
        }
        return of(errorCode.getCode(), message, errorCode.getHttpStatus(), cause, args);
    }

    public static WafBizException of(IErrorCode errorCode, Throwable cause, String... args) {
        return of(errorCode.getCode(), errorCode.getMessage(), errorCode.getHttpStatus(), cause, args);
    }

    public static WafBizException of(String code, String message, HttpStatus status, Throwable cause, String... args) {
        if (args != null) {
            message = String.format(message, args);
        }
        return new WafBizException(code, message, status, cause);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WafBizException that = (WafBizException) o;

        return Objects.equals(this.code, that.code)
                && Objects.equals(this.message, that.message)
                && Objects.equals(this.status, that.status)
                && Arrays.equals(args, that.args);

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (args != null ? Arrays.hashCode(args) : 0);
        return result;
    }
}
