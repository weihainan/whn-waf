package com.whn.waf.common.context;

import com.whn.waf.common.base.constant.ErrorCode;
import com.whn.waf.common.exception.WafBizException;
import com.whn.waf.common.exception.WafException;
import org.springframework.http.HttpStatus;

/**
 * 模块，辅助输出特定异常
 * Created by weihainan on 2016/9/27.
 */
public class Module {

    private String code;

    public Module(String code) {
        this.code = code;
    }


    public WafException notFound() {
        String errorCode = ErrorCode.PREFIX + this.code + "_NOT_FOUND";
        return WafBizException.of(errorCode, "module.not.found", HttpStatus.NOT_FOUND);
    }

    public WafException notFound(String message) {
        String errorCode = ErrorCode.PREFIX + this.code + "_NOT_FOUND";
        return WafBizException.of(errorCode, message, HttpStatus.NOT_FOUND);
    }

    public WafException existed() {
        String errorCode = ErrorCode.PREFIX + this.code + "_EXISTED";
        return WafBizException.of(errorCode, "module.existed", HttpStatus.CONFLICT);
    }

    /**
     * 名称已存在
     *
     * @param value 名称
     * @return 相应的异常
     */
    public WafException nameExisted(String value) {
        String errorCode = ErrorCode.PREFIX + this.code + "_NAME_EXISTED";
        return WafBizException.of(errorCode, "module.name.existed", HttpStatus.CONFLICT, value);
    }

    /**
     * 被关联
     *
     * @param module2 被关联的模块
     * @return 相应的异常
     */
    public WafException associatedWith(Module module2) {
        String errorCode = ErrorCode.PREFIX + this.code + "_ASSOCIATED_WITH_" + module2.code;
        return WafBizException.of(errorCode, "module.associated.with.module2",
                HttpStatus.NOT_ACCEPTABLE, code, module2.code);
    }

    public WafException nullId() {
        return WafBizException.of("module.null.id", ErrorCode.INVALID_ARGUMENT);
    }
}
