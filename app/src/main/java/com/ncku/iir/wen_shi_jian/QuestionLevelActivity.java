package com.ncku.iir.wen_shi_jian;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.JsonObject;
import com.ncku.iir.wen_shi_jian.core.Global;
import com.ncku.iir.wen_shi_jian.core.RetrofitRequest;
import com.ncku.iir.wen_shi_jian.core.Sound;

import java.util.UUID;

public class QuestionLevelActivity extends AppCompatActivity {

    ImageButton simpleImgBtn;
    ImageButton hardImgBtn;
    String username = "Wen-Shi-Jian";

    int isContinue = 0;

    String line_user_uuid;
    String line_user_email = "iir_android@gmail.com";

    // set music
    Sound sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_level_page);

        SharedPreferences mPreferences = com.ncku.iir.wen_shi_jian.Global.getPrefInstance();
        username = mPreferences.getString("username", "Wen-Shi-Jian");// intent.getStringExtra("username");
        Global.username = username;

        Intent intent = getIntent();
        try {
            isContinue = intent.getIntExtra("isContinue", 0);
        }catch (NullPointerException e ){
            isContinue = 0;
        }
        if(isContinue == 0){
            line_user_uuid = UUID.randomUUID().toString();
            Global.line_user_uuid = line_user_uuid;
        }
        else {
            line_user_uuid = Global.line_user_uuid;
        }

        // music play
        sound = new Sound(getBaseContext());
        sound.changeAndPlayMusic("budda");

        Log.d("q_level", username);
        Log.d("q_level", String.valueOf(isContinue));
        Log.d("q_level", "user "+ Global.line_user_uuid);

        Global.q_count = 1;
        Global.correct_count = 0;
        Global.score = 0;

        simpleImgBtn = findViewById(R.id.simpleButton);
        simpleImgBtn.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                //easy
                sound.recyle();
                Global.topic_id = 35;
                goQuestionType(35);
            }

        });

        hardImgBtn = findViewById(R.id.hardButton);
        hardImgBtn.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                //hard
                sound.recyle();
                Global.topic_id = 36;
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
