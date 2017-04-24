package com.whn.waf.common.filter;

import com.whn.waf.common.context.WafProperties;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

/**
 * 处理跨域问题
 *
 * @author weihainan.
 * @since 0.1 created on 2017/4/19.
 */
public class CORSFilter {


    public static final String WAF_CORS_ALLOW_ORIGIN = "waf.cors.allow.origin";
    public static final String WAF_CORS_ALLOW_METHODS = "waf.cors.allow.methods";
    public static final String WAF_CORS_ALLOW_HEADERS = "waf.cors.allow.headers";
    public static final String WAF_CORS_MAX_AGE = "waf.cors.max.age";

    static {
        Properties defaultProperties = WafProperties.getDefaultProperties();
        defaultProperties.setProperty(WAF_CORS_ALLOW_ORIGIN, "*");
        defaultProperties.setProperty(WAF_CORS_ALLOW_METHODS, "GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE, PATCH");
        defaultProperties.setProperty(WAF_CORS_ALLOW_HEADERS, "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization, Cache-control");
        defaultProperties.setProperty(WAF_CORS_MAX_AGE, "3600");
    }


    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        // 设定CORS的初始化参数
        // cors.allowed.origins *:Any origin is allowed to access the resource
        response.addHeader("Access-Control-Allow-Origin", WafProperties.getProperty(WAF_CORS_ALLOW_ORIGIN));
        // cors.allowed.methods Access-Control-Allow-Methods: A comma separated
        // list of HTTP methods that can be used to access the resource
        response.addHeader("Access-Control-Allow-Methods", WafProperties.getProperty(WAF_CORS_ALLOW_METHODS));
        // cors.allowed.headers Access-Control-Allow-Headers: A comma separated
        // list of request headers that can be used when making an actual
        // request
        response.addHeader("Access-Control-Allow-Headers", WafProperties.getProperty(WAF_CORS_ALLOW_HEADERS));
        // cors.preflight.maxage Access-Control-Max-Age The amount of seconds,
        // browser is allowed to cache the result of the pre-flight request
        response.addHeader("Access-Control-Max-Age", WafProperties.getProperty(WAF_CORS_MAX_AGE));
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

    public static String getBodyString(BufferedReader br) {
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return str;
    }
}
