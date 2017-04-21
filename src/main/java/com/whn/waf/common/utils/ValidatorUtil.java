package com.whn.waf.common.utils;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/10.
 */

import com.google.common.annotations.VisibleForTesting;
import com.whn.waf.common.base.constant.ErrorCode;
import com.whn.waf.common.exception.WafBizException;
import org.apache.commons.lang3.StringUtils;
import org.mockito.Mockito;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidatorUtil {

    private static Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private ValidatorUtil() {
    }

    public static String getErrorMessageStr(BindingResult result) {
        if (result == null || !result.hasErrors()) {
            return "";
        }
        StringBuilder errorMessage = new StringBuilder();
        boolean start = true;
        for (FieldError fieldError : result.getFieldErrors()) {
            if (start) {
                start = false;
            } else {
                errorMessage.append(',');
            }
            errorMessage.append(fieldError.getDefaultMessage());
        }
        return errorMessage.toString();
    }

    public static <T> String validate(T t) {
        StringBuilder errorMessage = new StringBuilder();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        boolean start = true;
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            if (start) {
                start = false;
            } else {
                errorMessage.append(',');
            }
            errorMessage.append(constraintViolation.getMessage());
        }
        return errorMessage.toString();
    }

    public static <T> String validateAndThrow(T t) {
        String validateError = validate(t);
        if (StringUtils.isNotBlank(validateError)) {
            throw WafBizException.of(validateError, ErrorCode.INVALID_ARGUMENT);
        }
        return validateError;
    }

    @VisibleForTesting
    public static void mock(Validator mockValidator) {
        validator = mockValidator;
    }

    @VisibleForTesting
    public static void mock() {
        validator = Mockito.mock(Validator.class);
    }
}