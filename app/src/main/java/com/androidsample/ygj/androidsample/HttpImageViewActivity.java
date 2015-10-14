package com.androidsample.ygj.androidsample;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class HttpImageViewActivity extends AppCompatActivity {
    private String strUrl = "http://www.xn--g6r18kq05d.com/heima_member/uploadproduct/2015-1/20150130163517.jpg";
    private TextView txtView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_image_view);
        txtView = (TextView) findViewById(R.id.urlText);
        imageView = (ImageView) findViewById(R.id.httpImgView);

        if (txtView != null) {
            txtView.setText(strUrl);
        }
        try {
            Bitmap bitmap = HttpUtil.GetHttpBitmap(strUrl);
            imageView.setImageBitmap(bitmap);
        } catch (Exception ex) {
            Log.i("info","load image faild");
            Log.e("error","load image faild",ex);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_http_image_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
