package com.cisco.menstruation;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.cisco.menstruation.callback.OnLanguageListener;
import com.cisco.menstruation.ui.BaseActivity;
import com.cisco.menstruation.ui.MenstruationView;

import java.util.Locale;

/**
 * Created by Cisco.hu on 2016/4/10.
 */
public class MenstruationActivity extends BaseActivity{
    private MenstruationView menstruationView;
    private Locale mLocale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menstruationView = new MenstruationView(this,mScreenWidth,mScreenHeight);
        LinearLayout.LayoutParams llp_main = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        menstruationView.setLayoutParams(llp_main);
        menstruationView.setFitsSystemWindows(true);
        setContentView(menstruationView);
        menstruationView.setOnLanguageChangeListener(new OnLanguageListener() {
            @Override
            public void onLanguageChange(int code) {
                switch (code){
                    //中文
                    case 1:
                        setLanguage("zh");
                        break;
                    //英语
                    case 2:
                        setLanguage("en");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 设置语音
     * @param lang
     */
    private void setLanguage(String lang) {
        mLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = mLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MenstruationActivity.class);
        startActivity(refresh);
        close();
    }
}
