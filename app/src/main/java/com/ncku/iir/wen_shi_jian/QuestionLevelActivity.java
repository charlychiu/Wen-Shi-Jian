package com.ncku.iir.wen_shi_jian;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class QuestionLevelActivity extends AppCompatActivity {

    ImageButton simpleImgBtn;
    ImageButton hardImgBtn;
    String username = "Wen-Shi-Jian";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_level_page);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        Log.d("q_level", username);

        simpleImgBtn = findViewById(R.id.simpleButton);
        simpleImgBtn.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                //easy
                goQuestionType(35);
            }

        });

        hardImgBtn = findViewById(R.id.hardButton);
        hardImgBtn.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v){
                //hard
                goQuestionType(36);
            }

        });



    }


    public void goQuestionType(int topicId) {
        Intent intent = new Intent(this, QuestionTypeActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("topicId", topicId);
        intent.putExtra("q_count", 1);
        intent.putExtra("maxCorrect", 0);
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
