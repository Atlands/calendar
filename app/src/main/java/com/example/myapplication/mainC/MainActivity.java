package com.example.myapplication.mainC;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
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
    private FloatingActionButton fabAddList, today;
    private int mainYear, mainMonth, mainDay, mainHour, mainMinute;

    private List<ToDoListItem> scheduleList = new ArrayList<>();
    private SimpleDateFormat sim = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

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

                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onCalendarStateChanged(boolean isMonthSate) {

            }
        });

        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainCalendar.toToday();
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
        recyclerView.setOnClickListener(new View.OnClickListener() {
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
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);
    }

    private void initSchedule() {
        scheduleList.clear();
        //日历点击时间
        Calendar clicktime = Calendar.getInstance();
        clicktime.clear();
        clicktime.set(mainYear, mainMonth - 1, mainDay);
        //现在时间
        Calendar nowtime = Calendar.getInstance();
        if (clicktime.get(Calendar.YEAR) == nowtime.get(Calendar.YEAR)
                && clicktime.get(Calendar.MONTH) == nowtime.get(Calendar.MONTH)
                && clicktime.get(Calendar.DAY_OF_MONTH) == nowtime.get(Calendar.DAY_OF_MONTH)) {
            today.setVisibility(View.GONE);
        } else {
            today.setVisibility(View.VISIBLE);
        }

        List<MyCalendar> sehedules = LitePal.findAll(MyCalendar.class);
        for (MyCalendar myCalendar : sehedules) {
            try {
                //数据库日程时间
                Calendar ctime = Calendar.getInstance();
                ctime.clear();
                SimpleDateFormat sim = new SimpleDateFormat("yyyy年MM月dd日");
                String s[] = myCalendar.getDate().split(" ");
                ctime.setTime(sim.parse(s[0]));

                boolean additme = false;
                String title = null;
                int image = 0;
                switch (myCalendar.getCategory()) {
                    case "日程":
                        switch (myCalendar.getRate()) {
                            case "一次性活动":
                                if (clicktime.getTime().equals(ctime.getTime())) {
                                    additme = true;
                                }
                                break;
                            case "每天":
                                if (!(clicktime.getTime().before(ctime.getTime()))) {
                                    additme = true;
                                }
                                break;
                            case "每周":
                                if ((!(clicktime.getTime().before(ctime.getTime()))) && (
                                        clicktime.get(Calendar.DAY_OF_WEEK) == ctime.get(Calendar.DAY_OF_WEEK))) {
                                    additme = true;
                                }
                                break;
                            case "每月":
                                if ((!(clicktime.getTime().before(ctime.getTime()))) && (
                                        clicktime.get(Calendar.DAY_OF_MONTH) == ctime.get(Calendar.DAY_OF_MONTH))) {
                                    additme = true;
                                }
                                break;
                            case "每年":
                                if ((!(clicktime.getTime().before(ctime.getTime())))
                                        && (clicktime.get(Calendar.MONTH) == ctime.get(Calendar.MONTH))
                                        && (clicktime.get(Calendar.DAY_OF_MONTH) == ctime.get(Calendar.DAY_OF_MONTH))) {
                                    additme = true;
                                }
                            default:
                                break;
                        }
                        image = R.drawable.ic_todolist_ric_click;
                        title = myCalendar.getTitle();
                        break;
                    case "生日":
                    case "纪念日":
                        if (myCalendar.getCategory().equals("生日")) {
                            image = R.drawable.ic_todolist_shengri_click;
                        } else {
                            image = R.drawable.ic_todolist_jinian_click;
                        }
                        String remark[] = myCalendar.getRemark().split("");
                        for (String i : remark) {
                            Calendar c = Calendar.getInstance();
                            c.setTime(sim.parse(s[0]));
                            switch (i) {
                                case "1":
                                    c.add(Calendar.DATE, 30);
                                    if (c.getTime().equals(clicktime.getTime())) {
                                        additme = true;
                                        title = myCalendar.getTitle() + "30天" + myCalendar.getCategory();
                                    }
                                    break;
                                case "2":
                                    c.add(Calendar.DATE, 100);
                                    if (c.getTime().equals(clicktime.getTime())) {
                                        additme = true;
                                        title = myCalendar.getTitle() + "100天" + myCalendar.getCategory();
                                    }
                                    break;
                                case "3":
                                    if ((clicktime.getTimeInMillis() >= ctime.getTimeInMillis()) &&
                                            (clicktime.get(Calendar.MONTH) == ctime.get(Calendar.MONTH)) &&
                                            (clicktime.get(Calendar.DAY_OF_MONTH) == ctime.get(Calendar.DAY_OF_MONTH))) {
                                        additme = true;
                                        title = myCalendar.getTitle() + (clicktime.get(Calendar.YEAR) - ctime.get(Calendar.YEAR)) + "周年" + myCalendar.getCategory();
                                    }
                                    break;
                                default:
                                    break;
                            }
                            if (additme) break;
                        }
                        break;
                    case "倒数日":
                        if ((ctime.getTimeInMillis() >= nowtime.getTimeInMillis() && clicktime.getTimeInMillis() <= ctime.getTimeInMillis())
                                || clicktime.getTimeInMillis() == ctime.getTimeInMillis()) {
                            title = myCalendar.getTitle() + "(" + (ctime.getTimeInMillis() - clicktime.getTimeInMillis()) / (24 * 60 * 60 * 1000) + "天后)";
                            additme = true;
                        }
                        break;
                    default:
                        break;
                }
                if (additme) {
                    ToDoListItem item = new ToDoListItem(myCalendar.getId(), image, title, myCalendar.getContent());
                    scheduleList.add(item);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        ToDoListAdapter adapter = new ToDoListAdapter(scheduleList);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initSchedule();
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    };

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);
        drawerLayout = findViewById(R.id.drawerLayout);
        mainCalendar = findViewById(R.id.calendar_view);
        todayRemark = findViewById(R.id.today_rm);
        recyclerView = findViewById(R.id.recycler_view);
        fabAddList = findViewById(R.id.fab);
        today = findViewById(R.id.today);
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
