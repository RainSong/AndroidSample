package com.androidsample.ygj.androidsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetTitle("导航窗口");
    }

    private void SetTitle(String title){
        Window window = getWindow();
        window.setTitle(title);
    }

    /**
     * @param view
     */
    public void BtnGoLogin_Click(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * @param view
     */
    public void BtnGoToList_Click(View view) {
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        startActivity(intent);
    }

    /**
     * @param view
     */
    public void btnGoToHttpImageView_Click(View view){
        GoTo(HttpImageViewActivity.class);
    }

    /**
     * @param view
     */

    public void btnGoToGPS_Click(View view){
        GoTo(LocationActivity.class);
    }

    /**
     * @param view
     */
    public void BtnGoToProductList_Click(View view){
        GoTo(ProductListActivity.class);
    }

    public void BtnToToDialog_Click(View view){
        GoTo(DialogActivity.class);
    }

    private  void GoTo(Class<?> cls){
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }

}
