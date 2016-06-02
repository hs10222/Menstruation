package com.cisco.menstruation.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.cisco.menstruation.configs.CalendarViewAdapter;
import com.cisco.menstruation.configs.Configs;
import com.cisco.menstruation.configs.CustomCalendarView;
import com.cisco.menstruation.configs.CustomDate;
import com.cisco.menstruation.customview.BleedingView;
import com.cisco.menstruation.menstruation.R;

/**
 * CalendarView
 * custom calendar view
 * Created by Cisco.hu on 2016/4/1.
 */
public class CalendarView extends LinearLayout implements CustomCalendarView.OnCellClickListener {
    private ViewPager mViewPager;
    private int mCurrentIndex = 498;
    private CustomCalendarView[] mShowViews;
    private CalendarViewAdapter<CustomCalendarView> adapter;
    private SildeDirection mDirection = SildeDirection.NO_SILDE;
    private Context mContext;
    private ScrollView mScrollView;
    private TextView[] mTvTitles = new TextView[7];
    private String[] mTitles;

    enum SildeDirection {
        RIGHT, LEFT, NO_SILDE;
    }

    public CalendarView(Context context) {
        super(context);
        setBackgroundColor(Color.WHITE);
        setOrientation(LinearLayout.VERTICAL);
        mContext = context;
        mTitles = mContext.getResources().getStringArray(R.array.calendar_title);
        setTop();
        setContent();
    }

    private void setTop() {
        LinearLayout ll_top = new LinearLayout(mContext);
        LinearLayout.LayoutParams llp_top = new LayoutParams(Configs.SCREEN_WIDTH,
                Configs.SCREEN_WIDTH * 120 / 720);
        ll_top.setBackgroundResource(R.color.background);
        addView(ll_top, llp_top);

        ImageView im_icon = new ImageView(mContext);
        im_icon.setBackgroundResource(R.mipmap.analyze);
        LinearLayout.LayoutParams llp_im_icon = new LayoutParams(
                Configs.SCREEN_WIDTH * 40 / 720, Configs.SCREEN_WIDTH * 40 / 720);
        llp_im_icon.setMargins(45 * Configs.SCREEN_WIDTH / 720, 40 * Configs.SCREEN_WIDTH / 720, 0, 0);
        ll_top.addView(im_icon, llp_im_icon);
    }

    private void setContent() {
        mScrollView = new ScrollView(mContext);
        LinearLayout.LayoutParams llp_scroll = new LayoutParams(Configs.SCREEN_WIDTH,
                Configs.SCREEN_HEIGHT - Configs.SCREEN_WIDTH * 120 / 720);
        addView(mScrollView, llp_scroll);
        LinearLayout ll_content = new LinearLayout(mContext);
        ll_content.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams llp_content = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        ll_content.setLayoutParams(llp_content);
        mScrollView.addView(ll_content);

        //左右选择日期
        LinearLayout ll_arrow = new LinearLayout(mContext);
        LinearLayout.LayoutParams llp_arrow = new LayoutParams(630 * Configs.SCREEN_WIDTH / 720,
                60 * Configs.SCREEN_WIDTH / 720);
        llp_arrow.setMargins(45 * Configs.SCREEN_WIDTH / 720, 0, 0, 0);
        ll_content.addView(ll_arrow, llp_arrow);
        ImageView im_left = new ImageView(mContext);
        im_left.setBackgroundResource(R.mipmap.arrow_left);
        LinearLayout.LayoutParams llp_im_left = new LinearLayout.LayoutParams(
                Configs.SCREEN_WIDTH * 24 / 720, Configs.SCREEN_WIDTH * 43 / 720);
        llp_im_left.setMargins(0, 10 * Configs.SCREEN_WIDTH / 720, 0, 0);
        ll_arrow.addView(im_left, llp_im_left);

        //星期标题
        LinearLayout ll_title = new LinearLayout(mContext);
        LinearLayout.LayoutParams llp_title = new LayoutParams(630 * Configs.SCREEN_WIDTH / 720,
                40 * Configs.SCREEN_WIDTH / 720);
        llp_title.setMargins(45 * Configs.SCREEN_WIDTH / 720, 0, 0, 0);
        ll_title.setBackgroundColor(Color.rgb(220, 220, 220));

        for (int i = 0, l = mTvTitles.length; i < l; i++) {
            mTvTitles[i] = new TextView(mContext);
            mTvTitles[i].setText(mTitles[i]);
            mTvTitles[i].setTextColor(Color.WHITE);
            mTvTitles[i].setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams llp_tv = new LayoutParams(90 * Configs.SCREEN_WIDTH / 720,
                    40 * Configs.SCREEN_WIDTH / 720);
            ll_title.addView(mTvTitles[i], llp_tv);
        }
        ll_content.addView(ll_title, llp_title);


        mViewPager = new ViewPager(mContext);
        LinearLayout.LayoutParams llp_viewpager = new LayoutParams(
                630 * Configs.SCREEN_WIDTH / 720, 450 * Configs.SCREEN_WIDTH / 720);
        llp_viewpager.setMargins(45 * Configs.SCREEN_WIDTH / 720, 0, 0, 0);
        ll_content.addView(mViewPager, llp_viewpager);
        mShowViews = new CustomCalendarView[3];
        for (int i = 0; i < 3; i++) {
            mShowViews[i] = new CustomCalendarView(mContext, this);
        }
        adapter = new CalendarViewAdapter<>(mShowViews);
        setViewPager();

        setPeriodLayout(ll_content);
        setBeedingLayout(ll_content);
    }

