package app.application.sdk_base_lib.util;

import android.content.SharedPreferences;

import java.util.Set;

import app.application.sdk_base_lib.BaseApplication;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by huangkangfa on 2017/10/12.
 */

public class SPUtils {
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;

    private volatile static SPUtils sp;
    private SPUtils(){}
    public static SPUtils getInstance(){
        if(sp==null){
            synchronized (SPUtils.class){
                if(sp==null){
                    sp=new SPUtils();
                    sp.editor= BaseApplication.getContext().getSharedPreferences("shared_prefs",MODE_PRIVATE).edit();
                    sp.pref=BaseApplication.getContext().getSharedPreferences("shared_prefs",MODE_PRIVATE);
                }
            }
        }
        return sp;
    }

    public void putString(String key, String data){
        editor.putString(key,data);
        editor.commit();
    }

    public String getString(String key,String defaultString){
        return pref.getString(key,defaultString);
    }


    public void putBoolean(String key, boolean data){
        editor.putBoolean(key,data);
        editor.commit();
    }

    public boolean getBoolean(String key,boolean defaultBoolean){
        return pref.getBoolean(key,defaultBoolean);
    }


    public void putLong(String key, long data){
        editor.putLong(key,data);
        editor.commit();
    }

    public long getLong(String key,long defaultLong){
        return pref.getLong(key,defaultLong);
    }


    public void putFloat(String key, float data){
        editor.putFloat(key,data);
        editor.commit();
    }

    public float getFloat(String key,float defaultFloat){
        return pref.getFloat(key,defaultFloat);
    }


    public void putInt(String key, int data){
        editor.putInt(key,data);
        editor.commit();
    }

    public int getInt(String key,int defaultInt){
        return pref.getInt(key,defaultInt);
    }


    public void putStringSet(String key, Set<String> data){
        editor.putStringSet(key,data);
        editor.commit();
    }

    public Set<String> getStringSet(String key){
        return pref.getStringSet(key,null);
    }
}
