package com.isoftstone.jxyz.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Slf4j
public class PostHttpsUtil {
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

    public static String get(String token,String url) {
        HttpGet httpGet = new HttpGet(url);
        Header[] headers = { new BasicHeader("gc-authentication", token),};
        httpGet.setHeaders(headers);
        HttpResponse response;
        try {
            response = httpClient.execute(httpGet);
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            refreshHttpClient();
            log.info(">>传输错误:{}", e);
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
            log.info(">>获取token失败:{}", e);
            e.printStackTrace();
        }
        return "";
    }


    public static String uploadPost(String filePath,String uploadUrl,String token){
        HttpPost httppost = new HttpPost(uploadUrl);
        Header[] headers = { new BasicHeader("gc-authentication", token),};
        httppost.setHeaders(headers);
        //传入参数可以为file或者filePath，在此处做转换
        File file = new File(filePath);
        MultipartEntityBuilder builder  = MultipartEntityBuilder.create();
        //设置浏览器兼容模式
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        //设置请求的编码格式
        builder.setCharset(Consts.UTF_8);
        builder.setContentType(ContentType.MULTIPART_FORM_DATA);
        //添加文件
        builder.addBinaryBody("file", file);
        HttpEntity reqEntity = builder.build();
        httppost.setEntity(reqEntity);
        HttpResponse response;
        try {
            response = httpClient.execute(httppost);
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            refreshHttpClient();
            log.info(">>上传文件失败:{}", e);
            e.printStackTrace();
        }
        return "";
    }
}
