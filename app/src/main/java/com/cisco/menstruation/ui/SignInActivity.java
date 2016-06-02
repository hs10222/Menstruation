package com.cisco.menstruation.ui;

import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/**
 * 登录界面
 * Created by Cisco.hu on 2016/4/24.
 */
public class SignInActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
