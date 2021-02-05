package com.example.mysdk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    //建立三个表，分别为user、leave、sign
    public static final String user="CREATE TABLE \"user\" (\n" +
            "\t`id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t`name`\tTEXT,\n" +
            "\t`id_number`\tTEXT,\n" +
            "\t`password`\tTEXT,\n" +
            "\t`class`\tTEXT,\n" +
            "\t`type`\tINTEGER,\n" +
            "\t`sign_number`\tINTEGER\n" +
            ")";
    public static final String sign="CREATE TABLE \"sign\" (\n" +
            "\t`sign_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t`name`\tTEXT,\n" +
            "\t`status`\tINTEGER,\n" +
            "\t`sign_time`\tTEXT,\n" +
            "\t`location`\tTEXT\n" +
            ")";
    public  static final String leave="CREATE TABLE `leave` (\n" +
            "\t`leave_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t`name`\tTEXT,\n" +
            "\t`start_time`\tTEXT,\n" +
            "\t`end_time`\tTEXT,\n" +
            "\t`cause`\tTEXT\n" +
            ")";
    public static final String CREATE_BOOK = "create table Book (" +
            "id integer primary key autoincrement, " +
            "author text, " +
            "price real, " +
            "page integer, " +
            "name text)";
    private Context mcontext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mcontext=context;
    }

    @Override
    //执行数据库操作
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(sign);
        db.execSQL(user);
        db.execSQL(leave);
        Toast.makeText(mcontext,"Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    //更新数据库
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists sign");
        db.execSQL("drop table if exists leave");
        onCreate(db);
    }
}