    /**
     * 设置月经布局
     */
    private void setPeriodLayout(LinearLayout layout) {
        //月经开始
        LinearLayout ll_period_start = new LinearLayout(mContext);
        LinearLayout.LayoutParams llp_period_start = new LayoutParams(
                630 * Configs.SCREEN_WIDTH / 720, 100 * Configs.SCREEN_WIDTH / 720);
        llp_period_start.setMargins(45 * Configs.SCREEN_WIDTH / 720, 0, 0, 0);
        layout.addView(ll_period_start, llp_period_start);
        ImageView im_period_start = new ImageView(mContext);
        im_period_start.setBackgroundResource(R.mipmap.info);
        LinearLayout.LayoutParams llp_im_period_start = new LayoutParams(
                60 * Configs.SCREEN_WIDTH / 720, 60 * Configs.SCREEN_WIDTH / 720);
        llp_im_period_start.setMargins(0, 20 * Configs.SCREEN_WIDTH / 720, 0, 0);
        ll_period_start.addView(im_period_start, llp_im_period_start);

        TextView tv_period_start = new TextView(mContext);
        tv_period_start.setText(R.string.period_start);
        tv_period_start.setTextColor(Color.BLACK);
        tv_period_start.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * Configs.SCREEN_WIDTH / 720);
        tv_period_start.setGravity(Gravity.CENTER | Gravity.LEFT);
        LinearLayout.LayoutParams llp_tv_period_start = new LayoutParams(
                Configs.SCREEN_WIDTH * 200 / 720, LayoutParams.MATCH_PARENT);
        llp_tv_period_start.setMargins(Configs.SCREEN_WIDTH * 20 / 720, 0, 0, 0);
        ll_period_start.addView(tv_period_start, llp_tv_period_start);

        Switch swith_period_start = new Switch(mContext);
        LinearLayout.LayoutParams llp_sw_period_start = new LayoutParams(
                Configs.SCREEN_WIDTH * 200 / 720, Configs.SCREEN_WIDTH * 60 / 720);
        llp_sw_period_start.setMargins(150 * Configs.SCREEN_WIDTH / 720,
                0, 0, 0);
        ll_period_start.addView(swith_period_start, llp_sw_period_start);

        TextView tv_line = new TextView(mContext);
        LinearLayout.LayoutParams llp_tv_line = new LayoutParams(
                630 * Configs.SCREEN_WIDTH / 720, 2 * Configs.SCREEN_WIDTH / 720);
        llp_tv_line.setMargins(45 * Configs.SCREEN_WIDTH / 720, 0, 0, 0);
        tv_line.setBackgroundColor(Color.rgb(220, 220, 220));
        layout.addView(tv_line, llp_tv_line);
        //月经停止
        LinearLayout ll_period_stop = new LinearLayout(mContext);
        LinearLayout.LayoutParams llp_period_stop = new LayoutParams(
                630 * Configs.SCREEN_WIDTH / 720, 100 * Configs.SCREEN_WIDTH / 720);
        llp_period_stop.setMargins(45 * Configs.SCREEN_WIDTH / 720, 0, 0, 0);
        layout.addView(ll_period_stop, llp_period_stop);

        ImageView im_period_stop = new ImageView(mContext);
        im_period_stop.setBackgroundResource(R.mipmap.info);
        LinearLayout.LayoutParams llp_im_period_stop = new LayoutParams(
                60 * Configs.SCREEN_WIDTH / 720, 60 * Configs.SCREEN_WIDTH / 720);
        llp_im_period_stop.setMargins(0, 20 * Configs.SCREEN_WIDTH / 720, 0, 0);
        ll_period_stop.addView(im_period_stop, llp_im_period_stop);

