package com.whn.waf.common.client.http;

import com.whn.waf.common.context.WafApplicationContext;
import org.apache.http.client.HttpClient;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/3/14.
 */
public class WafHttpClientBuilder {
    public HttpClient build() {
        return buildHttpClient(buildConnectionManager(), null);
    }

    public HttpClient build(int retryCount) {
        return this.build(retryCount, new ArrayList<Class<? extends IOException>>());
    }

    public HttpClient build(int retryCount, Collection<Class<? extends IOException>> clazzes) {
        if (clazzes == null) {
            clazzes = new ArrayList<Class<? extends IOException>>();
        }

        return buildHttpClient(buildConnectionManager(), new WafHttpRequestRetryHandler(retryCount, true, clazzes));
    }

    //创建连接池
    protected HttpClientConnectionManager buildConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        //设置默认的一些配置
        poolingConnectionManager.setDefaultSocketConfig( getConnectionManagerSocketConfig() );
        poolingConnectionManager.setDefaultConnectionConfig( getConnectionManagerConnectionConfig() );
        //设置同时最大连接数
        poolingConnectionManager.setMaxTotal(WafApplicationContext.getPropertyForInteger(WafHttpClient.WAF_CLIENT_MAX_TOTAL));
        poolingConnectionManager.setDefaultMaxPerRoute(WafApplicationContext.getPropertyForInteger(WafHttpClient.WAF_CLIENT_MAX_PER_ROUTE));
        return poolingConnectionManager;
    }

    /**
     * 创建HttpClient
     * @param connectionManager       指定使用的连接池
     * @param httpRequestRetryHandler 重试机制
     * @return HttpClient
     */
    protected HttpClient buildHttpClient(HttpClientConnectionManager connectionManager,
                                         WafHttpRequestRetryHandler httpRequestRetryHandler) {
        org.apache.http.impl.client.HttpClientBuilder httpClientBuilder = HttpClients.custom();

        //设置keepalive策略/连接池/重试
        HttpClient httpClient =  httpClientBuilder.setKeepAliveStrategy(new WafConnectionKeepAliveStrategy())
                .setConnectionManager(connectionManager)
                .setRetryHandler(httpRequestRetryHandler)
                .build();
        //检测连接池socket状态并进行必要回收
        startMonitorThread(connectionManager, httpClient);
        return httpClient;
    }

    protected IdleConnectionMonitorThread startMonitorThread(HttpClientConnectionManager connectionManager, HttpClient httpClient) {
        IdleConnectionMonitorThread monitorThread = new IdleConnectionMonitorThread(connectionManager, httpClient);
        // Don't stop quitting.
        monitorThread.setDaemon(true);
        monitorThread.start();

        return monitorThread;
    }

    protected ConnectionConfig getConnectionManagerConnectionConfig() {
        return ConnectionConfig.custom()
                .setBufferSize(8 * 1024)
                .setFragmentSizeHint(8 * 1024)
                .build();
    }

    protected SocketConfig getConnectionManagerSocketConfig() {
        return SocketConfig.custom()
                .setSoTimeout(WafApplicationContext.getPropertyForInteger(WafHttpClient.WAF_CLIENT_SOCKET_TIMEOUT))
                .build();
    }

    public static class IdleConnectionMonitorThread extends Thread {
        private final HttpClientConnectionManager connMgr;
        private final WeakReference<HttpClient> httpClientWeakReference;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr, HttpClient httpClient) {
            this.connMgr = connMgr;
            this.httpClientWeakReference = new WeakReference<HttpClient>(httpClient);
        }

        @Override
        public void run() {
            while ( httpClientWeakReference.get() != null ) {
                // Close expired connections
                connMgr.closeExpiredConnections();
                // Optionally, close connections
                // that have been idle longer than 30 sec
                connMgr.closeIdleConnections(30, TimeUnit.SECONDS);

                //wait for 5 second.
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignore) {  }
            }
        }
    }//end of class IdleConnectionMonitorThread
}

