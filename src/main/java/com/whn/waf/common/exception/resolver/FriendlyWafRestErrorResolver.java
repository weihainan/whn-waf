package com.whn.waf.common.exception.resolver;

import com.whn.waf.common.exception.resolver.converter.FriendlyExceptionMessageConverter;
import com.whn.waf.common.exception.resolver.converter.WafExceptionFriendlyConverter;
import com.whn.waf.common.exception.support.ResponseErrorMessage;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/14.
 */
public class FriendlyWafRestErrorResolver extends WafRestErrorResolver {

    private List<FriendlyExceptionMessageConverter> converters = new ArrayList<>();

    public FriendlyWafRestErrorResolver() {
        converters.add(new WafExceptionFriendlyConverter());
    }

    @Override
    protected ResponseEntity<ResponseErrorMessage> process(Throwable throwable, HttpServletRequest request) {
        ResponseEntity<ResponseErrorMessage> responseEntity = super.process(throwable, request);
        for (FriendlyExceptionMessageConverter converter : converters) {
            if (converter.convert(responseEntity)) {
                break;
            }
        }
        return responseEntity;
    }
}
