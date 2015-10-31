package com.androidsample.ygj.androidsample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;

    private TextView txtLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) return;

        txtLocation = (TextView) findViewById(R.id.txtLocation);

        //GPS没有打开弹出对话框，询问是否打开GPS
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this)
                    .setTitle("GPS没有打开")
                    .setMessage("是否去设置面板打开GPS？");
            SetPositiveButton(alertBuilder);
            SetNegativeButton(alertBuilder);
            alertBuilder.show();
        } else {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    UpdateView(location);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {
                    //当GPS LocationProvider可用是更新位置
                    try {
                        Location location = locationManager.getLastKnownLocation(provider);
                        UpdateView(location);
                    } catch (Exception ex) {
                    }
                }

                @Override
                public void onProviderDisabled(String provider) {
                    UpdateView(null);
                }
            };
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 8, locationListener);
            } catch (Exception ex) {
            }
        }
    }

    /*
    * 添加确定按钮，打开设置面板
    * */
    private void SetPositiveButton(AlertDialog.Builder builder) {
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                try {
                    startActivity(intent);
                } catch (Exception ex) {
                    intent.setAction(Settings.ACTION_SETTINGS);
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                    }
                }
            }
        });
    }

    /*
    * 添加取消按钮，返回主面板
    * */
    private void SetNegativeButton(AlertDialog.Builder builder) {
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(LocationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void UpdateView(Location location) {
        if (txtLocation == null) return;
        ;
        if (location != null) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                stringBuilder.append("实时位置信息：\n");
                stringBuilder.append(String.format("当前经度：%f\n", location.getLongitude()));
                stringBuilder.append(String.format("当前维度：%f\n", location.getLatitude()));
                stringBuilder.append(String.format("当前高度：%f\n", location.getAltitude()));
                stringBuilder.append(String.format("当前速度：%f\n", location.getSpeed()));
                stringBuilder.append(String.format("当前方向：%f\n", location.getBearing()));
                txtLocation.setText(stringBuilder.toString());
            } catch (Exception ex) {
                Log.e("错误", "获取位置信息失败", ex);
            }

        } else {
            txtLocation.setText("无法获取位置信息");
        }
    }

}
