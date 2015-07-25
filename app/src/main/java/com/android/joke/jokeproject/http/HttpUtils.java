package com.android.joke.jokeproject.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.joke.jokeproject.R;
import com.android.joke.jokeproject.common.BaseBean;
import com.android.joke.jokeproject.common.StringUtils;

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




//请求头：Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
//Accept-Encoding: gzip,deflate,sdch
//        Accept-Language: zh-CN,zh;q=0.8,en;q=0.6
//       User-Agent: Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko)
//       Chrome/36.0.1985.143 Safari/537.36
/**
 *
 * GET请求 :

 http://pengfu.junpinzhi.cn/mobileClientV21.ashx?
 client=android&version=1.2&key=111&PageIndex=1

 ？     %3F

 &      %26

 |      %124

 =     %3D

 #     %23

 /      %2F

 +     %2B

 %    %25

 空格  %20


 参数
 key ： 111（全部图片，文字混合）；112（纯文本）；113（纯图片）
 PageIndex： 1 （请求的分页）

 result：true 为返回成功
 message：successfull成功
 body: 体json
 ---pagecount 分页计数
 ---pagesize 每页条数
 ---pageindex 当前页数
 ---items 为json
 ------hid 唯一的id
 ------htitle 内容
 ------rcount
 ------dcount
 ------ptime 时间
 ------pusername 作者
 ------ispic
 ------intor
 ------image 图片json,如果没有如片则为空
 -----------purl 图片的url地址
 -----------ptitle 图片的标题
 *
 *
 * ****/
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
    public  void MainGetData(final Context context, final String whichData , final int count ,IbackData IbackData){
        mIbackData = IbackData;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //请求后返回json数据
                    String pathStr = context.getResources().getString(R.string.method_url_to_get_data)+whichData+"&PageIndex="+count;
                    String result = doHttpGet(pathStr);
                    if(!StringUtils.isNullOrNullStr(result)){
                        Message msg = new Message();
                        msg.obj = result;
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
        public void onSuccess(BaseBean baseBean);

        public void onFailure(String str);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        JSONObject dataJson = new JSONObject( String.valueOf(msg.obj));
                        BaseBean baseBean = new BaseBean(dataJson);
                        String message = baseBean.getStr("message");
                        BaseBean bean = (BaseBean) baseBean.get("body");
                        if("successfull".equals(message)){
                            mIbackData.onSuccess(bean);
                        }else{
                            mIbackData.onFailure("得到数据失败");
                        }
                    } catch (JSONException e) {
                        mIbackData.onFailure("解析数据数据失败");
                        e.printStackTrace();
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