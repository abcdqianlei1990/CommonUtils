package com.channey.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.channey.utils.StringUtils;
import com.channey.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button showToastBtn = (Button) findViewById(R.id.show_toast);
        showToastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.INSTANCE.showToast(MainActivity.this,StringUtils.INSTANCE.doubleFormat(0.3500000000000008,8));
            }
        });
    }
}
