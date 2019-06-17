package com.ncku.iir.wen_shi_jian;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserActivity extends AppCompatActivity {

    ImageView userView;
    EditText nameText;
    EditText ageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String imageUri = extras.getString("picture_uri");
            String age = extras.getString("age");
            String gender = extras.getString("gender");

            userView = (ImageView) findViewById(R.id.userView);
            userView.setImageURI(Uri.parse(imageUri));

            nameText = (EditText) findViewById(R.id.nameText);
            nameText.setText("小明");

            if (age != null) {
                ageText = (EditText) findViewById(R.id.ageText);
                ageText.setText(String.valueOf(Math.round(Float.parseFloat(age))));
            }

            if (gender != null) {
                if (gender.equals("male")) {
                    nameText.setText("HowHow");
                }
                if (gender.equals("female")) {
                    nameText.setText("A FU");
                }
            }
        }
    }

    public void startGame(View view) {
        // TODO: step 6 save user data to DB
        String userInputName = nameText.getText().toString();
        String userInputAge = ageText.getText().toString();

        //TODO: step 6 save username & age to sharedPreferences
        SharedPreferences mPreferences = Global.getPrefInstance();
        mPreferences.edit().putString("username", userInputName).apply();
        mPreferences.edit().putString("age", userInputAge).apply();

        Intent intent = new Intent(this, QuestionLevelActivity.class);
        intent.putExtra("username", userInputName);
        startActivity(intent);

    }
}
