package com.ncku.iir.wen_shi_jian;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ncku.iir.wen_shi_jian.core.Global;

import java.util.HashMap;

public class FeedbackActivity extends AppCompatActivity {

    Button againButton;
    Button nextButton;
    TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_page);

        Log.d("feed", "user " + Global.line_user_uuid);

        scoreView = findViewById(R.id.scoreView);
        scoreView.setText("分數 : " + String.valueOf(Global.score));

        // TODO: Step 7 save score to DB
        SharedPreferences mPreferences = com.ncku.iir.wen_shi_jian.Global.getPrefInstance();
        String tmpUserName = mPreferences.getString("username", "Not Value");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("score");
        // Check current user this time score is higher than past
        // If not, do not update the score
        // Otherwise, update the score to DB
        Query scoreQuery = myRef.orderByValue();
        scoreQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Integer> scores = new HashMap<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    scores.put(postSnapshot.getKey(), Integer.parseInt(postSnapshot.getValue().toString()));
                }

                int score = scores.containsKey(tmpUserName) ? scores.get(tmpUserName) : -1;
                if (score != -1) {
                    if (Global.score > score) {
                        myRef.child(tmpUserName).setValue(Global.score);
                    }
                } else {
                    myRef.child(tmpUserName).setValue(Global.score);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




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

    public void goQuestionLevel() {
        Intent intent = new Intent(this, QuestionLevelActivity.class);
        intent.putExtra("isContinue", 1);
        startActivity(intent);
    }

    public void goLeaderBroad() {
        Intent intent = new Intent(this, LeaderBoardActivity.class);
        startActivity(intent);
    }
}