        TextView tv_period_stop = new TextView(mContext);
        tv_period_stop.setText(R.string.period_stop);
        tv_period_stop.setTextColor(Color.BLACK);
        tv_period_stop.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * Configs.SCREEN_WIDTH / 720);
        tv_period_stop.setGravity(Gravity.CENTER | Gravity.LEFT);
        LinearLayout.LayoutParams llp_tv_period_stop = new LayoutParams(
                Configs.SCREEN_WIDTH * 200 / 720, LayoutParams.MATCH_PARENT);
        llp_tv_period_stop.setMargins(Configs.SCREEN_WIDTH * 20 / 720, 0, 0, 0);
        ll_period_stop.addView(tv_period_stop, llp_tv_period_stop);

        Switch swith_period_stop = new Switch(mContext);
        LinearLayout.LayoutParams llp_sw_period_stop = new LayoutParams(
                Configs.SCREEN_WIDTH * 200 / 720, Configs.SCREEN_WIDTH * 60 / 720);
        llp_sw_period_stop.setMargins(150 * Configs.SCREEN_WIDTH / 720,
                0, 0, 0);
        ll_period_stop.addView(swith_period_stop, llp_sw_period_stop);

        TextView tv_line_stop = new TextView(mContext);
        LinearLayout.LayoutParams llp_tv_line_stop = new LayoutParams(
                630 * Configs.SCREEN_WIDTH / 720, 2 * Configs.SCREEN_WIDTH / 720);
        llp_tv_line_stop.setMargins(45 * Configs.SCREEN_WIDTH / 720, 0, 0, 0);
        tv_line_stop.setBackgroundColor(Color.rgb(220, 220, 220));
        layout.addView(tv_line_stop, llp_tv_line_stop);
    }

    /**
     * 设置月经流量
     */
    private void setBeedingLayout(LinearLayout layout) {
        LinearLayout ll_beeding = new LinearLayout(mContext);
        LinearLayout.LayoutParams llp_beeding = new LayoutParams(630 * Configs.SCREEN_WIDTH / 720,
                100 * Configs.SCREEN_WIDTH / 720);
        llp_beeding.setMargins(45 * Configs.SCREEN_WIDTH / 720, 0, 0, 0);
        layout.addView(ll_beeding, llp_beeding);
        ImageView im_beeding = new ImageView(mContext);
        LinearLayout.LayoutParams llp_im_beeding = new LayoutParams(60 * Configs.SCREEN_WIDTH / 720,
                60 * Configs.SCREEN_WIDTH / 720);
        llp_im_beeding.setMargins(0, 20 * Configs.SCREEN_WIDTH / 720, 0, 0);
        im_beeding.setBackgroundResource(R.mipmap.info);
        ll_beeding.addView(im_beeding, llp_im_beeding);

        TextView tv_beeding = new TextView(mContext);
        tv_beeding.setText(R.string.bleeding);
        tv_beeding.setGravity(Gravity.CENTER | Gravity.LEFT);
        tv_beeding.setTextColor(Color.BLACK);
        tv_beeding.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * Configs.SCREEN_WIDTH / 720);
        LinearLayout.LayoutParams llp_tv_beeding = new LayoutParams(150 * Configs.SCREEN_WIDTH / 720,
                LayoutParams.MATCH_PARENT);
        llp_tv_beeding.setMargins(20 * Configs.SCREEN_WIDTH / 720, 0, 0, 0);
        ll_beeding.addView(tv_beeding, llp_tv_beeding);

        BleedingView bleedingViews = new BleedingView(mContext);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                Configs.SCREEN_WIDTH * 400 / 720, Configs.SCREEN_WIDTH * 100 / 720);
        llp.setMargins(0, 29 * Configs.SCREEN_WIDTH / 720, 0, 0);
        ll_beeding.addView(bleedingViews, llp);

    }

    @Override
    public void clickDate(CustomDate date) {

    }

    @Override
    public void changeDate(CustomDate date) {

    }

    private void setViewPager() {
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(498);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                measureDirection(position);
                updateCalendarView(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    /**
     * 计算方向
     *
     * @param arg0
     */
    private void measureDirection(int arg0) {

        if (arg0 > mCurrentIndex) {
            mDirection = SildeDirection.RIGHT;

        } else if (arg0 < mCurrentIndex) {
            mDirection = SildeDirection.LEFT;
        }
        mCurrentIndex = arg0;
    }

    // 更新日历视图
    private void updateCalendarView(int arg0) {
        mShowViews = adapter.getAllItems();
        if (mDirection == SildeDirection.RIGHT) {
            mShowViews[arg0 % mShowViews.length].rightSlide();
        } else if (mDirection == SildeDirection.LEFT) {
            mShowViews[arg0 % mShowViews.length].leftSlide();
        }
        mDirection = SildeDirection.NO_SILDE;
    }
}
