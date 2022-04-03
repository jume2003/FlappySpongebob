package com.example.flappyspongebob;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
	
	public static String NAME = "spongebob";

    private SPUtils() {
    }

    public static boolean putBoolean(Context context, String key, boolean value) {
    	try{
	        SharedPreferences settings = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	        SharedPreferences.Editor editor = settings.edit();
	        editor.putBoolean(key, value);
	        return editor.commit();
    	}catch(Throwable e){
    		return false;
    	}
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
    	try{
	        SharedPreferences settings = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	        return settings.getBoolean(key, defaultValue);
    	}catch(Throwable e){
    		return defaultValue;
    	}
    }

    public static boolean putInt(Context context, String key, int value) {
    	try{
	        SharedPreferences settings = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	        SharedPreferences.Editor editor = settings.edit();
	        editor.putInt(key, value);
	        return editor.commit();
    	}catch(Throwable e){
    		return false;
    	}
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, 0);
    }

    public static int getInt(Context context, String key, int defaultValue) {
    	try{
	        SharedPreferences settings = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	        return settings.getInt(key, defaultValue);
    	}catch(Throwable e){
    		return defaultValue;
    	}
    }
    
    public static boolean putLong(Context context, String key, long value) {
    	try{
	        SharedPreferences settings = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	        SharedPreferences.Editor editor = settings.edit();
	        editor.putLong(key, value);
	        return editor.commit();
    	}catch(Throwable e){
    		return false;
    	}
    }

    public static long getLong(Context context, String key) {
        return getLong(context, key, 0);
    }

    public static long getLong(Context context, String key, long defaultValue) {
    	try{
	        SharedPreferences settings = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	        return settings.getLong(key, defaultValue);
    	}catch(Throwable e){
    		return defaultValue;
    	}
    }
    
    public static boolean putString(Context context, String key, String value) {
    	try{
	        SharedPreferences settings = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	        SharedPreferences.Editor editor = settings.edit();
	        editor.putString(key, value);
	        return editor.commit();
    	}catch(Throwable e){
    		return false;
    	}
    }

    public static String getString(Context context, String key, String defaultValue) {
    	try{
	        SharedPreferences settings = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	        return settings.getString(key, defaultValue);
    	}catch(Throwable e){
    		return defaultValue;
    	}
    }

}
