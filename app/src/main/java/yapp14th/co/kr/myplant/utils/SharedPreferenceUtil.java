package yapp14th.co.kr.myplant.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferenceUtil {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static final String EMOTION_1 = "EMOTION_1";
    public static final String EMOTION_2 = "EMOTION_2";
    public static final String EMOTION_3 = "EMOTION_3";
    public static final String EMOTION_4 = "EMOTION_4";
    public static final String EMOTION_5 = "EMOTION_5";
    public static final String EMOTION_6 = "EMOTION_6";
    public static final String EMOTION_7 = "EMOTION_7";
    public static final String EMOTION_8 = "EMOTION_8";

    public static void sharedPreferenceInit(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static String getStringData(String key) {
        if (key.startsWith("EMOTION")){
            String data = "#" + ((int) (Math.random() * 89999999 + 10000000));
            // Log.d("color" + key, " : " + data);
            return sharedPreferences.getString(key, data);
        }

        return sharedPreferences.getString(key, "");
    }

    public static int getIntegerData(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public static void setData(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static void setData(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }
}
