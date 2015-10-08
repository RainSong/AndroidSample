package com.androidsample.ygj.androidsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Dictionary;
import java.util.Objects;

/**
 * Created by YGJ on 2015/10/8 0008.
 */
public class HttpUtil {
    /*
    *
    * */
    public static String Get(String url, Dictionary<String, Objects> paras) {
        return "";
    }

    public static String Post(String url, Dictionary<String, Objects> paras) {
        return "";
    }

    public static Bitmap GetHttpBitmap(String url) {
        URL imgUrl;
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            imgUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) imgUrl.openConnection();
            httpURLConnection.setConnectTimeout(2000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            inputStream = httpURLConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

        } catch (Exception ex) {

        }
        return bitmap;
    }
}
