package com.example.myapplication.AddToDoList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myapplication.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WheelAddTime extends Activity {
    private TextView tv_wheel_time, wheel_qx, wheel_qd;
    private WheelView wv_date, wv_hour, wv_minute;
    private Date wheelDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_add_time);

        //窗口对齐屏幕宽度
        Window win = this.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        win.setGravity(Gravity.BOTTOM);
        //点击窗口外部关闭
        this.setFinishOnTouchOutside(true);

        bindView();

        SharedPreferences sp=getSharedPreferences("selectdate",MODE_PRIVATE);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
        try {
            wheelDateTime = sim.parse(sp.getString("selectDate",""));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //定义WheelView的style，比如选中文字大小和其他文字大小
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 16;

        //在这里设置一个WheelView的Adapter作为数据源的适配器
        wv_date.setWheelAdapter(new ArrayWheelAdapter(this));
        //为WheelView设置一个皮肤风格（这里在WheelView中已经封装了一个Holo）
        wv_date.setSkin(WheelView.Skin.Holo);
        //这里将数据放入WheelView中
        wv_date.setWheelData(createDateDatas());
        //设置WheelView的Style（上面已经定义）
        wv_date.setWheelSize(5);
        wv_date.setStyle(style);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        wv_date.setSelection((int) ((wheelDateTime.getTime() - calendar.getTimeInMillis()) / (24 * 60 * 60 * 1000)));
        wv_date.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                tv_wheel_time.setText(wv_date.getSelectionItem()+" "+wv_hour.getSelectionItem()+":"+wv_minute.getSelectionItem());
            }
        });

        wv_hour.setWheelAdapter(new ArrayWheelAdapter(this));
        wv_hour.setSkin(WheelView.Skin.Holo);
        wv_hour.setWheelData(createHourDatas());
        wv_hour.setWheelSize(5);
        wv_hour.setLoop(true);
        wv_hour.setStyle(style);
        wv_hour.setSelection(wheelDateTime.getHours()+1);
        wv_hour.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                tv_wheel_time.setText(wv_date.getSelectionItem()+" "+wv_hour.getSelectionItem()+":"+wv_minute.getSelectionItem());
            }
        });

        wv_minute.setWheelAdapter(new ArrayWheelAdapter(this));
        wv_minute.setSkin(WheelView.Skin.Holo);
        wv_minute.setWheelData(createMinuteDatas());
        wv_minute.setWheelSize(5);
        wv_minute.setLoop(true);
        wv_minute.setStyle(style);
        wv_minute.setSelection(wheelDateTime.getMinutes()/5+1);
        wv_minute.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                tv_wheel_time.setText(wv_date.getSelectionItem()+" "+wv_hour.getSelectionItem()+":"+wv_minute.getSelectionItem());
            }
        });

        wheel_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        wheel_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=getSharedPreferences("selectdate",MODE_PRIVATE).edit();
                editor.putString("selectDate",tv_wheel_time.getText().toString());
                editor.apply();
                finish();
            }
        });
    }

    private List createMinuteDatas() {
        List minutes = new ArrayList();
        for (int i = 0; i < 60; ) {
            minutes.add(addZone(i));
            i = i + 5;
        }
        return minutes;
    }

    private List createHourDatas() {
        List hours = new ArrayList();
        for (int i = 0; i < 24; i++) {
            hours.add(addZone(i));
        }
        return hours;
    }

    private String addZone(int i) {
        if (i < 10) return "0" + i;
        else return "" + i;
    }

    private List createDateDatas() {
        List dates = new ArrayList();
        Calendar dateC = Calendar.getInstance();
        dateC.clear();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy年MM月dd日");
        while (dateC.get(Calendar.YEAR) < 2101) {
            dates.add(sim.format(dateC.getTime()));
            dateC.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    private void bindView() {
        tv_wheel_time = findViewById(R.id.tv_wheel_time);
        wheel_qx = findViewById(R.id.tv_qx);
        wheel_qd = findViewById(R.id.tv_qd);
        wv_date = findViewById(R.id.wv_date);
        wv_hour = findViewById(R.id.wv_hour);
        wv_minute = findViewById(R.id.wv_minute);
    }
}
