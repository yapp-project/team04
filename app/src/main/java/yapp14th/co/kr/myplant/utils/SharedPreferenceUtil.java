package yapp14th.co.kr.myplant.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferenceUtil {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static final String PUSH_CHECK_FINISHED = "PUSH_CHECK_FINISHED";
    public static final String COLOR_PICK_FINISHED = "COLOR_PICK_FINISHED";

    public static final String EMOTION_1 = "EMOTION_1";
    public static final String EMOTION_1_PIN_X = "EMOTION_1_PIN_X";
    public static final String EMOTION_1_PIN_Y = "EMOTION_1_PIN_Y";
    public static final String EMOTION_2 = "EMOTION_2";
    public static final String EMOTION_2_PIN_X = "EMOTION_2_PIN_X";
    public static final String EMOTION_2_PIN_Y = "EMOTION_2_PIN_Y";
    public static final String EMOTION_3 = "EMOTION_3";
    public static final String EMOTION_3_PIN_X = "EMOTION_3_PIN_X";
    public static final String EMOTION_3_PIN_Y = "EMOTION_3_PIN_Y";
    public static final String EMOTION_4 = "EMOTION_4";
    public static final String EMOTION_4_PIN_X = "EMOTION_4_PIN_X";
    public static final String EMOTION_4_PIN_Y = "EMOTION_4_PIN_Y";
    public static final String EMOTION_5 = "EMOTION_5";
    public static final String EMOTION_5_PIN_X = "EMOTION_5_PIN_X";
    public static final String EMOTION_5_PIN_Y = "EMOTION_5_PIN_Y";
    public static final String EMOTION_6 = "EMOTION_6";
    public static final String EMOTION_6_PIN_X = "EMOTION_6_PIN_X";
    public static final String EMOTION_6_PIN_Y = "EMOTION_6_PIN_Y";
    public static final String EMOTION_7 = "EMOTION_7";
    public static final String EMOTION_7_PIN_X = "EMOTION_7_PIN_X";
    public static final String EMOTION_7_PIN_Y = "EMOTION_7_PIN_Y";
    public static final String EMOTION_8 = "EMOTION_8";
    public static final String EMOTION_8_PIN_X = "EMOTION_8_PIN_X";
    public static final String EMOTION_8_PIN_Y = "EMOTION_8_PIN_Y";
    public static final String last = "last";

    public static void sharedPreferenceInit(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static String getStringData(String key) {
        if (key.startsWith("EMOTION")) {
            String data = "#" + ((int) (Math.random() * 89999999 + 10000000));
            // Log.d("color" + key, " : " + data);
            return sharedPreferences.getString(key, data);
        } else
            return sharedPreferences.getString(key, "");
    }

    public static int getIntegerData(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public static float getFloatData(String key) {
        return sharedPreferences.getFloat(key, 0);
    }

    public static boolean getBooleanData(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public static void setData(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static void setData(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setData(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public static void setData(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }
}
