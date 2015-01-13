package com.android.joke.jokeproject.http;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.joke.jokeproject.common.BaseBean;
import com.android.joke.jokeproject.common.StringUtils;

public class HttpUtils {
    private static final int CONNECTION_TIMEOUT = 10000;


    private static HttpUtils httpUtils;
    public static HttpUtils getIntences() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
        }
        return httpUtils;
    }

    public static String doHttpGet(String serverURL) throws Exception {
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParameters, CONNECTION_TIMEOUT);
        HttpClient hc = new DefaultHttpClient();
        HttpGet get = new HttpGet(serverURL);
        get.addHeader("Content-Type", "text/xml");
        get.setParams(httpParameters);
        HttpResponse response = null;
        try {
            response = hc.execute(get);
        } catch (UnknownHostException e) {
            throw new Exception("Unable to access " + e.getLocalizedMessage());
        } catch (SocketException e) {
            throw new Exception(e.getLocalizedMessage());
        }
        int sCode = response.getStatusLine().getStatusCode();
        if (sCode == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity());
        } else{
            throw new Exception("StatusCode is " + sCode);
        }
    }

    public  byte[] doHttpsGet(String serverURL) throws Exception {
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParameters, CONNECTION_TIMEOUT);
        HttpClient hc = initHttpClient(httpParameters);
        HttpGet get = new HttpGet(serverURL);
        get.addHeader("Content-Type", "text/xml");
        get.setParams(httpParameters);
        HttpResponse response = null;
        try {
            response = hc.execute(get);
        } catch (UnknownHostException e) {
            throw new Exception("Unable to access " + e.getLocalizedMessage());
        } catch (SocketException e) {
            throw new Exception(e.getLocalizedMessage());
        }
        int sCode = response.getStatusLine().getStatusCode();
        if (sCode == HttpStatus.SC_OK) {
            //return EntityUtils.toString(response.getEntity());
            List<Cookie> cookies = ((AbstractHttpClient) hc).getCookieStore().getCookies();
            if (cookies.isEmpty()) {
            } else {
                list = new ArrayList<String>();
                for (int i = 0; i < cookies.size(); i++ ) {
                    //保存cookie
                    list.add(cookies.get(i).getName() + "=" + cookies.get(i).getValue());
                }
            }
            return EntityUtils.toByteArray(response.getEntity());
        } else
            throw new Exception("StatusCode is " + sCode);
    }

    public static String doHttpPost(String serverURL, String xmlString) throws Exception {
        Log.d("doHttpPost", "serverURL="+serverURL);
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParameters, CONNECTION_TIMEOUT);
        HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(httpParameters, HTTP.UTF_8);
        HttpClient hc = new DefaultHttpClient();
        HttpPost post = new HttpPost(serverURL);
        post.addHeader("Content-Type", "text/xml");
        post.setEntity(new StringEntity(xmlString, "UTF-8"));
        post.setParams(httpParameters);
        HttpResponse response = null;
        try {
            response = hc.execute(post);
        } catch (UnknownHostException e) {
            throw new Exception("Unable to access " + e.getLocalizedMessage());
        } catch (SocketException e) {
            throw new Exception(e.getLocalizedMessage());
        }
        int sCode = response.getStatusLine().getStatusCode();
        Log.d("response code ", "sCode="+sCode);
        if (sCode == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity());
        } else
            throw new Exception("StatusCode is " + sCode);
    }

    public  String doHttpsPost(String serverURL, String xmlString) throws Exception {
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParameters, CONNECTION_TIMEOUT);
        HttpClient hc = initHttpClient(httpParameters);
        HttpPost post = new HttpPost(serverURL);
        //post.addHeader("Content-Type", "text/xml");
        post.addHeader("Accept", "*/*");
        post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        if(list != null && list.size()>0){
            StringBuilder sb = new StringBuilder();
            for(int i =0;i<list.size();i++){
                sb.append(list.get(i)).append("; ");
            }
            sb.deleteCharAt(sb.length()-2);
            post.addHeader("Cookie", sb.toString());
        }
        post.setEntity(new StringEntity(xmlString, "UTF-8"));
        post.setParams(httpParameters);
        HttpResponse response = null;
        try {
            response = hc.execute(post);
        } catch (UnknownHostException e) {
            throw new Exception("Unable to access " + e.getMessage());
        } catch (SocketException e) {
            throw new Exception(e.getMessage());
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        int sCode = response.getStatusLine().getStatusCode();
        Log.i("hcy", "sCode:"+sCode);
        if (sCode == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity());
        } else
            throw new Exception("StatusCode is " + sCode);
    }

    public  HttpClient initHttpClient(HttpParams params) {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SSLSocketFactoryImp(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient(params);
        }
    }

    public  class SSLSocketFactoryImp extends SSLSocketFactory {
        final SSLContext sslContext = SSLContext.getInstance("TLS");

        public SSLSocketFactoryImp(KeyStore truststore) throws NoSuchAlgorithmException,
                KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);

            TrustManager tm = new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                               String authType) throws java.security.cert.CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                               String authType) throws java.security.cert.CertificateException {
                }
            };
            sslContext.init(null, new TrustManager[] {
                    tm
            }, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
                throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }

    public  Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }


    /**
     *
     * 请求数据，
     * */
    public  void MainGetData(IbackData IbackData){
        mIbackData = IbackData;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //请求后返回json数据
                    String result = doHttpsPost("","");
                    if(!StringUtils.isNullOrNullStr(result)){
                        Message msg = new Message();
                        msg.obj = "";
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                    }else{
                        mHandler.sendEmptyMessage(2);
                    }
                } catch (Exception e) {
                    mHandler.sendEmptyMessage(2);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private  List<String> list;
    private  IbackData mIbackData;
    public interface IbackData{
        public void onSuccess(String str);

        public void onFailure(String str);
    }

    @SuppressLint("HandlerLeak")
    private    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    JSONObject dataJson = null;
                    try {
                        dataJson = new JSONObject(String.valueOf(msg.obj));
                    } catch (JSONException e) {
                        mIbackData.onFailure("解析数据数据失败");
                        e.printStackTrace();
                    }
                    BaseBean baseBean = new BaseBean(dataJson);
                    BaseBean bean = (BaseBean) baseBean.get("data");
                    String result1 = bean.getStr("data");
                    if(result1 != null){
                        mIbackData.onSuccess(result1);
                    }else{
                        mIbackData.onFailure("得到数据失败");
                    }
                    break;
                case 2:
                    mIbackData.onFailure("连接失败");
                    break;
                default :
                    break;
            }
        }
    };

}  