package com.androidsample.ygj.androidsample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Hashtable;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnShowPassword;
    private EditText txtUserName;
    private EditText txtUserPassword;

    private boolean blPasswordShow = false;
    private TextWatcher txtPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (txtUserPassword == null || btnShowPassword == null) return;
            String strUserPassword = txtUserPassword.getText().toString();
            if (strUserPassword != null && strUserPassword.length() > 0) {
                btnShowPassword.setEnabled(true);
            } else {
                blPasswordShow = false;
                btnShowPassword.setText("显示密码");
                txtUserPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                btnShowPassword.setEnabled(false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnShowPassword = (Button) findViewById(R.id.button_show_password);
        txtUserName = (EditText) findViewById(R.id.txt_user_name);
        txtUserPassword = (EditText) findViewById(R.id.txt_user_password);

        txtUserPassword.addTextChangedListener(txtPasswordWatcher);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtUserName == null || txtUserPassword == null) return;
                String strUserName = txtUserName.getText().toString().trim();
                String strUserPwd = txtUserPassword.getText().toString().trim();
                if (strUserName == null || strUserName.length() == 0) {
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (strUserPwd == null || strUserPwd.length() == 0) {
                    Toast.makeText(LoginActivity.this, "用户密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> mapParas = new Hashtable<String, Object>();
                mapParas.put("user_name", strUserName);
                mapParas.put("user_pwd", strUserPwd);
                mapParas.put("method", "user_login");

                String url = "http://192.168.1.104:8081/handlers/AjaxHandler.ashx";

                String json = null;
                try {
                    json = HttpUtil.Post(url, mapParas);
                } catch (Exception ex) {
                    Log.e("错误", "登录失败", ex);
                }

                if (json != null && json.length() > 0) {
                    try {
                        JSONTokener jsonTokener = new JSONTokener(json);
                        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
                        int status = jsonObject.getInt("Status");
                        Object objMessage = jsonObject.getString("Message");
                        if (status == 1) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (Exception ex) {
                        Log.e("错误", "反序列化JSON失败", ex);
                    }
                }

            }
        });

        btnShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blPasswordShow) {
                    btnShowPassword.setText("隐藏密码");
                    txtUserPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    blPasswordShow = false;
                } else {
                    btnShowPassword.setText("显示密码");
                    txtUserPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    blPasswordShow = true;
                }
            }
        });
    }
}
