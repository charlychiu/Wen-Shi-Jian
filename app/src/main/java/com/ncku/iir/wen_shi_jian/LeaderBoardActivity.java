package com.ncku.iir.wen_shi_jian;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LeaderBoardActivity extends AppCompatActivity {

    TextView firstView;
    TextView secondView;
    TextView thirdView;
    TextView bestView;
    TextView currentView;

    Button againButton;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderbord_page);

        firstView = (TextView)findViewById(R.id.firstView);
        secondView = (TextView)findViewById(R.id.secondView);
        thirdView = (TextView)findViewById(R.id.thirdView);
        bestView = (TextView)findViewById(R.id.bestView);
        currentView = (TextView)findViewById(R.id.currentView);

        firstView.setText("第一名: Loading...");
        secondView.setText("第二名: Loading...");
        thirdView.setText("第三名: Loading...");
        bestView.setText("最好排名 */*");
        currentView.setText("此次排名 */*");

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
                goMain();
            }
        });

        getAllDataFromFirebase();
    }

    private void getAllDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("score");

        Query scoreQuery = myRef.orderByValue();
        scoreQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Integer> scores = new HashMap<>();
//                List<String> scores = new ArrayList<String>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    scores.put(postSnapshot.getKey(), Integer.parseInt(postSnapshot.getValue().toString()));
//                    scores.add(postSnapshot.getValue().toString());
                }
                Log.d("LeaderBoardActivity", String.valueOf(scores.size()));
                Map<String, Integer> hm1 = sortByValue(scores);
                firstView.setText("第一名: " + getMapUserAndScore(hm1, 0));
                secondView.setText("第二名: " + getMapUserAndScore(hm1, 1));
                thirdView.setText("第三名: " + getMapUserAndScore(hm1, 2));
                Log.d("LeaderBoardActivity", getMapUserAndScore(hm1, 0));
                Log.d("LeaderBoardActivity", getMapUserAndScore(hm1, 1));
                Log.d("LeaderBoardActivity", getMapUserAndScore(hm1, 2));

                // Count for ranking
                int totalRankCount = scores.size();
                int userCurrentScore = Global.score;
                int currentRankCount = 0;
                int userCurrentRank = -1;
                LinkedHashMap<String, Integer> newMap = new LinkedHashMap<>(hm1);

                for (Map.Entry<String, Integer> entry : newMap.entrySet()) {
                    String key = entry.getKey();
                    int value = entry.getValue();
                    Log.d("LeaderBoardActivity", "Order from map: " + key + " " + value);
                    currentRankCount += 1;
                    if (userCurrentScore == value) {
                        userCurrentRank = currentRankCount;
                        break;
                    }
                }
                String currentString = String.format("此次排名 %d/%d", userCurrentRank ,totalRankCount);
                currentView.setText(currentString);

                SharedPreferences mPreferences = com.ncku.iir.wen_shi_jian.Global.getPrefInstance();
                String tmpUserName = mPreferences.getString("username", "Not Value");
                currentRankCount = 0;
                userCurrentRank = -1;
                for (Map.Entry<String, Integer> entry : newMap.entrySet()) {
                    String key = entry.getKey();
                    int value = entry.getValue();
                    Log.d("LeaderBoardActivity", "Order from map: " + key + " " + value);
                    currentRankCount += 1;
                    if (tmpUserName.equals(key)) {
                        userCurrentRank = currentRankCount;
                        break;
                    }
                }
                String bestString = String.format("最好排名 %d/%d", userCurrentRank ,totalRankCount);
                bestView.setText(bestString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getMapUserAndScore(Map<String, Integer> hm, int index) {
        return hm.keySet().toArray()[index].toString() + " " + hm.get(hm.keySet().toArray()[index]).toString();
    }

    // function to sort hashmap by values (Decrease)
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public void goQuestionLevel(){
        Intent intent = new Intent(this, QuestionLevelActivity.class);
        intent.putExtra("isContinue", 1);
        startActivity(intent);
    }

    public void goMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
