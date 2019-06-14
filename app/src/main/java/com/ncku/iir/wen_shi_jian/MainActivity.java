package com.ncku.iir.wen_shi_jian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void detection(View view) {
        // Intent intent = new Intent(this, DetectionActivity.class);

        // test question
        Intent intent = new Intent(this, QuestionLevelActivity.class);
        String username = "Shi-Jian";
        intent.putExtra("username", username);
        startActivity(intent);
    }
}
