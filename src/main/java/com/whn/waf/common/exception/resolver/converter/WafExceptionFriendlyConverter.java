package com.whn.waf.common.exception.resolver.converter;


import com.whn.waf.common.exception.WafException;
import com.whn.waf.common.exception.support.ResponseErrorMessage;
import org.springframework.http.ResponseEntity;

/**
 * @author vime
 * @since 0.9.6
 */
public class WafExceptionFriendlyConverter extends FriendlyExceptionMessageConverter {
    @Override
    public boolean convert(ResponseEntity<ResponseErrorMessage> responseEntity) {
        Throwable throwable = responseEntity.getBody().getThrowable();
        if(throwable instanceof WafException)
        {
            //不做任何转换
            return true;
        }
        return false;
    }
}
