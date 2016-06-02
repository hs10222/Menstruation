package com.cisco.menstruation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cisco.menstruation.configs.Configs;
import com.cisco.menstruation.data.SettingItem;

import java.util.List;

/**
 * Created by Administrator on 2016/5/7.
 */
public class SettingsAdapter extends BaseAdapter{
    private List<SettingItem> mList;
    private Context mContext;
    public SettingsAdapter(List<SettingItem> list,Context context){
        mList = list;
        mContext = context;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder mHolder = null;
        if(convertView == null){
            convertView = new LinearLayout(mContext);
            ((LinearLayout)convertView).setOrientation(LinearLayout.VERTICAL);
            LinearLayout ll_content = new LinearLayout(mContext);
            LinearLayout.LayoutParams llp_content = new LinearLayout.LayoutParams(
                    Configs.SCREEN_WIDTH,Configs.SCREEN_WIDTH*100/720);
            ((LinearLayout)convertView).addView(ll_content,llp_content);

            mHolder = new Holder();
            mHolder.im_icon = new ImageView(mContext);
            LinearLayout.LayoutParams llp_im_icon = new LinearLayout.LayoutParams(
                    Configs.SCREEN_WIDTH*60/720,Configs.SCREEN_WIDTH*60/720);
            llp_im_icon.setMargins(Configs.SCREEN_WIDTH * 20 / 720, Configs.SCREEN_WIDTH * 20 / 720,
                    Configs.SCREEN_WIDTH * 40 / 720, 0);
            ll_content.addView(mHolder.im_icon, llp_im_icon);

            mHolder.tv_name = new TextView(mContext);
            mHolder.tv_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, 30 * Configs.SCREEN_WIDTH / 720);
            mHolder.tv_name.setGravity(Gravity.CENTER|Gravity.LEFT);
            mHolder.tv_name.setTextColor(Color.BLACK);
            LinearLayout.LayoutParams llp_tv_name = new LinearLayout.LayoutParams(
                    Configs.SCREEN_WIDTH*200/720,100*Configs.SCREEN_WIDTH/720);
            ll_content.addView(mHolder.tv_name,llp_tv_name);

            mHolder.im_skip = new ImageView(mContext);
            LinearLayout.LayoutParams llp_im_skip = new LinearLayout.LayoutParams(
                    Configs.SCREEN_WIDTH * 60 / 720,Configs.SCREEN_WIDTH * 60 / 720);
            llp_im_skip.setMargins(320 * Configs.SCREEN_WIDTH / 720, 20 * Configs.SCREEN_WIDTH / 720, 0, 0);
            ll_content.addView(mHolder.im_skip, llp_im_skip);

            mHolder.tv_line = new TextView(mContext);
            LinearLayout.LayoutParams llp_tv_line = new LinearLayout.LayoutParams(
                    Configs.SCREEN_WIDTH*700/720,Configs.SCREEN_WIDTH*2/720);
            llp_tv_line.setMargins(20*Configs.SCREEN_WIDTH/720,0,0,0);
            ((LinearLayout)convertView).addView(mHolder.tv_line, llp_tv_line);
            convertView.setTag(mHolder);
        }else{
            mHolder = (Holder) convertView.getTag();
        }
        mHolder.im_icon.setBackgroundResource(mList.get(position).getIcon());
        mHolder.tv_name.setText(mList.get(position).getName());
        mHolder.im_skip.setBackgroundResource(mList.get(position).getSkip());
        mHolder.tv_line.setBackgroundColor(Color.rgb(220,220,220));
        return convertView;
    }

    class Holder{
        ImageView im_icon;
        TextView tv_name;
        ImageView im_skip;
        TextView tv_line;
    }
}
