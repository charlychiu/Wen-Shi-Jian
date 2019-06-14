package com.ncku.iir.wen_shi_jian;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {


    String username = "Wen-Shi-Jian";

    // default easy
    int topicId = 35;
    int countdown = 10;
    int q_count = 1;
    int maxCorrect = 0;

    //layout
    TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_page);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        topicId = intent.getIntExtra("topicId", 35);
        q_count = intent.getIntExtra("q_count", 1);
        maxCorrect = intent.getIntExtra("maxCorrect", 0);

        q_count += 1;

        Log.d("questionActivity", username);
        Log.d("questionActivity", String.valueOf(topicId));
        Log.d("questionActivity", String.valueOf(q_count));
        Log.d("questionActivity", String.valueOf(maxCorrect));

        if(topicId == 35){
            // easy
            countdown = 10;

        }
        else {
            // hard
            countdown = 8;
        }

        timeView = findViewById(R.id.timeView);


        CountDownTimer countDownTimer = new CountDownTimer(countdown*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeView.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                timeView.setText(String.valueOf(0));
            }
        }.start();

    }


}
