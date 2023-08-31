package com.example.activitylifecycletest;

import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startNormal = findViewById(R.id.start_normal);
        startNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NormalActivity.class);
                startActivity(intent);
            }
        });
        Button startDialog = findViewById(R.id.start_dialog);
        startDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NormalActivity.class);
                startActivity(intent);
            }
        });
    }
    String TAG = "zy";

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 启动");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 启动");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 启动");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 启动");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 启动");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: 启动");
    }

}