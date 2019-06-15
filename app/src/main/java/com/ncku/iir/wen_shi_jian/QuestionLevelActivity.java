package com.ncku.iir.wen_shi_jian;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.JsonObject;
import com.ncku.iir.wen_shi_jian.core.Global;
import com.ncku.iir.wen_shi_jian.core.RetrofitRequest;

import java.util.UUID;

public class QuestionLevelActivity extends AppCompatActivity {

    ImageButton simpleImgBtn;
    ImageButton hardImgBtn;
    String username = "Wen-Shi-Jian";

    int isContinue = 0;

    String line_user_uuid;
    String line_user_email = "iir_android@gmail.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_level_page);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        Global.username = username;
        try {
            isContinue = intent.getIntExtra("isContinue", 0);
        }catch (NullPointerException e ){
            isContinue = 0;
        }
        if(isContinue == 0){
            line_user_uuid = UUID.randomUUID().toString();
        }
        else {
            line_user_uuid = intent.getStringExtra("line_user_uuid");
        }

        Log.d("q_level", username);

        Global.q_count = 1;
        Global.maxCorrect = 0;
        Global.score = 0;

        simpleImgBtn = findViewById(R.id.simpleButton);
        simpleImgBtn.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                //easy
                Global.topic_id = 35;
                goQuestionType(35);
            }

        });

        hardImgBtn = findViewById(R.id.hardButton);
        hardImgBtn.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                //hard
                Global.topic_id = 35;
                goQuestionType(36);
            }

        });



    }


    public void goQuestionType(int topicId) {

        // getting user_id from questionnaire server
        String url_uid = "http://140.116.247.161:8888/questionnaire/login_user_line/";
        final JsonObject getUserIDtask = new JsonObject();

        getUserIDtask.addProperty("user_line_uuid", line_user_uuid);
        getUserIDtask.addProperty("user_email", line_user_email);

        RetrofitRequest RequestAPI = RetrofitRequest.getInstance();
        RequestAPI.lineMappingLoginResponse(url_uid, getUserIDtask);

        Intent intent = new Intent(this, QuestionTypeActivity.class);
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
