package com.channey.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.channey.utils.CommonUtils;
import com.channey.utils.DeviceUtils;
import com.channey.utils.FormatUtils;
import com.channey.utils.ShapeUtil;
import com.channey.utils.SharedPreferencesUtils;
import com.channey.utils.StringUtils;
import com.channey.utils.ToastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button showToastBtn = (Button) findViewById(R.id.show_toast);
        tv = (TextView) findViewById(R.id.tv);
        Set<String> set = SharedPreferencesUtils.INSTANCE.getStringSet(this, "test", Activity.MODE_PRIVATE);
        String date1 = FormatUtils.INSTANCE.timeStamp2String(Long.parseLong("1530765537000"),"yyyy.MM.dd HH:mm:ss");
        long stamp = FormatUtils.INSTANCE.string2TimeStamp("yyyy.MM.dd HH:mm:ss", date1);
        Log.d(TAG,"date="+date1+",stamp="+stamp);
//        Log.d("qian","date2 : "+formatDate(1530765537000l));
//        Log.d("qian","current date : "+formatDate(System.currentTimeMillis()));
        showToastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.INSTANCE.showToast(MainActivity.this, CommonUtils.INSTANCE.sha1(MainActivity.this));
                doubleFormat(1600.0);
            }
        });
        shapeUtilTest();

    }

    private void doubleFormat(double d){
        Log.d("qian",StringUtils.INSTANCE.doubleFormat(d));
    }

    public String formatDate(long timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8")); //设置时区
        Date date = new Date(timeStamp);
        String sd = sdf.format(date);
        String format = sdf.format(timeStamp);
        Log.d(TAG,"sdf.format(timeStamp) = "+sdf.format(timeStamp));
        return sd;
    }

    private void shapeUtilTest(){
        Map<String,Object> map = new HashMap<>();
        map.put(ShapeUtil.PROPERTY_RADIUS,20f);
//        map.put(ShapeUtil.PROPERTY_SOLID_COLOR,Color.YELLOW);
        map.put(ShapeUtil.PROPERTY_LEFTTOP_RADIUS,30f);
        map.put(ShapeUtil.PROPERTY_RIGHTTOP_RADIUS,30f);
        map.put(ShapeUtil.PROPERTY_RIGHTBOTTOM_RADIUS,60f);
        map.put(ShapeUtil.PROPERTY_STROKE_WIDTH,10);
        map.put(ShapeUtil.PROPERTY_STROKE_COLOR,Color.GREEN);
        int[] colors = {getResources().getColor(R.color.FF9B00),getResources().getColor(R.color.FF6200)};
        map.put(ShapeUtil.PROPERTY_GRADIENT_COLORS,colors);
        map.put(ShapeUtil.PROPERTY_ORIENTATION, GradientDrawable.Orientation.LEFT_RIGHT);
        ShapeUtil.INSTANCE.setShape(tv, map);
    }
}
