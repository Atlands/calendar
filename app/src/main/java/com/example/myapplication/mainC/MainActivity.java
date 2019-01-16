package com.example.myapplication.mainC;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.AddToDoList.AddToDoList;
import com.example.myapplication.R;
import com.example.myapplication.db.MyCalendar;
import com.necer.calendar.Miui10Calendar;
import com.necer.entity.NDate;
import com.necer.listener.OnCalendarChangedListener;

import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private Miui10Calendar mainCalendar;
    private TextView toolbarTitle, todayRemark;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddList;
    private int mainYear, mainMonth, mainDay, mainHour, mainMinute;

    private List<ToDoListItem> scheduleList = new ArrayList<>();
    SimpleDateFormat sim = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        LitePal.getDatabase();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        actionBar.setTitle("");

        mainCalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarDateChanged(NDate date) {
                mainYear = date.localDate.getYear();
                mainMonth = date.localDate.getMonthOfYear();
                mainDay = date.localDate.getDayOfMonth();

                toolbarTitle.setText(mainYear + "年" + mainMonth + "月");
                todayRemark.setText(" 农历" + date.lunar.lunarYearStr + "年 " + date.lunar.lunarMonthStr + date.lunar.lunarDayStr);

                initSchedule();
            }

            @Override
            public void onCalendarStateChanged(boolean isMonthSate) {

            }
        });

        fabAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Calendar selectTime = Calendar.getInstance();
                    Date selectDate = sim.parse(mainYear + "年" + mainMonth + "月" + mainDay + "日 "
                            + selectTime.get(Calendar.HOUR_OF_DAY) + ":" + selectTime.get(Calendar.MINUTE));
                    SharedPreferences.Editor editor = getSharedPreferences("selectdate", MODE_PRIVATE).edit();
                    editor.clear();
                    editor.putString("selectDate", sim.format(selectDate));
                    editor.apply();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(MainActivity.this, AddToDoList.class);
                startActivity(intent);
            }
        });
    }

    private void initSchedule() {
        scheduleList.clear();
        List<MyCalendar> sehedules = LitePal.findAll(MyCalendar.class);
        //            if (myCalendar.getTitle().equals("倒数日")){
        //                Date nowdate = new Date();
        //                if (!((calendar.getTime().before(nowdate) || calendar.getTime().after(myCalendar.getDateList().get(0))))) {
        //                    int cha=(int)((myCalendar.getDateList().get(0).getTime()-calendar.getTimeInMillis())/(24*60*60*1000));
        //                    ToDoListItem item = new ToDoListItem(image, myCalendar.getTitle(), myCalendar.getDate()+"("+cha+"天后)");
        //                    scheduleList.add(item);
        //                }
        //                break;
        //            }
        //            if (myCalendar.getDateList().contains(calendar.getTime())) {
        //                ToDoListItem item = new ToDoListItem(image, myCalendar.getTitle(), myCalendar.getDate());
        //                scheduleList.add(item);
        //            }
        for (MyCalendar myCalendar : sehedules) {
            try {

                Date date = sim.parse(myCalendar.getDate());
                //数据库日程时间
                Calendar ctime = Calendar.getInstance();
                ctime.clear();
                ctime.set(date.getYear(), date.getMonth(), date.getDay());
                //日历点击时间
                Calendar clicktime = Calendar.getInstance();
                clicktime.clear();
                clicktime.set(mainYear, mainMonth, mainDay);
                //现在时间
                Calendar nowtime = Calendar.getInstance();

                switch (myCalendar.getTitle()) {
                    case "日程":
                        boolean additme=false;
                        switch (myCalendar.getRate()) {
                            case "一次性活动":
                                if (clicktime.getTime().equals(ctime.getTime())) {
                                    additme=true;
                                }
                                break;
                            case "每天":
                                if (!(clicktime.getTime().before(ctime.getTime()))) {
                                    additme=true;
                                }
                                break;
                            case "每周":
                                if ((!(clicktime.getTime().before(ctime.getTime()))) && (
                                        clicktime.get(Calendar.DAY_OF_WEEK) == ctime.get(Calendar.DAY_OF_WEEK))) {
                                    additme=true;
                                }
                                break;
                            case "每月":
                                if ((!(clicktime.getTime().before(ctime.getTime()))) && (
                                        clicktime.get(Calendar.DAY_OF_MONTH) == ctime.get(Calendar.DAY_OF_MONTH))) {
                                    additme=true;
                                }
                                break;
                            case "每年":
                                if ((!(clicktime.getTime().before(ctime.getTime()))) && (
                                        clicktime.get(Calendar.MONTH) == ctime.get(Calendar.MONTH))&&
                                        (clicktime.get(Calendar.DAY_OF_MONTH)==ctime.get(Calendar.DAY_OF_MONTH))) {
                                    additme=true;
                                }
                            default:
                                break;
                        }
                        if (additme){
                            ToDoListItem item = new ToDoListItem(R.drawable.ic_todolist_ric_click, myCalendar.getTitle(), myCalendar.getContent());
                            scheduleList.add(item);
                        }
                        break;
                    case "生日":

                        break;
                    case "纪念日":

                        break;
                    case "倒数日":

                        break;
                    default:

                        break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        ToDoListAdapter adapter=new ToDoListAdapter(scheduleList);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }


    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        drawerLayout = findViewById(R.id.drawerLayout);
        mainCalendar = findViewById(R.id.calendar_view);
        todayRemark = findViewById(R.id.today_rm);
        recyclerView = findViewById(R.id.recycler_view);
        fabAddList = findViewById(R.id.fab);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;

            default:
        }
        return true;
    }
}
