package com.androidsample.ygj.androidsample;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.Toast;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Button btnShowDefaultDialog = (Button) findViewById(R.id.BtnShowDefaultDialog);
        btnShowDefaultDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDefaultDialog();
            }
        });
    }

    private void showDefaultDialog(){
        /*
        * 显示一个系统默认的弹出框
        * */
        Dialog dialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.btn_star)
                .setTitle("Dialog")
                .setMessage("This is Dialog")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this,"you chose 'YES'", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this,"you chose 'NO'", Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this,"you chose 'Cancel'", Toast.LENGTH_LONG).show();
                    }
                })
                .create();
        dialog.show();
    }

    private void ShowCustomDialog(){

    }
}
