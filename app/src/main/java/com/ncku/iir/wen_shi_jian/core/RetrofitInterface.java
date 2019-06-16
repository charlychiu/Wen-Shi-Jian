package com.ncku.iir.wen_shi_jian.core;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface RetrofitInterface {

    String BASE_URL = "http://140.116.247.161:8888/questionnaire/";

    // Request user_id from Health Education by line_id and line_email
    @POST
    Call<JsonObject> lineMappingLoginResponse(@Url String url, @Body JsonObject bodyContent); //<JsonObject>是return type 跟@header共同體

    //request answerRecord_uuid (start this session) from Health Education by user_id and topic_id
    @POST
    Call<JsonObject> questionStartResponse(@Url String url, @Body JsonObject bodyContent); //<JsonObject>是return type 跟@header共同體

    //request question from Health Education by answerRecord_uuid
    @POST
    Call<JsonObject> questionSelectResponse(@Url String url, @Body JsonObject bodyContent); //<JsonObject>是return type 跟@header共同體

    //post user answer to Health Education by answerRecord_uuid, state and userSelect
    // actually same as questionSelectResponse api
    @POST
    Call<JsonObject> answerCorrectResponse(@Url String url, @Body JsonObject bodyContent); //<JsonObject>是return type 跟@header共同體


}
