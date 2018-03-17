package com.utte.fileseparatedemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String SD_CARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        final String path = SD_CARD_PATH + File.separator+ "Balabalajtt.txt";
        final String path_pattern = SD_CARD_PATH + File.separator +"Balabalajtt_%d.txt";
        final String merge_path = SD_CARD_PATH + File.separator+ "Balabalajtt_merge.txt";
        final NDKFileUtils ndkFileUtils = new NDKFileUtils();

        Button diffBtn = findViewById(R.id.diff_btn);
        Button patchBtn = findViewById(R.id.patch_btn);

        diffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ndkFileUtils.diff(path, path_pattern,3);
                Log.d(TAG,"拆分成功");
            }
        });

        patchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ndkFileUtils.patch(path_pattern, 3, merge_path);
                Log.d(TAG, "合并成功");
            }
        });


    }


}
