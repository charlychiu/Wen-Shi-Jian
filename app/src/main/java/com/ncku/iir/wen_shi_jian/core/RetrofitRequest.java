package com.ncku.iir.wen_shi_jian.core;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.google.gson.JsonObject;
import com.ncku.iir.wen_shi_jian.QuestionLevelActivity;
import com.ncku.iir.wen_shi_jian.QuestionTypeActivity;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ncku.iir.wen_shi_jian.core.Global.answerRecord_uuid;
import static com.ncku.iir.wen_shi_jian.core.Global.startQuestionnaire;

public class RetrofitRequest{
    // 以Singleton模式建立
    public static RetrofitRequest mInstance = new RetrofitRequest();

    //declare
    RetrofitInterface RetrofitInterface;

    public RetrofitRequest() {
        // 設置baseUrl即要連的網站，addConverterFactory用Gson作為資料處理Converter ->轉JsonObject 不是JSONObject
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://140.116.247.161:8888/questionnaire/") //因為parameter是url, 可ignore
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    public static RetrofitRequest getInstance() {
        if (mInstance == null)
            mInstance = new RetrofitRequest();
        return mInstance;
    }

    public RetrofitInterface getAPI_interface() {
        return RetrofitInterface;
    }

    // for get user_id from from Health Education (questionnaire server)
    public void lineMappingLoginResponse(String url, JsonObject bodyContent) {
        String real_url = "http://140.116.247.161:8888/questionnaire/login_user_line/";
        // 2. 透過RetrofitManager取得連線基底
        RetrofitInterface = RetrofitRequest.getInstance().getAPI_interface();

        // 3. 建立連線的Call，此處設置call為interface
//        Call<JsonObject> call = getAPI_interface().User_Name(url, "Bearer"+" "+"m9tss6hDwjvqDkM2sVmwNfGd2STHrRwHQhEC9pLr");
        Call<JsonObject> call = RetrofitInterface.lineMappingLoginResponse(real_url, bodyContent);
        Log.d("current step", "initialize call for line mapping login");

        // 4. async 執行call
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                // 連線成功
                JsonObject response_object = response.body();
                Log.d("response string", response.toString());
                Log.d("response msg", response.message());
                Log.d("response success", String.valueOf(response.isSuccessful()));
//                Log.d("response body string", response.body().toString() );
                if (response.isSuccessful()) {
                    Log.d("response body string", String.valueOf(response.body().isJsonNull()));
                    Log.d("response state", response_object.get("state").getAsString());
                    Log.d("response user_id", response_object.get("user_id").getAsString());
                    Global.HE_user_id = response_object.get("user_id").getAsString();
                    Log.d("queried user_id", Global.HE_user_id);

                    // start to exam
                    // use user_id from questionnaire server and topic_id(subject) to get answerRecord_uuid to start exam
                    String url_recordid = "http://140.116.247.161:8888/questionnaire/startQuestion/";
                    final JsonObject getRecordIDtask = new JsonObject();
                    getRecordIDtask.addProperty("topic_id", Global.topic_id);
                    getRecordIDtask.addProperty("user_id", Global.HE_user_id);
                    questionStartResponse(url_recordid, getRecordIDtask);

                } else {
                    checkRequestContent(call.request());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // 連線失敗
                Log.d("response fail", "unsuccessful at store data");
                checkRequestContent(call.request());
            }
        });
    }
    // ------------------------------------------------------------------------------

    // for get answerRecord_uuid (start this session) from Health Education by user_id and topic_id
    public void questionStartResponse(String url,JsonObject bodyContent) {
        // 2. 透過RetrofitManager取得連線基底
        RetrofitInterface = RetrofitRequest.getInstance().getAPI_interface();

        // 3. 建立連線的Call，此處設置call為interface
//        Call<JsonObject> call = getAPI_interface().User_Name(url, "Bearer"+" "+"m9tss6hDwjvqDkM2sVmwNfGd2STHrRwHQhEC9pLr");
        Call<JsonObject> call = getAPI_interface().questionStartResponse(url, bodyContent);
        Log.d("current step", "initialize call for questionStartResponse");

        // 4. async 執行call
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                // 連線成功
                JsonObject response_object = response.body();
                Log.d("response string", response.toString());
                Log.d("response msg", response.message());
                Log.d("response success", String.valueOf(response.isSuccessful()));
//                Log.d("response body string", response.body().toString() );
                if (response.isSuccessful()) {
                    Log.d("response body string", String.valueOf(response.body().isJsonNull()));
                    Log.d("response state", response_object.get("state").getAsString());
                    Log.d("response questionNum", response_object.get("questionNum").getAsString());
                    Log.d("response answerRecordid", response_object.get("answerRecord_uuid").getAsString());
                    answerRecord_uuid = response_object.get("answerRecord_uuid").getAsString();
                    Global.questionNum = response_object.get("questionNum").getAsInt();


                    // use answerRecord_uuid to get questions
                    String url_q = "http://140.116.247.161:8888/questionnaire/new_selectionQuestion/";
                    final JsonObject getQuestiontask = new JsonObject();
                    getQuestiontask.addProperty("state","select");
                    getQuestiontask.addProperty("answerRecord_uuid", answerRecord_uuid);
                    questionSelectResponse(url_q, getQuestiontask);

                } else {
                    checkRequestContent(call.request());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // 連線失敗
                Log.d("response fail", "unsuccessful at store data");
                checkRequestContent(call.request());
            }
        });
    }
    // ------------------------------------------------------------------------------

    // getting question from Health Education by answerRecord_uuid and state='select'
    public void questionSelectResponse(String url,JsonObject bodyContent) {
        // 2. 透過RetrofitManager取得連線基底
        RetrofitInterface = RetrofitRequest.getInstance().getAPI_interface();

        // 3. 建立連線的Call，此處設置call為interface
        Call<JsonObject> call = getAPI_interface().questionSelectResponse(url, bodyContent);
        Log.d("current step", "initialize call for questionSelectResponse");

        // 4. async 執行call
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                // 連線成功
                JsonObject response_object = response.body();
                Log.d("response string", response.toString());
                Log.d("response msg", response.message());
                Log.d("response success", String.valueOf(response.isSuccessful()));
//                Log.d("response body string", response.body().toString() );
                if (response.isSuccessful()) {
                    Log.d("response body string", String.valueOf(response.body().isJsonNull()));
                    Log.d("response state", response_object.get("state").getAsString());
                    if (!response_object.get("state").getAsString().equals("isEnd")){
                        Log.d("response question", response_object.get("question").getAsString());
                        Log.d("response answer", response_object.get("answer").getAsString());
                    }

                    // jump to HealthQuestionFragment
//                    if (!startQuestionnaire){
                    if (response_object.get("state").getAsString().equals("show_question")){
                        // ready to put question and answer to access HealthQuestionFragment
                        //Bundle bundle = new Bundle();
                        // question's option
                        JsonObject options = response_object.get("options").getAsJsonObject();

//                        bundle.putString("state", response_object.get("state").getAsString());//state 初始為show_question
//                        bundle.putString("question", response_object.get("question").getAsString()); //question context
                        Global.state = response_object.get("state").getAsString();
                        Global.question = response_object.get("question").getAsString();

                        Log.d("A",options.get("A").getAsString());
                        Log.d("B",options.get("B").getAsString());
                        Log.d("C",options.get("C").getAsString());
                        Log.d("D",options.get("D").getAsString());
//                        bundle.putString("A", options.get("A").getAsString());
//                        bundle.putString("B", options.get("B").getAsString());
//                        bundle.putString("C", options.get("C").getAsString());
//                        bundle.putString("D", options.get("D").getAsString());
//                        bundle.putString("answer", response_object.get("answer").getAsString());//ground truth
                        Global.A = options.get("A").getAsString();
                        Global.B = options.get("B").getAsString();
                        Global.C = options.get("C").getAsString();
                        Global.D = options.get("D").getAsString();
                        Global.answer = response_object.get("answer").getAsString();

                        // test
                        //Global.mainAct.changeFragment(HealthQuestionFragment.newInstance(bundle));
                        startQuestionnaire = true;
                    }else{
                        // state == isEnd
                        //Bundle bundle = new Bundle();
                        // question's option

//                        bundle.putString("state", response_object.get("state").getAsString());//state 初始為show_question
//                        bundle.putString("question", "恭喜你作答完畢!"); //question context
//                        bundle.putString("A"," ");
//                        bundle.putString("B"," ");
//                        bundle.putString("C"," ");
//                        bundle.putString("D"," ");
//                        bundle.putString("answer", "End");//ground truth
                        Global.state = response_object.get("state").getAsString();
                        Global.question =  "恭喜你作答完畢!";
                        Global.A = " ";
                        Global.B = " ";
                        Global.C = " ";
                        Global.D = " ";
                        Global.answer = "End";


                        //test
                        //Global.mainAct.changeFragment(HealthQuestionFragment.newInstance(bundle));
                    }


                } else {
                    checkRequestContent(call.request());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // 連線失敗
                Log.d("response fail", "unsuccessful at store data");
                checkRequestContent(call.request());
            }
        });
    }
    // ------------------------------------------------------------------------------

    // post user answer to Health Education by answerRecord_uuid, state and userSelect
    public void answerCorrectResponse(String url,JsonObject bodyContent) {
        // 2. 透過RetrofitManager取得連線基底
        RetrofitInterface = RetrofitRequest.getInstance().getAPI_interface();

        // 3. 建立連線的Call，此處設置call為interface
        Call<JsonObject> call = getAPI_interface().answerCorrectResponse(url, bodyContent);
        Log.d("current step", "initialize call for answerCorrectResponse");

        // 4. async 執行call
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                // 連線成功
                JsonObject response_object = response.body();
                Log.d("response string", response.toString());
                Log.d("response msg", response.message());
                Log.d("response success", String.valueOf(response.isSuccessful()));
//                Log.d("response body string", response.body().toString() );
                if (response.isSuccessful()) {
                    Log.d("response body string", String.valueOf(response.body().isJsonNull()));
                    Log.d("response state", response_object.get("state").getAsString());
                    Log.d("response user_ability", response_object.get("user_ability").getAsString());
                    Log.d("response correct", response_object.get("correct").getAsString());
                    Log.d("response text", response_object.get("text").getAsString());
                    Log.d("response speak", response_object.get("speak").getAsString());

                    // ready to put question and answer to access HealthQuestionFragment
                    if (response_object.get("state").getAsString().equals("show_correct")){
                        // jump to ReplyFragment
                        //Bundle bundle = new Bundle();
                        // question's option
                        int correct = response_object.get("correct").getAsInt();
                        String text = response_object.get("text").getAsString();
                        String speak = response_object.get("speak").getAsString();
//                        bundle.putInt("correct", response_object.get("correct").getAsInt()); //correct or not
//                        bundle.putString("text", response_object.get("text").getAsString());
//                        bundle.putString("speak",response_object.get("speak").getAsString());

                        //test
                        //Global.mainAct.changeFragment(ReplyFragment.newInstance(bundle));
                    }

                } else {
                    checkRequestContent(call.request());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // 連線失敗
                Log.d("response fail", "unsuccessful at store data");
                checkRequestContent(call.request());
            }
        });
    }
    // ------------------------------------------------------------------------------

    // for public use to check request content
    private void checkRequestContent(Request request) {
        Log.d("request Headers", request.headers().toString());
        Log.d("request URL", request.url().toString());
    }





}
