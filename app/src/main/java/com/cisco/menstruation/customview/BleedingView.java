package com.cisco.menstruation.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cisco.menstruation.configs.Configs;
import com.cisco.menstruation.menstruation.R;

/**
 * Created by Administrator on 2016/6/2.
 */
public class BleedingView extends LinearLayout{

    private Context mContext;
    private int mClickTimes;
    private int mClickTimes1;
    private int mClickTimes2;

    public BleedingView(Context context) {
        super(context);
        mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);
        init();
    }

    /**
     * 初始化布局
     */
    private void init() {
        LinearLayout ll_two = new LinearLayout(mContext);
        LinearLayout.LayoutParams llp_two = new LayoutParams(Configs.SCREEN_WIDTH*66/720,
                42*Configs.SCREEN_WIDTH/720);
        llp_two.setMargins(0,0,30*Configs.SCREEN_WIDTH/720,0);
        addView(ll_two, llp_two);
        final ImageView[] im_two = new ImageView[2];
        for (int i = 0; i < 2; i++) {
            im_two[i] = new ImageView(mContext);
            LinearLayout.LayoutParams llp = new LayoutParams(Configs.SCREEN_WIDTH * 33 / 720,
                    42 * Configs.SCREEN_WIDTH / 720);
            im_two[i].setBackgroundResource(R.mipmap.bleeding_grey);
            ll_two.addView(im_two[i], llp);
        }

        LinearLayout ll_three = new LinearLayout(mContext);
        LinearLayout.LayoutParams llp_three = new LayoutParams(Configs.SCREEN_WIDTH*99/720,
                42*Configs.SCREEN_WIDTH/720);
        llp_three.setMargins(0,0,30*Configs.SCREEN_WIDTH/720,0);
        addView(ll_three,llp_three);

        final ImageView[] im_three = new ImageView[3];
        for (int i = 0; i < 3; i++) {
            im_three[i] = new ImageView(mContext);
            LinearLayout.LayoutParams llp = new LayoutParams(Configs.SCREEN_WIDTH * 33 / 720,
                    42 * Configs.SCREEN_WIDTH / 720);
            im_three[i].setBackgroundResource(R.mipmap.bleeding_grey);
            ll_three.addView(im_three[i], llp);
        }

        LinearLayout ll_four = new LinearLayout(mContext);
        LinearLayout.LayoutParams llp_four = new LayoutParams(Configs.SCREEN_WIDTH*132/720,
                42*Configs.SCREEN_WIDTH/720);
        llp_four.setMargins(0,0,30*Configs.SCREEN_WIDTH/720,0);
        addView(ll_four,llp_four);
        final ImageView[] im_four = new ImageView[4];
        for (int i = 0; i < 4; i++) {
            im_four[i] = new ImageView(mContext);
            LinearLayout.LayoutParams llp = new LayoutParams(Configs.SCREEN_WIDTH * 33 / 720,
                    42 * Configs.SCREEN_WIDTH / 720);
            im_four[i].setBackgroundResource(R.mipmap.bleeding_grey);
            ll_four.addView(im_four[i], llp);
        }
        ll_two.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i =0; i < 3;i++){
                    im_three[i].setBackgroundResource(R.mipmap.bleeding_grey);
                }
                for(int i =0; i < 4; i++){
                    im_four[i].setBackgroundResource(R.mipmap.bleeding_grey);
                }
                if (mClickTimes % 2 == 0) {
                    for(int i =0; i < 2;i++){
                        im_two[i].setBackgroundResource(R.mipmap.bleeding_red);
                    }
                } else {
                    for(int i =0; i < 2;i++){
                        im_two[i].setBackgroundResource(R.mipmap.bleeding_grey);
                    }
                }
                mClickTimes = ++mClickTimes > 5? mClickTimes%2:mClickTimes;
                mClickTimes1 = 0;
                mClickTimes2 = 0;
            }
        });
        ll_three.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i =0; i < 2;i++){
                    im_two[i].setBackgroundResource(R.mipmap.bleeding_grey);
                }
                for(int i =0; i < 4; i++){
                    im_four[i].setBackgroundResource(R.mipmap.bleeding_grey);
                }
                if (mClickTimes1 % 2 == 0) {
                    for(int i =0; i < 3;i++){
                        im_three[i].setBackgroundResource(R.mipmap.bleeding_red);
                    }
                } else {
                    for(int i =0; i < 3;i++){
                        im_three[i].setBackgroundResource(R.mipmap.bleeding_grey);
                    }
                }
                mClickTimes1 = ++mClickTimes1 > 5? mClickTimes1%2:mClickTimes1;
                mClickTimes = 0;
                mClickTimes2 = 0;
            }
        });
        ll_four.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i =0; i < 2;i++){
                    im_two[i].setBackgroundResource(R.mipmap.bleeding_grey);
                }
                for(int i =0; i < 3; i++){
                    im_three[i].setBackgroundResource(R.mipmap.bleeding_grey);
                }
                if (mClickTimes2 % 2 == 0) {
                    for(int i =0; i < 4;i++){
                        im_four[i].setBackgroundResource(R.mipmap.bleeding_red);
                    }
                } else {
                    for(int i =0; i < 4;i++){
                        im_four[i].setBackgroundResource(R.mipmap.bleeding_grey);
                    }
                }
                mClickTimes2 = ++mClickTimes2 > 5? mClickTimes2%2:mClickTimes2;
                mClickTimes1 = 0;
                mClickTimes = 0;
            }
        });
    }

}
