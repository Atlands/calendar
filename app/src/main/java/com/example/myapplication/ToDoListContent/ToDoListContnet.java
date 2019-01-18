package com.example.myapplication.ToDoListContent;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.AddToDoList.AddToDoList;
import com.example.myapplication.R;
import com.example.myapplication.db.MyCalendar;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.List;

public class ToDoListContnet extends AppCompatActivity {
    private CollapsingToolbarLayout collToobar;
    private Toolbar toolbar;
    private TextView category,time,content,edit,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_contnet);
        init();
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        final Intent intent=getIntent();
        final int id=intent.getIntExtra("id",0);
        final MyCalendar myCalendar=LitePal.find(MyCalendar.class,id);
        category.setText(myCalendar.getCategory());
        collToobar.setTitle(myCalendar.getTitle());
        SharedPreferences sharedPreferences=getSharedPreferences("selectdate",MODE_PRIVATE);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String s1[]=sharedPreferences.getString("selectDate",null).split(" ");
        String s2[]=myCalendar.getDate().split(" ");
        String s=s1[0]+s2[1];
        time.setText(s);
        content.setText(myCalendar.getContent());
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=getSharedPreferences("selectdate",MODE_PRIVATE).edit();
                editor.putString("category",myCalendar.getCategory());
                editor.putString("selectDate",myCalendar.getDate());
                editor.putString("title",myCalendar.getTitle());
                editor.putString("content",myCalendar.getContent());
                editor.putString("rate",myCalendar.getRate());
                editor.putString("remark",myCalendar.getRemark());
                Intent intent1=new Intent(ToDoListContnet.this,AddToDoList.class);
                startActivity(intent1);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ToDoListContnet.this);
                builder.setTitle("删除日程");
                builder.setMessage("此条日程的所有相关日程都将被删除，您确认删除吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LitePal.delete(MyCalendar.class,id);
                        finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
        }
        return true;
    }
    private void init() {
        collToobar=findViewById(R.id.content_page_coll_toolbar);
        toolbar=findViewById(R.id.content_page_tollbar);
        category=findViewById(R.id.content_page_category);
        time=findViewById(R.id.content_page_time);
        content=findViewById(R.id.content_page_content);
        edit=findViewById(R.id.content_page_edit);
        delete=findViewById(R.id.content_page_delete);
    }
}
