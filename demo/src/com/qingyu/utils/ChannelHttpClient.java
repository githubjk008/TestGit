/**
 * 
 * Copyright (c) 2010-2016,Inc.All Rights Reserved.
 */
package com.qingyu.utils;

import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;

/**
 * 
 * @author poll
 * @version $Id: ChannelHttpClient.java, v 0.1 2016�?1�?4�?下午1:21:35 poll Exp $
 */
public class ChannelHttpClient {

    private static PoolingHttpClientConnectionManager cm;

    public static final int                           CONN_TIMEOUT = 50 * 1000; //请求超时 50s
    public static final int                           READ_TIMEOUT = 50 * 1000; //等待数据超时时间 50s
    public static final String                        charset      = "UTF-8";

    static {
        if (cm == null) {
            cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(300);
            cm.setDefaultMaxPerRoute(100);
        }
    }

    private static HttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    public static String postParameters(String url, Map<String, String> params) throws ConnectTimeoutException,
                                                                               SocketTimeoutException, Exception {
        return postForm(url, params, null, CONN_TIMEOUT, READ_TIMEOUT);
    }

    public static String postParameters(String url, Map<String, String> params, Integer connTimeout, Integer readTimeout)
                                                                                                                         throws ConnectTimeoutException,
                                                                                                                         SocketTimeoutException,
                                                                                                                         Exception {
        return postForm(url, params, null, connTimeout, readTimeout);
    }

    public static String postParameters(String url, String paramStr) throws ConnectTimeoutException,
                                                                    SocketTimeoutException, Exception {
        return post(url, paramStr, charset, null, CONN_TIMEOUT, READ_TIMEOUT);
    }

    public static String postParameters(String url, String paramStr, String charsets) throws ConnectTimeoutException,
                                                                                    SocketTimeoutException, Exception {
        return post(url, paramStr, charsets, "application/xml", CONN_TIMEOUT, READ_TIMEOUT);
    }

    public static String post(String url, String paramStr, String charset, String contentType)
                                                                                              throws ConnectTimeoutException,
                                                                                              SocketTimeoutException,
                                                                                              Exception {
        return post(url, paramStr, charset, contentType, CONN_TIMEOUT, READ_TIMEOUT);
    }

    public static String get(String url, Map<String, String> params) throws Exception {

        StringBuffer paramSb = new StringBuffer();
        for (Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            paramSb.append(key).append("=").append(val).append("&");
        }
        String paramStr = paramSb.substring(0, paramSb.length() - 1);
        if (url.charAt(url.length() - 1) != '?') {
            url = url + "?";
        }
        String requestUrl = url + paramStr;

        return get(requestUrl);
    }

    public static String get(String url) throws Exception {

        return get(url, charset, CONN_TIMEOUT, READ_TIMEOUT);
    }

    public static String get(String url, String charset) throws Exception {
        return get(url, charset, CONN_TIMEOUT, READ_TIMEOUT);
    }

