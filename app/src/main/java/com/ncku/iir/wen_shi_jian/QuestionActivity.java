package com.ncku.iir.wen_shi_jian;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class QuestionActivity extends AppCompatActivity {

    String username = "Wen-Shi-Jian";

    // default easy
    int topicId = 35;
    int countdown = 10;
    int q_count = 1;
    int maxCorrect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_type_page);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        topicId = intent.getIntExtra("topicId", 35);
        q_count = intent.getIntExtra("q_count", 1);
        maxCorrect = intent.getIntExtra("maxCorrect", 0);

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



    }


    // Save the activity state when it's going to stop.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    // Recover the saved state when the activity is recreated.
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }


}
