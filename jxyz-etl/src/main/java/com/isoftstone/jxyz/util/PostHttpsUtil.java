package com.isoftstone.jxyz.util;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class PostHttpsUtil {
    //private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 4000;
    private static CloseableHttpClient httpClient;
    private static CookieStore cookieStore = new BasicCookieStore();
    static {
//        connMgr = new PoolingHttpClientConnectionManager();
//        connMgr.setMaxTotal(200);
//        connMgr.setDefaultMaxPerRoute(100);

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        configBuilder.setStaleConnectionCheckEnabled(true);
        configBuilder.setCookieSpec(CookieSpecs.STANDARD);
        requestConfig = configBuilder.build();
        refreshHttpClient();
        // initCookieStore(Conf.get("priceInfoCookie"));
        // initCookieStore(Conf.get("accountBalanceCookie"));
        // initCookieStore(Conf.get("refreshCookie"));
    }

    private static void refreshHttpClient() {
        httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setDefaultRequestConfig(requestConfig).setDefaultCookieStore(cookieStore).build();
    }

    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

    public static void setCookieStore(HttpResponse httpResponse) {
        Header[] headers = httpResponse.getHeaders("Set-Cookie");
        for (Header header : headers) {
            String cookieValueStr=header.getValue();
            String [] cookieValues=cookieValueStr.split(";");
            String cookieName=cookieValues[0].split("=")[0];
            String cookieValue=cookieValues[0].split("=")[1];
            BasicClientCookie cookie = new BasicClientCookie(cookieName, cookieValue);
            cookie.setDomain("api.gate.io");
            cookie.setPath("/");
            cookieStore.addCookie(cookie);
        }
    }

    public static String post(String url, String outputStr) {
        HttpPost httppost = new HttpPost(url);
        Header[] headers = { new BasicHeader("Content-Type", "application/json"),};
        httppost.setHeaders(headers);

        StringEntity uefEntity = new StringEntity(outputStr,"utf-8");//解决中文乱码问题
        uefEntity.setContentEncoding("UTF-8");
        uefEntity.setContentType("application/json");
        httppost.setEntity(uefEntity);
        HttpResponse response;
        try {
            response = httpClient.execute(httppost);
            setCookieStore(response);
            String result = EntityUtils.toString(response.getEntity());
//            System.out.println(EntityUtils.toString(response.getEntity()));
            return result;
        } catch (Exception e) {
            refreshHttpClient();
            e.printStackTrace();
        }
        return "";
    }
}
