package com.lm.threadpoolexecutor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ThreadManager.ThreadPool instance = ThreadManager.getInstance();
        for (int i = 0; i < 20; i++) {
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    Log.d("tag","开启线程下载");
                }
            };
            instance.execute(runnable);
        }
    }
}
