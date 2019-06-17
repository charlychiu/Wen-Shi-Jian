package com.ncku.iir.wen_shi_jian;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ncku.iir.wen_shi_jian.core.Global;

public class FeedbackActivity extends AppCompatActivity {

    Button againButton;
    Button nextButton;
    TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_page);

        Log.d("feed", "user "+ Global.line_user_uuid);

        scoreView = findViewById(R.id.scoreView);
        scoreView.setText("分數 : "+String.valueOf(Global.score));

        // TODO: Step 7 save score to DB
        SharedPreferences mPreferences = com.ncku.iir.wen_shi_jian.Global.getPrefInstance();
        String tmpUserName = mPreferences.getString("username", "Not Value");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("score");
        myRef.child(tmpUserName).setValue(Global.score);

        againButton = findViewById(R.id.againButton);
        againButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Continue
                goQuestionLevel();
            }
        });
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLeaderBroad();
            }
        });


    }

    public void goQuestionLevel(){
        Intent intent = new Intent(this, QuestionLevelActivity.class);
        intent.putExtra("isContinue", 1);
        startActivity(intent);
    }

    public void goLeaderBroad(){
        Intent intent = new Intent(this, LeaderBoardActivity.class);
        startActivity(intent);
    }
}
