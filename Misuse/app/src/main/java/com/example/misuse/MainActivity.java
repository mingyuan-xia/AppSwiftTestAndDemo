package com.example.misuse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button SQLiteActivity = (Button)findViewById(R.id.SQLiteActivity);
        Button SharedPreferencesActivity = (Button)findViewById(R.id.SharedPreferencesActivity);
        Button ContentProviderActivity = (Button)findViewById(R.id.ContentProviderActivity);
        Button StorageActivity = (Button)findViewById(R.id.StorageActivity);
        Button HttpClientActivity = (Button)findViewById(R.id.HttpClientActivity);
        Button FragmentActivity = (Button)findViewById(R.id.FragmentActivity);
        Button WebViewActivity = (Button)findViewById(R.id.WebviewActivity);
        Button BufferedActivity = (Button)findViewById(R.id.BufferedActivity);

        SQLiteActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SQLiteActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferencesActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SharedPreferencesActivity.class);
                startActivity(intent);
            }
        });

        ContentProviderActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContentProviderActivity.class);
                startActivity(intent);
            }
        });

        StorageActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StorageActivity.class);
                startActivity(intent);
            }
        });

        HttpClientActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HttpClientActivity.class);
                startActivity(intent);
            }
        });

        FragmentActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FragmentActivity.class);
                startActivity(intent);
            }
        });

        WebViewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        });

        BufferedActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BufferedReaderWriterActivity.class);
                startActivity(intent);
            }
        });
    }
}
