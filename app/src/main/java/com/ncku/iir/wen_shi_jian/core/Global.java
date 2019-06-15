package com.ncku.iir.wen_shi_jian.core;

public class Global {

    // for Health Education
    public static String HE_user_id="unknown";
    // diabetes_all = 31, high_pressure = 30, diabetes_nutrition = 32
    public static int topic_id = 31;
    public static int questionNum = 0;
    public static String answerRecord_uuid = "unknown";
    public static Boolean startQuestionnaire = false;

    // for shi jian
    public static int q_count = 0;
    public static int  maxCorrect = 0;
    public static String username;
    public static String A = " ";
    public static String B = " ";
    public static String C = " ";
    public static String D = " ";
    public static String state = "";
    public static String question = "";
    public static String answer = "";
    public static int score = 0;


    // for feedback
    public static int wrong_num = 0;
    public static String feedbackinfo = "";

}
