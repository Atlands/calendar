package com.example.myapplication.AddToDoList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.example.myapplication.R;
import java.text.SimpleDateFormat;


public class AddRicheng extends Fragment implements View.OnClickListener {

    private TextInputLayout inputLayout;
    private EditText ricTitle, ricContent;
    private TextView beginTime, rate, end_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_richeng, container, false);
        initWidget(view);
        inputLayout.setHint("日程");
        ricTitle = inputLayout.getEditText();
        String errorText = "日程名不能为空";
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(errorText);
        if (ricTitle.getText() == null) ricTitle.setError(ssbuilder);
        beginTime.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SimpleDateFormat sim=new SimpleDateFormat("yyyy年MM月dd日 hh:mm");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("selectdate", Context.MODE_PRIVATE);
        beginTime.setText(sharedPreferences.getString("selectDate",""));
    }

    private void initWidget(View view) {
        inputLayout = view.findViewById(R.id.inputlayout);
        ricTitle = view.findViewById(R.id.richeng_title);
        ricContent = view.findViewById(R.id.richeng_content);
        beginTime = view.findViewById(R.id.ric_begin_time);
        rate = view.findViewById(R.id.ric_rate);
        end_text = view.findViewById(R.id.ric_end_text);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ric_begin_time:
                Intent intent = new Intent(getActivity(), WheelAddTime.class);
                startActivity(intent);
                break;
        }
    }
}
