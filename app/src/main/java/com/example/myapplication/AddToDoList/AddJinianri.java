package com.example.myapplication.AddToDoList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;


public class AddJinianri extends Fragment implements View.OnClickListener {
    private EditText title, content;
    private TextView beginTime, end_text;
    private CheckBox checkBox1, checkBox2, checkBox3;

    private SharedPreferences.Editor editor;

    String remark = "";
    String text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_jinianri, container, false);
        bindView(view);
        editor = getActivity().getSharedPreferences("selectdate", Context.MODE_PRIVATE).edit();
        editor.putString("category", "纪念日");
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                editor.putString("title", editable.toString());
                editor.apply();
            }
        });
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                editor.putString("content", editable.toString());
                editor.apply();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("selectdate", Context.MODE_PRIVATE);
        title.setText(sharedPreferences.getString("title", null));
        content.setText(sharedPreferences.getString("content", null));
        beginTime.setText(sharedPreferences.getString("selectDate", null));
        end_text.setText(text);
        String remark = sharedPreferences.getString("remark", null);
        if (remark.contains("1")) checkBox1.setChecked(true);
        if (remark.contains("2")) checkBox2.setChecked(true);
        initRemark();
    }

    private void bindView(View view) {
        title = view.findViewById(R.id.jinian_title);
        content = view.findViewById(R.id.jinian_content);
        beginTime = view.findViewById(R.id.jinian_time);
        end_text = view.findViewById(R.id.jinian_end_text);
        checkBox1 = view.findViewById(R.id.jinian_30);
        checkBox2 = view.findViewById(R.id.jinian_100);
        checkBox3 = view.findViewById(R.id.jinn_every_year);

        beginTime.setOnClickListener(this);
        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        checkBox3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jinian_time:
                Intent intent = new Intent(getActivity(), WheelAddTime.class);
                startActivity(intent);
                break;
            case R.id.jinian_30:
            case R.id.jinian_100:
            case R.id.jinn_every_year:
                initRemark();
                break;
            default:
                break;
        }
    }

    private void initRemark() {
        text = "将于 " + beginTime.getText().toString() + " 后的";
        if (checkBox1.isChecked()) {
            remark += "1";
            String date[] = beginTime.getText().toString().split(" ");
            text += "\n30天后 " + date[1] + " 提醒";
        }
        if (checkBox2.isChecked()) {
            remark += "2";
            String date[] = beginTime.getText().toString().split(" ");
            text += "\n100天后 " + date[1] + " 提醒";
        }
        if (checkBox3.isChecked()) {
            remark += "3";
            String date[] = beginTime.getText().toString().split("年");
            text += "\n每年 " + date[1] + "提醒";
        }
        editor.putString("remark", remark);
        editor.apply();
        end_text.setText(text);
    }
}
