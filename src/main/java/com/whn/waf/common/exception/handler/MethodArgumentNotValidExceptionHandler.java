package com.whn.waf.common.exception.handler;

import com.whn.waf.common.base.constant.ErrorCode;
import com.whn.waf.common.exception.WafBizException;
import com.whn.waf.common.exception.support.ResponseErrorMessage;
import com.whn.waf.common.utils.ValidatorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证失败异常处理
 */
public class MethodArgumentNotValidExceptionHandler extends AbstractRestErrorHandler {

    @Override
    protected ResponseErrorMessage getBody(Throwable throwable, HttpServletRequest request) {
        ResponseErrorMessage errorMessage = new ResponseErrorMessage(throwable);
        String message = ValidatorUtil.getErrorMessageStr(
                ((MethodArgumentNotValidException) throwable).getBindingResult());
        errorMessage.setMessage(message);
        errorMessage.setDetail(appendStackTrace(null, throwable));
        errorMessage.setCode(getCode(throwable, request));
        errorMessage.setThrowable(WafBizException.of(ErrorCode.INVALID_ARGUMENT));
        updateRemoteErrorMessage(errorMessage, request);
        return errorMessage;
    }

    @Override
    protected String getCode(Throwable throwable, HttpServletRequest request) {
        return ErrorCode.INVALID_ARGUMENT.getCode();
    }

    @Override
    protected HttpStatus getHttpStatus(Throwable throwable, HttpServletRequest request) {
        return HttpStatus.BAD_REQUEST;
    }
}
