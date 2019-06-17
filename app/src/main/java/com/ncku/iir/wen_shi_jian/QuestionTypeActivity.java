package com.ncku.iir.wen_shi_jian;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.ncku.iir.wen_shi_jian.core.Global;
import com.ncku.iir.wen_shi_jian.core.RetrofitRequest;

import java.util.UUID;

public class QuestionTypeActivity extends AppCompatActivity {

    String username = "Wen-Shi-Jian";

    // default easy
    int topicId = 35;
    int countdown = 10;
    int q_count = 1;
    int correct_count = 0;


    //layoout
    TextView timeView;
    TextView quesNumView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_type_page);


        username = Global.username;
        topicId = Global.topic_id;
        q_count = Global.q_count;
        correct_count = Global.correct_count;


        Log.d("questionTypeActivity", username);
        Log.d("questionTypeActivity", String.valueOf(topicId));
        Log.d("questionTypeActivity", String.valueOf(q_count));
        Log.d("questionTypeActivity", String.valueOf(correct_count));

        if(topicId == 35){
            // easy
            countdown = 10;

        }
        else {
            // hard
            countdown = 8;
        }

        timeView = findViewById(R.id.timeView);
        timeView.setText(String.valueOf(countdown)+"秒");

        quesNumView = findViewById(R.id.quesNumView);
        quesNumView.setText("第"+String.valueOf(q_count)+"題");


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // yourMethod();
                goQuestion();
            }
        }, 1000);



    }

    public void goQuestion(){



        Intent intent = new Intent(this, QuestionActivity.class);
        startActivity(intent);
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
