package com.androidsample.ygj.androidsample;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity  {
    private Button btnLogin;
    private EditText txtUserName;
    private  EditText txtUserPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        txtUserName = (EditText)findViewById(R.id.txt_user_name);
        txtUserPassword = (EditText)findViewById(R.id.txt_user_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUserName==null || txtUserPassword==null)return;
                String strUserName = txtUserName.getText().toString().trim();
                String strUserPwd = txtUserPassword.getText().toString().trim();
                if(strUserName==null || strUserName.length()==0){
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(strUserPwd==null || strUserPwd.length()==0){
                    Toast.makeText(LoginActivity.this,"用户密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    private void BtnLogin_Click(View view){
        if(txtUserName==null || txtUserPassword==null)return;
        String strUserName = txtUserName.getText().toString().trim();
        String strUserPwd = txtUserPassword.getText().toString().trim();
        if(strUserName==null || strUserName.length()==0){
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(strUserPwd==null || strUserPwd.length()==0){
            Toast.makeText(LoginActivity.this,"用户密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
