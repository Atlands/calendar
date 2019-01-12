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

public class AddDaoshu extends Fragment implements View.OnClickListener {
    private EditText title, content;
    private TextView beginTime, end_text;

    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_daoshu, container, false);
        bindView(view);
        editor = getActivity().getSharedPreferences("selectdate", Context.MODE_PRIVATE).edit();
        editor.putString("category", "倒数日");
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
        end_text.setText(beginTime.getText().toString()+" 前的每天于首页做出倒数展示，并于此时间通知栏提醒");
    }
    private void bindView(View view) {
        title = view.findViewById(R.id.daoshu_title);
        content = view.findViewById(R.id.doashu_content);
        beginTime = view.findViewById(R.id.daoshu_time);
        end_text = view.findViewById(R.id.daoshu_end_text);

        beginTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.daoshu_time:
                Intent intent = new Intent(getActivity(), WheelAddTime.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
