package com.example.mysdk;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SaveToSdCardActivity extends AppCompatActivity {
    private Button save, read, delete;
    private EditText content;
    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_to_sd_card);
        save = (Button) findViewById(R.id.save);
        read = (Button) findViewById(R.id.read);
        delete = (Button) findViewById(R.id.delete);
        content = (EditText) findViewById(R.id.content);
        show = (TextView) findViewById(R.id.show);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile();
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.setText(readFile());
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFile();
            }
        });
    }

    //保存文件到sd卡
    public void saveFile() {
        FileOutputStream fos = null;
        //获取SD卡状态
        String state = Environment.getExternalStorageState();
        //判断SD卡是否就绪
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "请检查SD卡", Toast.LENGTH_SHORT).show();
            return;
        }
        //取得SD卡根目录
        File file = Environment.getExternalStorageDirectory();
        try {
            Log.d("======SD卡根目录：", "" + file.getCanonicalPath().toString());
            //File myFile=new File(file.getCanonicalPath()+"/sd.txt");
            /*
            输出流的构造参数1：可以是File对象  也可以是文件路径
            输出流的构造参数2：默认为False=>覆盖内容； true=>追加内容
             */
            fos = new FileOutputStream(file.getCanonicalPath() + "/sd.txt");
            // fos = new FileOutputStream(file.getCanonicalPath() + "/sd.txt",true);
            //fos=new FileOutputStream(myFile);
            String str = content.getText().toString();
            fos.write(str.getBytes());
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //从SD卡读取文件
    public String readFile() {
        //读的时候要用字符流   万一里面有中文
        BufferedReader reader = null;
        FileInputStream fis = null;
        StringBuilder sbd = new StringBuilder();
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "SD卡未就绪", Toast.LENGTH_SHORT).show();
            return "";
        }
        File root = Environment.getExternalStorageDirectory();
        try {
            fis = new FileInputStream(root + "/sd.txt");
            reader = new BufferedReader(new InputStreamReader(fis));
            String row = "";
            while ((row = reader.readLine()) != null) {
                sbd.append(row);
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sbd.toString();
    }

    //删除SD卡文件
    public void removeFile() {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "SD卡未就绪", Toast.LENGTH_SHORT).show();
            return;
        }
        //取得SD卡根目录
        File root = Environment.getExternalStorageDirectory();
        File myFile=new File(root+"/sd.txt");
        //File myFile=new File(root,"sd.txt");
        if (myFile.exists()) {
            myFile.delete();
            Toast.makeText(this,"文件已删除",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"文件不存在",Toast.LENGTH_SHORT).show();
        }
    }
}
