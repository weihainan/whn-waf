package com.whn.waf.common.filter;

import com.whn.waf.common.context.WafApplicationContext;
import com.whn.waf.common.exception.handler.MethodArgumentNotValidExceptionHandler;
import com.whn.waf.common.exception.resolver.WafErrorResolver;
import com.whn.waf.common.exception.resolver.WafRestErrorResolver;
import com.whn.waf.common.exception.resolver.FriendlyWafRestErrorResolver;
import com.whn.waf.common.exception.support.RestErrorMappings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/14.
 */
public class ExceptionFilter extends OncePerRequestFilter {

    private final static String WAF_EXCEPTION_FRIENDLY_DISABLED = "waf.exception.friendly.disabled";


    Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<WafErrorResolver> wafErrorResolvers = new ArrayList<>();

    public ExceptionFilter() {
        wafErrorResolvers.add(wafErrorResolver());
    }

    /**
     * 具体执行异常处理
     */
    public WafErrorResolver wafErrorResolver() {
        WafRestErrorResolver resolver;
        if (Boolean.parseBoolean(WafApplicationContext.getProperty(WAF_EXCEPTION_FRIENDLY_DISABLED,"true"))) {
            resolver = new WafRestErrorResolver();
        }else {
            resolver = new FriendlyWafRestErrorResolver();
        }

        for (RestErrorMappings mapping : RestErrorMappings.values()) {
            resolver.addHandler(mapping.getThrowableClass(), mapping.getHandler());
        }

        //配置使用Valid的错误信息能够反映在message字段上
        resolver.addHandler(MethodArgumentNotValidException.class,
                new MethodArgumentNotValidExceptionHandler());

        return resolver;
    }

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("Exception filter start");
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause() instanceof Exception){
                ex = (Exception) ex.getCause();
            }
            for (WafErrorResolver wafErrorResolver : wafErrorResolvers) {
                try {
                    if (wafErrorResolver.process(ex, request, response))
                        break;
                } catch (Exception e) {
                    ex = e;
                }
            }
        }
        logger.debug("Exception filter end");
    }
}
