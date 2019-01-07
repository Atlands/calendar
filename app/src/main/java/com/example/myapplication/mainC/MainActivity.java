package com.example.myapplication.mainC;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.AddToDoList.AddToDoList;
import com.example.myapplication.R;
import com.necer.calendar.Miui10Calendar;
import com.necer.entity.NDate;
import com.necer.listener.OnCalendarChangedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private Miui10Calendar mainCalendar;
    private TextView toolbarTitle, todayRemark;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddList;
    private int mainYear, mainMonth, mainDay, mainHour, mainMinute;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

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
            }

            @Override
            public void onCalendarStateChanged(boolean isMonthSate) {

            }
        });

        fabAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                try {
                    Calendar selectTime=Calendar.getInstance();
                    Date selectDate = sim.parse(mainYear + "-" + mainMonth + "-" + mainDay+" "
                            +selectTime.get(Calendar.HOUR_OF_DAY)+":"+selectTime.get(Calendar.MINUTE));
                    Intent intent = new Intent(MainActivity.this, AddToDoList.class);
                    intent.putExtra("selectDate",selectDate.toString());
                    startActivity(intent);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
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
