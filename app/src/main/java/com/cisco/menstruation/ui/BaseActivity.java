package com.cisco.menstruation.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.cisco.menstruation.configs.Configs;

/**
 * Created by Cisco.hu on 2016/4/23.
 */
public class BaseActivity extends Activity{
    public  int mScreenHeight;
    public  int mScreenWidth;
    public  float mScalePixel;

    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //出去任务栏和菜单栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.requestWindowFeature(Window.ID_ANDROID_CONTENT);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        IntentFilter filter = new IntentFilter();
        filter.addAction("ExitApp");
        this.registerReceiver(broadcastReceiver, filter);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenHeight = metric.heightPixels; // 屏幕宽度（像素）
        mScreenWidth = metric.widthPixels; // 屏幕高度（像素）
        mScalePixel = metric.density; // 屏幕密度
        Configs.SCREEN_WIDTH = mScreenWidth;
        Configs.SCREEN_HEIGHT = mScreenHeight;

    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        this.unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    /*******************
     * 函数：退出app
     *******************/
    public void close() {
        Intent intent = new Intent();
        intent.setAction("ExitApp");
        this.sendBroadcast(intent);
        super.finish();
    }

    /***************************************
     * 函数：设置是否全屏
     * @param isFull
     ****************************************/
    public void setFullScreen(boolean isFull){
        if(isFull){
            //全屏显示
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}
