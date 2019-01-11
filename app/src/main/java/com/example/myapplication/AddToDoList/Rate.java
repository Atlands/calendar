package com.example.myapplication.AddToDoList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myapplication.R;

public class Rate extends Activity implements View.OnClickListener {

    private TextView oneActivity,everyDay,everyWeek,everyMonth,everyYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
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
    }

    private void bindView() {
        oneActivity=findViewById(R.id.one_activity);
        everyDay=findViewById(R.id.everyday);
        everyWeek=findViewById(R.id.everyweek);
        everyMonth=findViewById(R.id.everymonth);
        everyYear=findViewById(R.id.everyyear);

        oneActivity.setOnClickListener(this);
        everyDay.setOnClickListener(this);
        everyWeek.setOnClickListener(this);
        everyMonth.setOnClickListener(this);
        everyYear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor editor=getSharedPreferences("selectdate",MODE_PRIVATE).edit();
        switch (view.getId()){
            case R.id.one_activity:
                editor.putString("rate", "一次性活动");
                break;
            case R.id.everyday:
                editor.putString("rate", "每天");
                break;
            case R.id.everyweek:
                editor.putString("rate", "每周");
                break;
            case R.id.everymonth:
                editor.putString("rate", "每月");
                break;
            case R.id.everyyear:
                editor.putString("rate", "每年");
                break;
        }
        editor.apply();
        finish();
    }
}
