package com.cisco.menstruation.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cisco.menstruation.configs.CalendarViewAdapter;
import com.cisco.menstruation.configs.Configs;
import com.cisco.menstruation.configs.CustomDate;
import com.cisco.menstruation.menstruation.R;

import java.util.List;

/**
 * Created by Administrator on 2016/4/24.
 */
public class HomeView extends LinearLayout{

    public HomeView(Context context) {
        super(context);
    }
}
