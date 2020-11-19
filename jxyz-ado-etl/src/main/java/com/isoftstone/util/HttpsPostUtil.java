package com.isoftstone.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpsPostUtil {

    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 30000;
    private static CloseableHttpClient httpClient;
    private static CookieStore cookieStore = new BasicCookieStore();
    static {
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        configBuilder.setStaleConnectionCheckEnabled(true);
        configBuilder.setCookieSpec(CookieSpecs.STANDARD);
        requestConfig = configBuilder.build();
        refreshHttpClient();
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

    public static String deleteFile(String url,String token){
        HttpDelete httpDelete = new HttpDelete(url);
        if (null!=token || token != ""){
            Header[] headers = { new BasicHeader("gc-authentication", token),};
            httpDelete.setHeaders(headers);
        }
        HttpResponse response;
        try {
            response = httpClient.execute(httpDelete);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            refreshHttpClient();
            e.printStackTrace();
        }
        return "";
    }

    public static String post(String url, String outputStr,String token) {
        HttpPost httppost = new HttpPost(url);
        Header[] headers = { new BasicHeader("Content-Type", "application/json"),};
        httppost.setHeaders(headers);
        if (null != token){
            JSONObject tokenJson = new JSONObject();
            tokenJson.put("token",token);
            httppost.setEntity(new StringEntity(tokenJson.toJSONString(),"utf-8"));
        }
        StringEntity uefEntity = new StringEntity(outputStr,"utf-8");//解决中文乱码问题
        uefEntity.setContentEncoding("UTF-8");
        uefEntity.setContentType("application/json");
        httppost.setEntity(uefEntity);
        HttpResponse response;
        try {
            response = httpClient.execute(httppost);
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            refreshHttpClient();
            e.printStackTrace();
        }
        return "";
    }

}
