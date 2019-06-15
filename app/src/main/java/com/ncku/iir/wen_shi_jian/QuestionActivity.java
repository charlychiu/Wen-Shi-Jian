package com.ncku.iir.wen_shi_jian;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.ncku.iir.wen_shi_jian.core.Global;
import com.ncku.iir.wen_shi_jian.core.RetrofitRequest;

public class QuestionActivity extends AppCompatActivity {


    String username = "Wen-Shi-Jian";

    // default easy
    int topicId = 35;
    int countdown = 10;
    int q_count = 1;
    int maxCorrect = 0;

    String question;
    String A;
    String B;
    String C;
    String D;
    String answer;
    int remainTime = 0;

    //layout
    TextView timeView;
    TextView questView;
    Button buttonA;
    Button buttonB;
    Button buttonC;
    Button buttonD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_page);

//        Intent intent = getIntent();
//        username = intent.getStringExtra("username");
//        topicId = intent.getIntExtra("topicId", 35);
//        q_count = intent.getIntExtra("q_count", 1);
//        maxCorrect = intent.getIntExtra("maxCorrect", 0);

        username = Global.username;
        topicId = Global.topic_id;
        q_count = Global.q_count;
        maxCorrect = Global.maxCorrect;
        question = Global.question;
        A = Global.A;
        B = Global.B;
        C = Global.C;
        D = Global.D;
        answer = Global.answer;

        Global.q_count += 1;
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
        timeView.setText(String.valueOf(10));

        questView = findViewById(R.id.questView);
        questView.setText(question);

        buttonA = findViewById(R.id.buttonA);
        buttonA.setText(A);
        buttonA.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnswer("A");
                if(answer.equals("A")||answer.equals("a")){
                    buttonA.setBackgroundColor(Color.GREEN);
                }
                else {
                    buttonA.setBackgroundColor(Color.RED);
                    switch (answer){
                        case "A":
                            buttonA.setBackgroundColor(Color.GREEN);
                            break;
                        case "B":
                            buttonB.setBackgroundColor(Color.GREEN);
                            break;
                        case "C":
                            buttonC.setBackgroundColor(Color.GREEN);
                            break;
                        case "D":
                            buttonD.setBackgroundColor(Color.GREEN);
                        default:
                            Log.d("Question", "no match Answer");
                    }
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                        if(q_count > 5){
                            goFeedback();
                        }
                        else{
                            // use answerRecord_uuid to get questions
                            String url_q = "http://140.116.247.161:8888/questionnaire/new_selectionQuestion/";
                            final JsonObject getQuestiontask = new JsonObject();
                            getQuestiontask.addProperty("state","select");
                            getQuestiontask.addProperty("answerRecord_uuid", Global.answerRecord_uuid);
                            RetrofitRequest RequestAPI  = RetrofitRequest.getInstance();
                            RequestAPI.questionSelectResponse(url_q, getQuestiontask);
                            //jump activity
                            goQuestionType();
                        }
                    }
                }, 1000);
            }
        });

        buttonB = findViewById(R.id.buttonB);
        buttonB.setText(B);
        buttonB.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnswer("B");
                if(answer.equals("B")||answer.equals("b")){
                    buttonB.setBackgroundColor(Color.GREEN);
                }
                else {
                    buttonB.setBackgroundColor(Color.RED);
                    switch (answer){
                        case "A":
                            buttonA.setBackgroundColor(Color.GREEN);
                            break;
                        case "B":
                            buttonB.setBackgroundColor(Color.GREEN);
                            break;
                        case "C":
                            buttonC.setBackgroundColor(Color.GREEN);
                            break;
                        case "D":
                            buttonD.setBackgroundColor(Color.GREEN);
                        default:
                            Log.d("Question", "no match Answer");

                    }
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                        if(q_count > 5){
                            goFeedback();
                        }
                        else{
                            // use answerRecord_uuid to get questions
                            String url_q = "http://140.116.247.161:8888/questionnaire/new_selectionQuestion/";
                            final JsonObject getQuestiontask = new JsonObject();
                            getQuestiontask.addProperty("state","select");
                            getQuestiontask.addProperty("answerRecord_uuid", Global.answerRecord_uuid);
                            RetrofitRequest RequestAPI  = RetrofitRequest.getInstance();
                            RequestAPI.questionSelectResponse(url_q, getQuestiontask);
                            //jump activity
                            goQuestionType();
                        }
                    }
                }, 1000);
            }
        });

        buttonC = findViewById(R.id.buttonC);
        buttonC.setText(C);
        buttonC.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnswer("C");
                if(answer.equals("C")||answer.equals("c")){
                    buttonC.setBackgroundColor(Color.GREEN);
                }
                else {
                    buttonC.setBackgroundColor(Color.RED);
                    switch (answer){
                        case "A":
                            buttonA.setBackgroundColor(Color.GREEN);
                            break;
                        case "B":
                            buttonB.setBackgroundColor(Color.GREEN);
                            break;
                        case "C":
                            buttonC.setBackgroundColor(Color.GREEN);
                            break;
                        case "D":
                            buttonD.setBackgroundColor(Color.GREEN);
                        default:
                            Log.d("Question", "no match Answer");

                    }
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                        if(q_count > 5){
                            goFeedback();
                        }
                        else{
                            // use answerRecord_uuid to get questions
                            String url_q = "http://140.116.247.161:8888/questionnaire/new_selectionQuestion/";
                            final JsonObject getQuestiontask = new JsonObject();
                            getQuestiontask.addProperty("state","select");
                            getQuestiontask.addProperty("answerRecord_uuid", Global.answerRecord_uuid);
                            RetrofitRequest RequestAPI  = RetrofitRequest.getInstance();
                            RequestAPI.questionSelectResponse(url_q, getQuestiontask);
                            //jump activity
                            goQuestionType();
                        }
                    }
                }, 1000);
            }
        });

        buttonD = findViewById(R.id.buttonD);
        buttonD.setText(D);
        buttonD.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnswer("D");
                if(answer.equals("D")||answer.equals("d")){
                    buttonD.setBackgroundColor(Color.GREEN);
                }
                else {
                    buttonD.setBackgroundColor(Color.RED);
                    switch (answer){
                        case "A":
                            buttonA.setBackgroundColor(Color.GREEN);
                            break;
                        case "B":
                            buttonB.setBackgroundColor(Color.GREEN);
                            break;
                        case "C":
                            buttonC.setBackgroundColor(Color.GREEN);
                            break;
                        case "D":
                            buttonD.setBackgroundColor(Color.GREEN);
                        default:
                            Log.d("Question", "no match Answer");

                    }
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                        if(q_count > 5){
                            goFeedback();
                        }
                        else{
                            // use answerRecord_uuid to get questions
                            String url_q = "http://140.116.247.161:8888/questionnaire/new_selectionQuestion/";
                            final JsonObject getQuestiontask = new JsonObject();
                            getQuestiontask.addProperty("state","select");
                            getQuestiontask.addProperty("answerRecord_uuid", Global.answerRecord_uuid);
                            RetrofitRequest RequestAPI  = RetrofitRequest.getInstance();
                            RequestAPI.questionSelectResponse(url_q, getQuestiontask);
                            //jump activity
                            goQuestionType();
                        }
                    }
                }, 1000);
            }
        });




        CountDownTimer countDownTimer = new CountDownTimer(countdown*1000+500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeView.setText(String.valueOf(millisUntilFinished/1000));
                remainTime = (int)millisUntilFinished/1000;
            }

            @Override
            public void onFinish() {
                timeView.setText(String.valueOf(0));
            }
        }.start();

    }

    public void goFeedback(){
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);

    }

    public  void goQuestionType(){
        Intent intent = new Intent(this, QuestionTypeActivity.class);
        startActivity(intent);
    }

    public void handleAnswer(String user_ans){
        // use answerRecord_uuid to get questions
        String url_q = "http://140.116.247.161:8888/questionnaire/new_selectionQuestion/";
        final JsonObject getAnswertask = new JsonObject();
        getAnswertask.addProperty("state","update");
        getAnswertask.addProperty("answerRecord_uuid", Global.answerRecord_uuid);
        getAnswertask.addProperty("userSelected",user_ans);

        RetrofitRequest RequestAPI  = RetrofitRequest.getInstance();
        RequestAPI.answerCorrectResponse(url_q, getAnswertask);
    }
}
