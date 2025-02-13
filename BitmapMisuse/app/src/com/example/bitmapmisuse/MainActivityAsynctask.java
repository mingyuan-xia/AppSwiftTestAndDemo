package com.example.bitmapmisuse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityAsynctask extends Activity {
    private static final String TAG = "BITMAP_MISUSE";
    private static final int NUM = 5;

    private String timings = "";
    private byte[] imgData;
    private File imgFile;
    private ImageView[] ivs;
    private volatile static int c = 0;
    private JoinTask joinTask;

    private volatile Bitmap dataBmp = null;
    private volatile Bitmap fileBmp = null;
    private volatile Bitmap resourceBmp = null;
    private volatile Bitmap streamBmp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Debug.startMethodTracing("bitmapmisuse");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepare(this, this);

        // set up imageview
        initImageView();
        
        // configure butteon listener
        final Button button = (Button) findViewById(R.id.myButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "Fresh and GC!";
                int duration = Toast.LENGTH_SHORT;

                for (int i =0; i<10; i++){
                    int[] Test = new int[1000000];
                }
                refreshImageView();
                System.gc();
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        button.setOnTouchListener(new View.OnTouchListener() {
            
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                refreshImageView();
                return false;
            }
        });
    }
    
    private void initImageView(){
        ImageView preDefIv = (ImageView) findViewById(R.id.myImageView);
        long startTime0 = System.currentTimeMillis();
        preDefIv.setImageResource(R.drawable.mypic);
        long difference0 = System.currentTimeMillis() - startTime0;
        String s0 = "preDefIv.setImageResource() takes " + difference0 + " ms";
        timings += s0 + "\n";
        Log.d(TAG, s0);

        ivs = new ImageView[NUM];
        for (int i = 0; i < NUM; ++i) {
            ivs[i] = new ImageView(this);
        }

        joinTask = new JoinTask();
        DecodeDataTask ddTask = new DecodeDataTask();
        DecodeFileTask dfTask = new DecodeFileTask();
        DecodeResourceTask drTask = new DecodeResourceTask();
        DecodeStreamTask dsTask = new DecodeStreamTask();
        ddTask.execute(1000);
        dfTask.execute(1000);
        drTask.execute(1000);
        dsTask.execute(1000);
    }
    private void refreshImageView(){
        ivs[4].setImageResource(R.drawable.mypic);
    }

    private void prepare(MainActivityAsynctask m, MainActivityAsynctask n) {
        AssetManager assetManager = getAssets();
        try {
            InputStream fis = assetManager.open("fromfile.jpg");
            imgData = new byte[fis.available()];
            fis.read(imgData);
            fis.close();
            imgFile = new File(getFilesDir(), "image.jpg");
            if (!imgFile.exists()) {
                FileOutputStream fos = new FileOutputStream(imgFile);
                fos.write(imgData);
                fos.close();
            }
        } catch (IOException e) {
            return;
        }
    }

    public class DecodeDataTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... params) {
            long startTime = System.currentTimeMillis();
            Bitmap bm = BitmapFactory.decodeByteArray(imgData, 0,
                    imgData.length);
            long difference = System.currentTimeMillis() - startTime;
            String s = "myDecodeData() takes " + difference + " ms";
            timings += s + "\n";
            dataBmp = bm;
            for (int i = 0; i < 10; ++i) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ivs[0].setImageBitmap(dataBmp);
            c++;
            if (c == 4) {
                joinTask.execute(1000);
            }
        }

    }

    public class DecodeFileTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... params) {
            long startTime = System.currentTimeMillis();
            Bitmap bm = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            long difference = System.currentTimeMillis() - startTime;
            String s = "myDecodeFile() takes " + difference + " ms";
            timings += s + "\n";
            fileBmp = bm;
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ivs[1].setImageBitmap(fileBmp);
            c++;
            if (c == 4) {
                joinTask.execute(1000);
            }
        }

    }

    public class DecodeResourceTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... params) {
            long startTime = System.currentTimeMillis();
            Resources res = getResources();
            int id = R.drawable.mypic;
            Bitmap bm = BitmapFactory.decodeResource(res, id);
            long difference = System.currentTimeMillis() - startTime;
            String s = "myDecodeResource() takes " + difference + " ms";
            timings += s + "\n";
            resourceBmp = bm;
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ivs[2].setImageBitmap(resourceBmp);
            c++;
            if (c == 4) {
                joinTask.execute(1000);
            }
        }

    }

    public class DecodeStreamTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... params) {
            long startTime = System.currentTimeMillis();
            AssetManager assetManager = getAssets();
            try {
                InputStream fis = assetManager.open("fromfile.jpg");
                Bitmap b = BitmapFactory.decodeStream(fis);
                fis.close();
                long difference = System.currentTimeMillis() - startTime;
                String s = "myDecodeStream() takes " + difference + " ms";
                timings += s + "\n";
                streamBmp = b;
            } catch (IOException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ivs[3].setImageBitmap(streamBmp);
            c++;
            if (c == 4) {
                joinTask.execute(1000);
            }
        }

    }

    public class JoinTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            LinearLayout ll = (LinearLayout) findViewById(R.id.myLayout);
            long startTime = System.currentTimeMillis();
            ivs[4].setImageResource(R.drawable.mypic);
            long difference = System.currentTimeMillis() - startTime;
            String s = "setImageResource() takes " + difference + " ms";
            timings += s + "\n";
            Log.d(TAG, s);

            TextView msg = new TextView(MainActivityAsynctask.this);
            msg.setText(timings);
            ll.addView(msg);
            for (ImageView iv : ivs) {
                ll.addView(iv);
            }
        }

    }
}
