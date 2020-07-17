package com.example.minidouyin.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.minidouyin.R;

public class GetResultActivity extends Activity  {
    private EditText editText;
    public static final String KEY = "result_key";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_result);
        Intent intent =getIntent();
        EditText e=findViewById(R.id.edittext);
        e.setText(String.valueOf(intent.getIntExtra("number",0)));
        initView();
    }

    private void initView() {
        editText = findViewById(R.id.edittext);
        findViewById(R.id.btn_go_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(KEY, input);
                if (input.isEmpty()) {
                    setResult(RESULT_CANCELED, intent);
                } else {
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });
    }
}
