package com.channey.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.channey.utils.FormatUtils;
import com.channey.utils.SharedPreferencesUtils;
import com.channey.utils.StringUtils;
import com.channey.utils.ToastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button showToastBtn = (Button) findViewById(R.id.show_toast);
        String doubleFormat = StringUtils.INSTANCE.doubleFormat(1500.00, 2);
        Set<String> set = SharedPreferencesUtils.INSTANCE.getStringSet(this, "test");
        String date1 = FormatUtils.INSTANCE.timeStamp2String(Long.parseLong("1530765537000"),"yyyy.MM.dd HH:mm:ss");
        long stamp = FormatUtils.INSTANCE.string2TimeStamp("yyyy.MM.dd HH:mm:ss", date1);
        Log.d("qian","date="+date1+",stamp="+stamp);
//        Log.d("qian","date2 : "+formatDate(1530765537000l));
//        Log.d("qian","current date : "+formatDate(System.currentTimeMillis()));
        showToastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<String> set1 = new HashSet<String>();
                set1.add("1234");
                set1.add("5555");
                SharedPreferencesUtils.INSTANCE.saveStringSet(MainActivity.this,"",set1);
                ToastUtils.INSTANCE.showToast(MainActivity.this,StringUtils.INSTANCE.doubleFormat(0.3500000000000008,8));
            }
        });
    }

    public String formatDate(long timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8")); //设置时区
        Date date = new Date(timeStamp);
        String sd = sdf.format(date);
        String format = sdf.format(timeStamp);
        Log.d("qian","sdf.format(timeStamp) = "+sdf.format(timeStamp));
        return sd;
    }
}
