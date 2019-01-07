package com.example.myapplication.AddToDoList;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.Calendar;
import java.util.UUID;

public class AddToDoList extends AppCompatActivity implements View.OnClickListener {

    private TextView fg1, fg2, fg3, fg4;
    private FrameLayout addContent;
    private AddRicheng fragment1 = new AddRicheng();
    private AddShengri fragment2 = new AddShengri();
    private AddJinianri fragment3 = new AddJinianri();
    private AddDaoshu fragment4 = new AddDaoshu();

    //private Add
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do_list);
        bindView();
        fg1.performClick();
    }

    public String getSelectDate() {
        Intent intent = getIntent();
        return intent.getStringExtra("selectDate");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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

    private void hideAllFragment(FragmentTransaction fTransaction) {
        if (fragment1 != null) fTransaction.hide(fragment1);
        if (fragment2 != null) fTransaction.hide(fragment2);
        if (fragment3 != null) fTransaction.hide(fragment3);
        if (fragment4 != null) fTransaction.hide(fragment4);
    }

    private void bindView() {
        fg1 = findViewById(R.id.richeng);
        fg2 = findViewById(R.id.shengri);
        fg3 = findViewById(R.id.jinian);
        fg4 = findViewById(R.id.daoshu);
        addContent = findViewById(R.id.add_view_content);

        fg1.setOnClickListener(this);
        fg2.setOnClickListener(this);
        fg3.setOnClickListener(this);
        fg4.setOnClickListener(this);
    }
}
