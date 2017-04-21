package com.whn.waf.common.exception.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对异常进行处理的接口
 */
public interface WafErrorResolver {
    public boolean process(Throwable throwable, HttpServletRequest request, HttpServletResponse response);
}