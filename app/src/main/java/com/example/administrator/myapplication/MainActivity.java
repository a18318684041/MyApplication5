package com.example.administrator.myapplication;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private ImageView img;
    private ProgressBar pro;
    private EditText address;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btn = (Button) findViewById(R.id.btn);
        img = (ImageView) findViewById(R.id.img);
        pro = (ProgressBar) findViewById(R.id.progress);
        address = (EditText) findViewById(R.id.address);
        name  = (EditText) findViewById(R.id.name);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载图片
/*                OkHttpUtils
                        .get()
                        .url("http://glxy.gdcp.cn/UploadFile/248/2016/12/14/20161214133535894.jpg")
                        .build().execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        img.setImageBitmap(response);
                    }
                });*/


                //下载文件的进度显示
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = address.getText().toString();
                        final String name = address.getText().toString();
                        OkHttpUtils.get().url(url).build().execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(),"h") {

                            @Override
                            public void inProgress(final float progress, long total, int id) {
                                pro.setProgress((int) (100 * progress));
                            }

                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Log.d("AAA", "onError: "+"发生错误"+e.toString());
                            }

                            @Override
                            public void onResponse(File response, int id) {
                                Log.d("AAA", "onError: "+"正在下载"+response.toString());
                                Toast.makeText(MainActivity.this,"正在下载",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
