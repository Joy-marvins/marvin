package com.example.mysdk;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private SharedHelper sh;

    @Override
    //测试数据加密中的attachBaseContext
    protected void attachBaseContext(Context base) {
        // 在这里调用Context的方法会崩溃
        super.attachBaseContext(base);
        // 在这里可以正常调用Context的方法
        sh = new SharedHelper(base);
        sh.save2("测试数据加密","测试attachBaseContext");
    }
}
