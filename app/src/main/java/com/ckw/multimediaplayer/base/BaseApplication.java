package com.ckw.multimediaplayer.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by ckw
 * on 2018/3/2.
 */

public class BaseApplication extends Application{

    public static Context sAppContext;
    @Override
    public void onCreate() {
        super.onCreate();

        sAppContext = getApplicationContext();

    }

}
