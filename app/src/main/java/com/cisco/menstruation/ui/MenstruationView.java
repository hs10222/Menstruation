package com.cisco.menstruation.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cisco.menstruation.callback.OnLanguageListener;
import com.cisco.menstruation.menstruation.R;

/**
 * Created by Cisco.hu on 2016/4/23.
 */
public class MenstruationView extends FrameLayout implements View.OnTouchListener{
    private FrameLayout mLayoutTop;
    private FrameLayout mLayoutBottom;
    private Context mContext;
    private int mWidth;
    private int mHegith;
    private String[] mTabMenuName;
    private int[] mTabMenuIcon = {R.mipmap.home,R.mipmap.calendar,R.mipmap.add,
            R.mipmap.notification_tool,R.mipmap.setting};;
    private HomeView mHomeView;
    private CalendarView mCalendarView;
    private SettingsView mSettingsView;
    private OnLanguageListener mListener;
    public MenstruationView(Context context,int w,int h) {
        super(context);
        mContext = context;
        mWidth = w;
        mHegith = h;
        initView();
    }

    private void initView(){
        mTabMenuName = mContext.getResources().getStringArray(R.array.tab_menu);
        mLayoutTop = new FrameLayout(mContext);
        mLayoutBottom = new FrameLayout(mContext);
        setContentView(0);
        setControlView();
    }

    public void setOnLanguageChangeListener(OnLanguageListener listener){
        mListener = listener;
    }
    /**
     * tab切换内容页
     * @param id
     */
    private void setContentView(int id){
        mLayoutTop.removeAllViews();
        Log.i("sys","--------"+id);
        switch (id){
            case 0:
                mHomeView = new HomeView(mContext);
                mLayoutTop.addView(mHomeView);
                break;
            case 1:
                mCalendarView = new CalendarView(mContext);
                mLayoutTop.addView(mCalendarView);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                mSettingsView = new SettingsView(mContext,mListener);
                mLayoutTop.addView(mSettingsView);
                break;
            default:
                break;
        }
    }

    /**
     * 自定义的tab选择菜单
     */
    private void setControlView(){
        FrameLayout.LayoutParams llp_top = new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,mHegith-mWidth*120/720,1);
        FrameLayout.LayoutParams llp_bottom = new FrameLayout.LayoutParams(mWidth,
                mWidth*144/720);
        llp_bottom.gravity = Gravity.BOTTOM;
        addView(mLayoutTop, llp_top);
        addView(mLayoutBottom, llp_bottom);
        FrameLayout ll_bottom = new FrameLayout(mContext);
        FrameLayout.LayoutParams llp = new FrameLayout.LayoutParams(mWidth,
                mWidth*120/720);
        llp.setMargins(0,22*mWidth/720,0,0);
        ll_bottom.setLayoutParams(llp);
        ll_bottom.setBackgroundColor(Color.rgb(239,239,239));
        mLayoutBottom.addView(ll_bottom);
        for(int i =0 ,l = mTabMenuName.length; i < l; i++){
            if(i != 2){
                TabMenu menu = new TabMenu(mContext);
                menu.setId(i);
                menu.setOnTouchListener(this);
                menu.setMenuName(mTabMenuName[i]);
                menu.setMenuIcon(mTabMenuIcon[i]);
                FrameLayout.LayoutParams llp_menu = new FrameLayout.LayoutParams(
                        mWidth/5,mWidth*120/720);
                llp_menu.setMargins(i*mWidth/5,0,0,0);
                ll_bottom.addView(menu,llp_menu);
            }else{
                FrameLayout fl_menu = new FrameLayout(mContext);
                fl_menu.setFocusableInTouchMode(true);
                fl_menu.setId(i);
                fl_menu.setBackgroundResource(R.drawable.add);
                FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(mWidth/5,mWidth/5);
                flp.setMargins(mWidth*2/5,0,0,0);
                ImageView im = new ImageView(mContext);
                FrameLayout.LayoutParams llp_im = new FrameLayout.LayoutParams(mWidth*60/720,mWidth*60/720);
                llp_im.setMargins(38  * mWidth / 720, 38 * mWidth / 720, 0, 0);
                im.setBackgroundResource(mTabMenuIcon[i]);
                im.setLayoutParams(llp_im);
                fl_menu.addView(im);
                mLayoutBottom.addView(fl_menu,flp);
                fl_menu.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(v.getId());
                    }
                });
            }

        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if(event.getX()>0&&event.getY()>0&&event.getX()<view.getWidth()
                &&event.getY()<view.getHeight()){
            Log.i("sys","---------"+event.getX());
            int id = view.getId();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                        setContentView(id);
                    break;
                default:
                    break;
            }
        }
        return true;
    }
}
