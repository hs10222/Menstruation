package com.cisco.menstruation.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cisco.menstruation.configs.Configs;

/**
 * Created by Administrator on 2016/4/23.
 */
public class TabMenu extends FrameLayout{
    private ImageView mImageIcon;
    private TextView mTextName;
    private Context mContext;
    public TabMenu(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init(){
        mImageIcon = new ImageView(mContext);
        mTextName = new TextView(mContext);
        mTextName.setTextSize(TypedValue.COMPLEX_UNIT_PX, Configs.SCREEN_WIDTH*25/720);
        mTextName.setGravity(Gravity.CENTER|Gravity.TOP);
        mTextName.setTextColor(Color.rgb(184,184,184));
        FrameLayout.LayoutParams llp_icon = new LayoutParams(Configs.SCREEN_WIDTH*60/720,
                Configs.SCREEN_WIDTH*60/720);
        llp_icon.setMargins(Configs.SCREEN_WIDTH*42/720,Configs.SCREEN_WIDTH*20/720
                ,0,Configs.SCREEN_WIDTH*8/720);

        addView(mImageIcon,llp_icon);
        FrameLayout.LayoutParams llp_name = new LayoutParams(Configs.SCREEN_WIDTH*140/720,
                LayoutParams.WRAP_CONTENT);
        llp_name.setMargins(Configs.SCREEN_WIDTH*2/720,Configs.SCREEN_WIDTH*85/720,
                0,0);
        addView(mTextName,llp_name);
    }

    public void setMenuName(int id){
        mTextName.setText(id);
    }

    public void setMenuName(String name){
        mTextName.setText(name);
    }

    public void setMenuIcon(int id){
        mImageIcon.setBackgroundResource(id);
    }

}
