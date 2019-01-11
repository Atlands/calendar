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

public class AddShengri extends Fragment implements View.OnClickListener {
    private EditText title, content;
    private TextView beginTime, end_text;
    private CheckBox checkBox1, checkBox2, checkBox3;

    private SharedPreferences.Editor editor;
//    private String rate="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_shengri, container, false);
        bindView();
        editor = getActivity().getSharedPreferences("selectdate", Context.MODE_PRIVATE).edit();
        editor.putString("category", "生日");
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
    }

    private void bindView() {
        title = getView().findViewById(R.id.shengri_title);
        content = getView().findViewById(R.id.shengri_contect);
        beginTime = getView().findViewById(R.id.shengri_time);
        end_text = getView().findViewById(R.id.shengri_end_text);
        checkBox1 = getView().findViewById(R.id.shengri_30);
        checkBox2 = getView().findViewById(R.id.shengri_100);
        checkBox3 = getView().findViewById(R.id.shengri_year);

        beginTime.setOnClickListener(this);
        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        checkBox3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shengri_time:
                Intent intent = new Intent(getActivity(), WheelAddTime.class);
                startActivity(intent);
                break;
            case R.id.shengri_30:
                break;
            case R.id.shengri_100:
                break;
            case R.id.shengri_year:
                break;
            default:
                break;
        }
        String rate="";
        if (checkBox1.isChecked())
            rate+="1";
        if (checkBox2.isChecked())
            rate+="2";
        if (checkBox3.isChecked())
            rate+="3";
        editor.putString("rate",rate);
    }
}
