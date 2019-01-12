package com.example.myapplication.AddToDoList;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.db.MyCalendar;
import com.example.myapplication.mainC.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AddToDoList extends AppCompatActivity implements View.OnClickListener {

    private TextView fg1, fg2, fg3, fg4;
    private Toolbar toolbar;
    private ImageButton sure;
    private FrameLayout addContent;
    private AddRicheng fragment1;
    private AddShengri fragment2;
    private AddJinianri fragment3;
    private AddDaoshu fragment4;

    //private Add
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do_list);
        bindView();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        fg1.performClick();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.todolist_sure:
                SharedPreferences sharedPreferences = getSharedPreferences("selectdate", Context.MODE_PRIVATE);
                String category=sharedPreferences.getString("category","日程");
                MyCalendar myCalendar=new MyCalendar();
                myCalendar.setCategory(category);
                myCalendar.setTitle(sharedPreferences.getString("title",null));
                myCalendar.setContent(sharedPreferences.getString("content",null));
                myCalendar.setDate(sharedPreferences.getString("selectDate",null));
                SimpleDateFormat sim=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                try {
                    Date time=sim.parse(sharedPreferences.getString("selectDate",null));
                    Calendar calendar=Calendar.getInstance();
                    calendar.clear();
                    calendar.setTime(time);
                    List<Date> date=new ArrayList<>();
                    date.add(time);
                    if (category.equals("日程")){
                        myCalendar.setRate(sharedPreferences.getString("rate",null));
                        switch (sharedPreferences.getString("rate",null)){
                            case "一次性活动":
                                break;
                            case "每天":
                                while (time.getYear()<2100){
                                    calendar.add(Calendar.DATE,1);
                                    date.add(calendar.getTime());
                                }
                                break;
                            case "每周":
                                while (time.getYear()<2100){
                                    calendar.add(Calendar.DATE,7);
                                    date.add(calendar.getTime());
                                }
                                break;
                            case "每月":
                                while (time.getYear()<2100){
                                    calendar.add(Calendar.MONTH,1);
                                    date.add(calendar.getTime());
                                }
                                break;
                            case "每年":
                                while (time.getYear()<2100){
                                    calendar.add(Calendar.YEAR,1);
                                    date.add(calendar.getTime());
                                }
                                break;
                            default:
                                break;
                        }
                        myCalendar.setDateList(date);
                    }else if (category.equals("生日")||category.equals("纪念日")){
                        String remark[]=sharedPreferences.getString("remark",null).split("");
                        for (String i:remark){
                            calendar.setTime(time);
                            if (i.equals("1")) {
                                calendar.add(Calendar.DATE,30);
                                date.add(calendar.getTime());
                            }
                            if (i.equals("2")) {
                                calendar.add(Calendar.DATE,100);
                                date.add(calendar.getTime());
                            }
                            if (i.equals("3")) {
                                while (time.getYear()<2100){
                                    calendar.add(Calendar.YEAR,1);
                                    date.add(calendar.getTime());
                                }
                            }
                        }
                        myCalendar.setRemark(sharedPreferences.getString("remark",null));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                myCalendar.save();
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.richeng:
                setSelected();
                fg1.setSelected(true);
                replaceFragment(new AddRicheng());
                break;
            case R.id.shengri:
                setSelected();
                fg2.setSelected(true);
                replaceFragment(new AddShengri());
                break;
            case R.id.jinian:
                setSelected();
                fg3.setSelected(true);
                replaceFragment(new AddJinianri());
                break;
            case R.id.daoshu:
                setSelected();
                fg4.setSelected(true);
                replaceFragment(new AddDaoshu());
                break;
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.add_view_content, fragment);
        transaction.commit();
    }

    private void setSelected() {
        fg1.setSelected(false);
        fg2.setSelected(false);
        fg3.setSelected(false);
        fg4.setSelected(false);
    }

    private void bindView() {
        toolbar = findViewById(R.id.todolist_back);
        sure=findViewById(R.id.todolist_sure);
        fg1 = findViewById(R.id.richeng);
        fg2 = findViewById(R.id.shengri);
        fg3 = findViewById(R.id.jinian);
        fg4 = findViewById(R.id.daoshu);
        addContent = findViewById(R.id.add_view_content);

        fg1.setOnClickListener(this);
        fg2.setOnClickListener(this);
        fg3.setOnClickListener(this);
        fg4.setOnClickListener(this);
        sure.setOnClickListener(this);
    }
}
