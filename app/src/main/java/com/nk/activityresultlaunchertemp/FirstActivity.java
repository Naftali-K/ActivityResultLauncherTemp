package com.nk.activityresultlaunchertemp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FirstActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;

    private EditText input_et;
    private Button submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        setReferences();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("name", input_et.getText().toString());
                setResult(REQUEST_CODE, intent);

                finish();
            }
        });
    }

    private void setReferences(){
        input_et = findViewById(R.id.input_et);
        submit_btn = findViewById(R.id.submit_btn);
    }
}