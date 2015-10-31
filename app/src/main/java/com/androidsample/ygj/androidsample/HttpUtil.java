package com.androidsample.ygj.androidsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

/**
 * Created by YGJ on 2015/10/8 0008.
 * Http请求的帮助类
 */
public class HttpUtil {
    /*
    *
    * */
    public static String Get(String url, Map<String, Object> paras)
            throws Exception {
        return null;
    }

    public static String Post(String url, Map<String, Object> paras) throws Exception {

        URL httpUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
        connection.setConnectTimeout(2000);
        // 发送POST请求必须设置如下两行
        connection.setDoInput(true);
        connection.setDoOutput(true);

        //禁用缓存
        connection.setUseCaches(false);

        // 设置通用的请求属性
        connection.setRequestProperty("accept", "application/json, text/javascript, */*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setRequestProperty("Charset", "UTF-8");

        if (paras != null && !paras.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();

            Set<Map.Entry<String, Object>> enntities = paras.entrySet();
            for (Map.Entry<String, Object> para : enntities) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append('&');
                }
                stringBuilder.append(String.format("%s=%s", para.getKey(), para.getValue()));
            }
            String strParas = stringBuilder.toString();
            byte[] bytes = strParas.getBytes(Charset.forName("UTF8"));

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(bytes);

        }

        int requestStatus = connection.getResponseCode();
        if(requestStatus == HttpURLConnection.HTTP_OK){

            InputStream inputStream = connection.getInputStream();

            StringBuilder stringBuilder = new StringBuilder();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String msg = null;
            while ((msg = bufferedReader.readLine()) != null)
                stringBuilder.append(msg);

            return  stringBuilder.toString();
        }
        return null;
    }

    /*
    *  通过Http获取网络图片
    * */
    public static Bitmap GetHttpBitmap(String url)
            throws Exception {
        URL imgUrl;
        Bitmap bitmap = null;
        InputStream inputStream = null;

        imgUrl = new URL(url);
        try{
        HttpURLConnection httpURLConnection = (HttpURLConnection) imgUrl.openConnection();
        httpURLConnection.setConnectTimeout(2000);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.connect();

        int requestCode = httpURLConnection.getResponseCode();
        inputStream = httpURLConnection.getInputStream();

        bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        httpURLConnection.disconnect();
        }
        catch (Exception ex){}

        return bitmap;
    }
}
