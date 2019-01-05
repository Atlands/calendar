package com.example.myapplication.AddToDoList;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;


public class AddRicheng extends Fragment {

    private TextInputLayout inputLayout;
    private EditText ricTitle,ricContent;
    private TextView beginTime,rate,endTime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_richeng, container, false);
        initWidget(view);
        inputLayout.setHint("日程");
        ricTitle=inputLayout.getEditText();
        String errorText="日程名不能为空";
        ForegroundColorSpan fgcspan=new ForegroundColorSpan(Color.RED);
        SpannableStringBuilder ssbuilder =new SpannableStringBuilder(errorText);
        if (ricTitle.getText()==null) ricTitle.setError(ssbuilder);
        return view;
    }

    private void initWidget(View view) {
        inputLayout= view.findViewById(R.id.inputlayout);
        ricTitle=view.findViewById(R.id.richeng_title);
        ricContent=view.findViewById(R.id.richeng_content);
        beginTime=view.findViewById(R.id.ric_begin_time);
        rate=view.findViewById(R.id.ric_rate);
        endTime=view.findViewById(R.id.ric_end_time);
    }
}
