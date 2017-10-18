package com.whn.waf.common.base.support;

import com.whn.waf.common.base.constant.ErrorCode;
import com.whn.waf.common.exception.WafBizException;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/5/26.
 */
public class ApplicationResolveInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationResolveInterceptor.class);

//    @Resource
//    private ApplicationService applicationService = Context.getBean(ApplicationService.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }
        if (((HandlerMethod) handler).getBeanType().getAnnotation(AppApi.class) != null ||
                ((HandlerMethod) handler).getMethodAnnotation(AppApi.class) != null) {
            Object pathVariables = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (pathVariables != null && pathVariables instanceof Map) {
                Map map = (Map) pathVariables;
                Object value = map.get("application");
                if (value == null) {
                    throw WafBizException.of(ErrorCode.APPLICATION_NAME_IS_REQUIRED);
                }
                String application = (String) value;
                logger.debug("AppNameProvider set application name = {}", application);
                AppNameProvider.set(application);
                MDC.put("application", application);
            }
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        AppNameProvider.remove();
    }

}
