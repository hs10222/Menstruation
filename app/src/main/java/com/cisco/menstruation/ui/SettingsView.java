package com.cisco.menstruation.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cisco.menstruation.adapter.SettingsAdapter;
import com.cisco.menstruation.callback.OnLanguageListener;
import com.cisco.menstruation.configs.Configs;
import com.cisco.menstruation.data.SettingItem;
import com.cisco.menstruation.menstruation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * settings view
 * Created by Cisco.hu on 2016/4/24.
 */
public class SettingsView extends LinearLayout{

    private LinearLayout mLayoutTop;
    private LinearLayout mLayoutContent;
    private Context mContext;
    private int[] mIcons = {R.mipmap.status,R.mipmap.notification,R.mipmap.passcode,
            R.mipmap.language,R.mipmap.theme,R.mipmap.support};
    private String[] mNames;
    private List<SettingItem> mList;
    private OnLanguageListener mListener;
    public SettingsView(Context context,OnLanguageListener listener) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);
//        this.setBackgroundColor(Color.rgb(200, 200, 200));
        mContext = context;
        mListener = listener;
        initData();
        setContentLayout();
    }
    private void initData(){
        mNames = mContext.getResources().getStringArray(R.array.settings_name);
        mList = new ArrayList<>(6);
        Log.i("sys",""+mIcons.length);
        for(int i =0 ,l = mNames.length;i<l; i++){
            SettingItem item = new SettingItem();
            item.setIcon(mIcons[i]);
            item.setName(mNames[i]);
            item.setSkip(R.mipmap.skip);
            mList.add(item);
        }
    }
    private void setContentLayout(){
        setTop();
        setCenter();
    }

    private void setTop(){
        mLayoutTop = new LinearLayout(mContext);
        mLayoutTop.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams llp_top = new LayoutParams(Configs.SCREEN_WIDTH,
                Configs.SCREEN_WIDTH*120/720);
        addView(mLayoutTop, llp_top);
        TextView title = new TextView(mContext);
        title.setText(R.string.setting_title);
        title.setTextColor(Color.WHITE);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40 * Configs.SCREEN_WIDTH / 720);
        title.setGravity(Gravity.CENTER);
        mLayoutTop.addView(title);
    }

    private void setCenter(){
        mLayoutContent = new LinearLayout(mContext);
        mLayoutContent.setBackgroundColor(Color.rgb(220, 220, 220));
        LinearLayout.LayoutParams llp_center = new LayoutParams(Configs.SCREEN_WIDTH,
                Configs.SCREEN_HEIGHT-240*Configs.SCREEN_WIDTH/720);
        addView(mLayoutContent, llp_center);

        mLayoutContent.setOrientation(LinearLayout.VERTICAL);

        LinearLayout ll_sign = new LinearLayout(mContext);
        ll_sign.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams llp_sign = new LayoutParams(Configs.SCREEN_WIDTH,
                Configs.SCREEN_WIDTH*180/720);
        llp_sign.setMargins(0, 30 * Configs.SCREEN_WIDTH / 720, 0, 0);
        mLayoutContent.addView(ll_sign, llp_sign);

        ImageView im_icon = new ImageView(mContext);
        im_icon.setBackgroundColor(Color.rgb(245, 107, 96));
        LinearLayout.LayoutParams llp_icon = new LayoutParams(Configs.SCREEN_WIDTH*120/720,
                Configs.SCREEN_WIDTH*120/720);
        llp_icon.setMargins(Configs.SCREEN_WIDTH * 30 / 720, Configs.SCREEN_WIDTH * 30 / 720, 0, 0);
        ll_sign.addView(im_icon, llp_icon);

        TextView tv_title = new TextView(mContext);
        tv_title.setText(R.string.sing_up);
        tv_title.setGravity(Gravity.CENTER);
        tv_title.setTextColor(Color.BLACK);
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * Configs.SCREEN_WIDTH / 720);
        LinearLayout.LayoutParams llp_tv_title = new LayoutParams(Configs.SCREEN_WIDTH*200/720,
                Configs.SCREEN_WIDTH*180/720);
        llp_tv_title.setMargins(Configs.SCREEN_WIDTH * 30 / 720, 0, 0, 0);
        ll_sign.addView(tv_title, llp_tv_title);

        ImageView im_skip = new ImageView(mContext);
        im_skip.setBackgroundResource(R.mipmap.skip);
        LinearLayout.LayoutParams llp_skip = new LayoutParams(Configs.SCREEN_WIDTH*60/720,
                Configs.SCREEN_WIDTH*60/720);
        llp_skip.setMargins(260 * Configs.SCREEN_WIDTH / 720, 60 * Configs.SCREEN_WIDTH / 720, 0, 0);

        ll_sign.addView(im_skip, llp_skip);
        ListView listView = new ListView(mContext);
        LinearLayout.LayoutParams llp_list = new LayoutParams(Configs.SCREEN_WIDTH,
                Configs.SCREEN_WIDTH*612/720);
        llp_list.setMargins(0, 40 * Configs.SCREEN_WIDTH / 720, 0, 0);
        mLayoutContent.addView(listView, llp_list);
        SettingsAdapter adapter = new SettingsAdapter(mList,mContext);
        listView.setAdapter(adapter);
        listView.setBackgroundColor(Color.WHITE);
        listView.setDividerHeight(0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 3:
                        showLanguageDialog();
                        break;
                }
            }
        });
    }

    private void showLanguageDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //    指定下拉列表的显示数据
        final String[] cities = {"中文","English"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                mListener.onLanguageChange(which+1);
                Toast.makeText(mContext, "选择的城市为：" + cities[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();



    }


}
