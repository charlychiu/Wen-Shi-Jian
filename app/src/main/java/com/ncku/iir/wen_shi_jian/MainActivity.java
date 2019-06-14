package com.ncku.iir.wen_shi_jian;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);

        //Init SharedPreferences
        // pref usage
        Global.initPrefInstance(getSharedPreferences("user", MODE_PRIVATE));

//        SharedPreferences mPreferences = Global.getPrefInstance();
//        String tmpUserName = mPreferences.getString("username", "Not Value");
//        Log.d("MainActivity", tmpUserName);

    }

    // TODO: step 1. click 'start' button
    public void detection(View view) {
        Intent intent = new Intent(this, DetectionActivity.class);
        startActivity(intent);
    }
}
