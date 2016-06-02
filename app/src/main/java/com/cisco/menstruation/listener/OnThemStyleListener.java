package com.cisco.menstruation.listener;

/**
 * app them change listener
 * when user click the change themstyle
 * button,add the ThemSyleListener in
 * the view,then them self to change
 * backgroundcolor or drawable or text
 * Created by Cisco.hu on 2016/4/23.
 */
public interface OnThemStyleListener {
    //use the code to flag the them
    //forexample 1 night, 2 day,3 others...
    void onThemStyleChange(int code);
}