    /**
     * 发�?�?�� Post 请求, 使用指定的字符集编码.
     * 
     * @param url
     * @param body
     *            RequestBody
     * @param charset
     *            编码
     * @param contentType
     * 
     * @param connTimeout
     *            建立链接超时时间,毫秒.
     * @param readTimeout
     *            响应超时时间,毫秒.
     * @return ResponseBody, 使用指定的字符集编码.
     * @throws ConnectTimeoutException
     *             建立链接超时异常
     * @throws SocketTimeoutException
     *             响应超时
     * @throws Exception
     */
    public static String post(String url, String body, String charset, String contentType, Integer connTimeout,
                              Integer readTimeout) throws ConnectTimeoutException, SocketTimeoutException, Exception {
        HttpClient client = null;
        HttpPost post = new HttpPost(url);
        String result = "";
        charset = StringUtils.isBlank(charset) ? "UTF-8" : charset;
        contentType = StringUtils.isBlank(contentType) ? "application/x-www-form-urlencoded" : contentType;

        try {
            if (StringUtils.isNotBlank(body)) {

                HttpEntity entity = new StringEntity(body, ContentType.create(contentType, charset));
                post.setEntity(entity);
            }
            // 设置参数
            Builder customReqConf = RequestConfig.custom();
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout);
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout);
            }
            post.setConfig(customReqConf.build());

            HttpResponse res;
            if (url.startsWith("https")) {
                // 执行 Https 请求.
                client = createSSLInsecureClient();
                res = client.execute(post);
            } else {
                // 执行 Http 请求.
                client = getHttpClient();
                res = client.execute(post);
            }
            result = IOUtils.toString(res.getEntity().getContent(), charset);
        } finally {
            post.releaseConnection();
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
        return result;
    }

    /**
     * 提交form表单
     * 
     * @param url
     * @param params
     * @param connTimeout
     * @param readTimeout
     * @return
     * @throws ConnectTimeoutException
     * @throws SocketTimeoutException
     * @throws Exception
     */
    public static String postForm(String url, Map<String, String> params, Map<String, String> headers,
                                  Integer connTimeout, Integer readTimeout) throws ConnectTimeoutException,
                                                                           SocketTimeoutException, Exception {

        HttpClient client = null;
        HttpPost post = new HttpPost(url);
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> formParams = new ArrayList<org.apache.http.NameValuePair>();
                Set<Entry<String, String>> entrySet = params.entrySet();
                for (Entry<String, String> entry : entrySet) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
                post.setEntity(entity);
            }

            if (headers != null && !headers.isEmpty()) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }
            // 设置参数
            Builder customReqConf = RequestConfig.custom();
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout);
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout);
            }
            post.setConfig(customReqConf.build());
            HttpResponse res = null;
            if (url.startsWith("https")) {
                // 执行 Https 请求.
                client = createSSLInsecureClient();
                res = client.execute(post);
            } else {
                // 执行 Http 请求.
                client = getHttpClient();
                res = client.execute(post);
            }
            return IOUtils.toString(res.getEntity().getContent(), "UTF-8");
        } finally {
            post.releaseConnection();
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
    }

    /**
     * 发�?�?�� GET 请求
     * 
     * @param url
     * @param charset
     * @param connTimeout
     *            建立链接超时时间,毫秒.
     * @param readTimeout
     *            响应超时时间,毫秒.
     * @return
     * @throws ConnectTimeoutException
     *             建立链接超时
     * @throws SocketTimeoutException
     *             响应超时
     * @throws Exception
     */
    public static String get(String url, String charset, Integer connTimeout, Integer readTimeout)
                                                                                                  throws ConnectTimeoutException,
                                                                                                  SocketTimeoutException,
                                                                                                  Exception {

        HttpClient client = null;
        HttpGet get = new HttpGet(url);
        String result = "";
        try {
            // 设置参数
            Builder customReqConf = RequestConfig.custom();
            if (connTimeout != null) {
                customReqConf.setConnectTimeout(connTimeout);
            }
            if (readTimeout != null) {
                customReqConf.setSocketTimeout(readTimeout);
            }
            get.setConfig(customReqConf.build());

            HttpResponse res = null;

            if (url.startsWith("https")) {
                // 执行 Https 请求.
                client = createSSLInsecureClient();
                res = client.execute(get);
            } else {
                // 执行 Http 请求.
                client = getHttpClient();
                res = client.execute(get);
            }

            result = IOUtils.toString(res.getEntity().getContent(), charset);
        } finally {
            get.releaseConnection();
            if (url.startsWith("https") && client != null && client instanceof CloseableHttpClient) {
                ((CloseableHttpClient) client).close();
            }
        }
        return result;
    }

    /**
     * �?response 里获�?charset
     * 
     * @param ressponse
     * @return
     */
    @SuppressWarnings("unused")
    private static String getCharsetFromResponse(HttpResponse ressponse) {
        // Content-Type:text/html; charset=GBK
        if (ressponse.getEntity() != null && ressponse.getEntity().getContentType() != null
            && ressponse.getEntity().getContentType().getValue() != null) {
            String contentType = ressponse.getEntity().getContentType().getValue();
            if (contentType.contains("charset=")) {
                return contentType.substring(contentType.indexOf("charset=") + 8);
            }
        }
        return null;
    }

    /**
     * 创建 SSL连接
     * 
     * @return
     * @throws GeneralSecurityException
     */
    private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

            return HttpClients.custom().setSSLSocketFactory(sslsf).build();

        } catch (GeneralSecurityException e) {
            throw e;
        }
    }

}
