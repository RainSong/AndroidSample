package com.androidsample.ygj.androidsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

<<<<<<< HEAD
    /**
     * @param view
     */
=======
>>>>>>> 786b723b40bb5fb7e5715f0160b59404221f31d8
    public void btnGoToGPS_Click(View view){
        GoTo(LocationActivity.class);
    }

<<<<<<< HEAD
    /**
     * @param view
     */
    public void BtnGoToProductList_Click(View view){
        GoTo(ProductListActivity.class);
    }
=======
>>>>>>> 786b723b40bb5fb7e5715f0160b59404221f31d8
    private  void GoTo(Class<?> cls){
        Intent intent = new Intent(MainActivity.this, cls);
        startActivity(intent);
    }

}
