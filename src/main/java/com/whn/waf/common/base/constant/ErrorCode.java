package com.whn.waf.common.base.constant;

import com.whn.waf.common.context.WafProperties;
import org.springframework.http.HttpStatus;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/10.
 */
public enum ErrorCode implements IErrorCode {

    APPLICATION_NAME_DUPLICATE(HttpStatus.BAD_REQUEST, "APPLICATION_NAME_DUPLICATE", "已存在相同的Application名称"),
    APPLICATION_NAME_ONLY_INPUT_EN_NUM(HttpStatus.BAD_REQUEST, "APPLICATION_NAME_ONLY_INPUT_EN_NUM", "应用名称只支持小写字母、数字、下划线、横杠。并以字母开头"),
    APPLICATION_NAME_IS_REQUIRED(HttpStatus.BAD_REQUEST, "APPLICATION_NAME_IS_REQUIRED", "请求路径中没有application信息"),
    APPLICATION_NAME_IS_NOT_REGISTERED(HttpStatus.BAD_REQUEST, "APPLICATION_NAME_IS_NOT_REGISTERED", "application未接入"),

    //配置相关
    CONFIG_LOADING_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "CONFIG_LOADING_FAIL", "error.code.config.loading.fail"),
    CONFIG_MISSING(HttpStatus.INTERNAL_SERVER_ERROR, "CONFIG_MISSING", "error.code.config.missing"),
    CONFIG_MISSING_ITEM(HttpStatus.INTERNAL_SERVER_ERROR, "CONFIG_MISSING_ITEM", "error.code.config.missing.item"),

    // 请求相关
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "INVALID_ARGUMENT", "error.code.invalid.argument"),
    REQUIRE_ARGUMENT(HttpStatus.BAD_REQUEST, "REQUIRE_ARGUMENT", "error.code.require.argument"),
    INVALID_OPERATOR(HttpStatus.NOT_ACCEPTABLE, "INVALID_OPERATOR", "error.code.invalid.operator"),

    // 没有数据
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "DATA_NOT_FOUND", "error.code.data.not.found"),

    //ListParam相关
    INVALID_QUERY(HttpStatus.BAD_REQUEST, "INVALID_QUERY", "error.code.invalid.query"),
    FIELD_NOT_FOUND(HttpStatus.BAD_REQUEST, "FIELD_NOT_FOUND", "error.code.field.not.found"),

    //程序错误
    FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "FAIL", "error.code.fail");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    public static final String PREFIX = WafProperties.getErrorCodePrefix();

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getCode() {
        return PREFIX + this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
