package com.example.myapplication.AddToDoList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddRicheng extends Fragment implements View.OnClickListener {

    private TextInputLayout inputLayout;
    private EditText ricTitle, ricContent;
    private TextView beginTime, rate, end_text;

    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_richeng, container, false);
        initWidget(view);
        editor = getActivity().getSharedPreferences("selectdate", Context.MODE_PRIVATE).edit();
        editor.putString("category", "日程");
        inputLayout.setHint("日程");
        ricTitle = inputLayout.getEditText();
        String errorText = "日程名不能为空";
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(errorText);
        if (ricTitle.getText() == null) ricTitle.setError(ssbuilder);
        ricTitle.addTextChangedListener(new TextWatcher() {
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
        ricContent.addTextChangedListener(new TextWatcher() {
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
        ricTitle.setText(sharedPreferences.getString("title", null));
        ricContent.setText(sharedPreferences.getString("content", null));
        beginTime.setText(sharedPreferences.getString("selectDate", null));
        rate.setText(sharedPreferences.getString("rate", "一次性活动"));

        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            Date date = sim.parse(beginTime.getText().toString());
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.setTime(date);
            switch (sharedPreferences.getString("rate", "一次性活动")) {
                case "一次性活动":
                    end_text.setText("将于 " + beginTime.getText().toString() + " 提醒");
                    break;
                case "每天":
                    end_text.setText("将于 " + calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + 1 + "月"
                            + calendar.get(Calendar.DAY_OF_MONTH) + "日" + " 后的每天 "
                            + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " 提醒");
                    break;
                case "每周":
                    end_text.setText("将于 " + calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + 1 + "月"
                            + calendar.get(Calendar.DAY_OF_MONTH) + "日" + " 后的 每周" + getWeek(calendar.get(Calendar.DAY_OF_WEEK)) + " "
                            + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " 提醒");
                    break;
                case "每月":
                    end_text.setText("将于 " + calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + 1 + "月"
                            + calendar.get(Calendar.DAY_OF_MONTH) + "日" + " 后的 每月" + calendar.get(Calendar.DAY_OF_MONTH) + "日 "
                            + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " 提醒");
                    break;
                case "每年":
                    end_text.setText("将于 " + calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + 1 + "月"
                            + calendar.get(Calendar.DAY_OF_MONTH) + "日" + " 后的 每年" + calendar.get(Calendar.MONTH) + 1 + "月"
                            + calendar.get(Calendar.DAY_OF_MONTH) + "日 " + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                            + calendar.get(Calendar.MINUTE) + " 提醒");
                    break;
                default:
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String getWeek(int i) {
        switch (i) {
            case 1:
                return "日";
            case 2:
                return "一";
            case 3:
                return "二";
            case 4:
                return "三";
            case 5:
                return "四";
            case 6:
                return "五";
            case 7:
                return "六";
            default:
                return null;
        }
    }

    private void initWidget(View view) {
        inputLayout = view.findViewById(R.id.inputlayout);
        ricTitle = view.findViewById(R.id.richeng_title);
        ricContent = view.findViewById(R.id.richeng_content);
        beginTime = view.findViewById(R.id.ric_begin_time);
        rate = view.findViewById(R.id.ric_rate);
        end_text = view.findViewById(R.id.ric_end_text);

        beginTime.setOnClickListener(this);
        rate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ric_begin_time:
                intent = new Intent(getActivity(), WheelAddTime.class);
                break;
            case R.id.ric_rate:
                intent = new Intent(getActivity(), Rate.class);
                break;
        }
        startActivity(intent);
    }
}
