package com.androidsample.ygj.androidsample;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YGJ on 2015/11/4 0004.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    //CrashHandler实例
    private static CrashHandler instance;

    //程序的Context对象
    private Context mContext;

    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    //私有构造函数，保证外部无法实例化
    private CrashHandler() {

    }

    //获取CrashHandler实例
    public static CrashHandler getInstance() {
        if (instance == null)
            instance = new CrashHandler();
        return instance;
    }

    public void init(Context context) {
        this.mContext = context;
        //获取系统默认的UncaughtException处理器
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(!handleException(ex) && mContext!=null){
            mDefaultHandler.uncaughtException(thread,ex);
        }
        else{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e("ygj","线程暂停失败",e);
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) return false;
        collectDeviceInfo(mContext);
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        saveCatchInfoToFile(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param context
     */
    private void collectDeviceInfo(Context context) {

        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName()
                    , PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("ygj", "获取PackageInfo时发生错误");
        }
        if (packageInfo != null) {
            String versionName = packageInfo.versionName == null ? "" : packageInfo.versionName;
            infos.put("versionName", versionName);
            infos.put("versioniCode", packageInfo.versionCode + "");
        }

        Field[] fields = Build.class.getFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (IllegalAccessException e) {
                Log.e("ygj", "收集设备信息时发生错误");
            }
        }

    }

    /**
     * 获取日志文件路径
     *
     * @return logFilePath
     **/
    private String getFilePath() {
        String filePath = "";
        //判断SD卡是否存在
        boolean isSDCardExists = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
        // Environment.getExternalStorageDirectory()相当于File file=new File("/sdcard")
        boolean isRootDirExists = Environment.getExternalStorageDirectory().exists();
        if (isSDCardExists && isRootDirExists) {
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/crashlog/";
        } else {
            // MyApplication.getInstance().getFilesDir()返回的路劲为/data/data/PACKAGE_NAME/files，
            // 其中的包就是我们建立的主Activity所在的包
            filePath = SampleApp.getInstance().getFilesDir().getAbsolutePath() + "/crashlog/";
        }
        return filePath;
    }

    /**
     * 将异常信息写入日志文件
     **/
    private void saveCatchInfoToFile(Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            stringBuilder.append(entry.getKey() + "=" + entry.getKey() + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        stringBuilder.append(result);

        long timestamp = System.currentTimeMillis();
        String time = formatter.format(new Date());
        String fileName = "crash-" + time + "-" + timestamp + ".log";
        String filePath = getFilePath();

        File directory = new File(filePath);
        if(!directory.exists()){
            directory.mkdir();
        }

        File file = new File(filePath + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.e("ygj", "创建日志文件失败", e);
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bytes = result.getBytes();
            fileOutputStream.write(bytes, 0, bytes.length);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("ygj", "未找到相日志文件" + filePath + fileName);
        } catch (IOException e) {
            Log.e("ygj", "日志文件失败" + filePath + fileName);
        }
    }

    private void sendCrashlogToPM() {
    }
}
