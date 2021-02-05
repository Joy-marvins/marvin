package com.example.mysdk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btn_back;
    private Button btn_createSDcard;
    private TextView textView;
    private EditText editname;
    private EditText editpasswd;
    private Button btnlogin;
    private String strname;
    private TextView time1;
    private String strpasswd;
    private SharedHelper sh;
    private Context mContext;
    private Context othercontext;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 1);

//        btn_createSDcard =  (Button)findViewById(R.id.create_SDcard);
//        btn_back=findViewById(com.example.mysdk.R.id.btn_back);
        textView = (TextView) findViewById(R.id.textView);
        Button btnshow=(Button)findViewById(R.id.btnshow);
        Button btn_calljs=(Button)findViewById(R.id.btn_calljs);
        Button btn_printlog=(Button)findViewById(R.id.btn_printlog);
        final WebView mWebView = (WebView) findViewById(R.id.WebView);
        textView.setText(MyJni.getString());
        mContext = getApplicationContext();
        sh = new SharedHelper(mContext);
        bindViews();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        Time now = new Time();

        mWebView.loadUrl("file:///android_asset/test.html");
        WebSettings webSettings = mWebView.getSettings();
//        createDatabase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /**
//                 * getWritableDatabase:表示可以创建或打开一个现有的数据库(如果数据库已经存在则直接打开,
//                 *  否则创建一个新的数据库), 并返回一个对数据库进行读写操作的对象
//                 *  getReadableDatabase:同上, 但是如果出现不可写入, 比如磁盘满了, 此方法返回的对象以只读的方式打开
//                 *  而getWritableDatabase则会抛出异常.
//                 *
//                 */
//                dbHelper.getWritableDatabase();
//            }
//        });
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得第一个应用的包名,从而获得对应的Context,需要对异常进行捕获
                try {
                    othercontext = createPackageContext("com.example.mysdk", Context.CONTEXT_IGNORE_SECURITY);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                //根据Context取得对应的SharedPreferences
                sp = othercontext.getSharedPreferences("mysp", MODE_PRIVATE);
                String name = sp.getString("username", "");
                String passwd = sp.getString("passwd", "");
                Toast.makeText(getApplicationContext(), "存储在SharedPreference中的\n用户名为：" + name + "\n密码为：" + passwd, Toast.LENGTH_SHORT).show();
                //指定目录存入数据
            }
        });

        btn_printlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得第一个应用的包名,从而获得对应的Context,需要对异常进行捕获
                try {
                    othercontext = createPackageContext("com.example.mysdk", Context.CONTEXT_IGNORE_SECURITY);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                //根据Context取得对应的SharedPreferences
                Log.d("============================================测试防日志输出：", "日志输出成功");
            }
        });


        btn_calljs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {

                        // 注意调用的JS方法名要对应上btnlogin
                        // 调用javascript的callJS()方法
                        mWebView.loadUrl("javascript:myFunction()");
                    }
                });
            }
        });
        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

        });
//        btn_createSDcard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(MainActivity.this,SaveToSdCardActivity.class);
//                startActivity(intent);
//            }
//        });
    }
    private void bindViews() {
        final MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 1);
        editname = (EditText)findViewById(com.example.mysdk.R.id.editname);
        editpasswd = (EditText)findViewById(com.example.mysdk.R.id.editpasswd);
        btnlogin = (Button)findViewById(com.example.mysdk.R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strname = editname.getText().toString();
                strpasswd = editpasswd.getText().toString();
                sh.save(strname,strpasswd);
                dbHelper.getWritableDatabase();
                if (Environment.getExternalStorageState().equals("mounted"))
                    try
                    {
                        writeFileToSDCard();
                    }
                    catch (IOException localIOException)
                    {
//                        while (true)
                            localIOException.printStackTrace();
                    }
                else
                    Log.d("SDCard错误", "未安装SDCard！");


//                sh.save2(strname,strpasswd);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        Map<String,String> data = sh.read();
        editname.setText(data.get("username"));
        editpasswd.setText(data.get("passwd"));
    }

    //在指定目录写入数据
    private void writeFileToSDCard()

            throws IOException
                {
                    //获取当前时间
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
                    Date curDate = new Date(System.currentTimeMillis());
                    String str = formatter.format(curDate);
                    File localFile1 = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "Bangcle");
                    //存储路径:/SDcard/bangcel/
                    localFile1.mkdir();
                    File localFile2 = new File(localFile1.getAbsoluteFile(), "testFile.txt");
//                    File localFile2 = new File(localFile1.getAbsoluteFile(), fileName);
                    Log.d("文件路径", localFile2.getAbsolutePath());
                    localFile2.createNewFile();
                    FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
                    byte[] arrayOfByte = "测试SDCard数据加密\n\n123213274673868347584358435\nqweerrttyyuQWERRTYU\n！@#￥￥%%…………&*（（）——+}{：“）》？\n\n".getBytes();
                    byte[] arrayOfByte2 = str.getBytes();
                    localFileOutputStream.write(arrayOfByte, 0, arrayOfByte.length);
                    localFileOutputStream.write(arrayOfByte2, 0, arrayOfByte2.length);
                    localFileOutputStream.flush();
                    localFileOutputStream.close();
                    Log.d("文件写入", "成功");
                }
}
