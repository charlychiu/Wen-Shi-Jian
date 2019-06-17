package com.ncku.iir.wen_shi_jian;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;
import com.ncku.iir.wen_shi_jian.core.Global;
import com.ncku.iir.wen_shi_jian.core.RetrofitRequest;

public class ShijianActivity extends AppCompatActivity {

    ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shi_jian_page);

        imageview = findViewById(R.id.imageView);

        setImage(Global.correct_count);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                goFeedback();
            }
        }, 3000);
    }

    public void goFeedback(){
        Intent intent = new Intent(this, FeedbackActivity.class);
        startActivity(intent);

    }

    public void setImage(int num){
        if (num==5){
            Glide.with(getApplicationContext())
                    .load(R.drawable.best)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(imageview);
        }
        else if (num==4){
            Glide.with(getApplicationContext())
                    .load(R.drawable.second)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(imageview);
        }
        else if (num==3){
            Glide.with(getApplicationContext())
                    .load(R.drawable.sofarsogood)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(imageview);
        }
        else if (num==2){
            Glide.with(getApplicationContext())
                    .load(R.drawable.savesoul)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(imageview);
        }
        else if (num==1){
            Glide.with(getApplicationContext())
                    .load(R.drawable.sj01)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(imageview);
        }
        else if (num==0){
            Glide.with(getApplicationContext())
                    .load(R.drawable.wrong)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(imageview);
        }
    }
}
