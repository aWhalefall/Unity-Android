package com.daimaqiankun;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.daimaqiankun.mydemo.UnityPlayerActivity;
import com.unity3d.player.UnityPlayer;

public class MainActivity extends AppCompatActivity {


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1000:
                    UnityPlayer.UnitySendMessage("Android_IAPManager", "SimpleReturn", "987:");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 未在主类中
     */
    public void ShowChukongPay_Sub() {
        Message msg = new Message();
        msg.what = -1000;
        mHandler.sendMessage(msg);
    }


    public void startUnity(View view) {
        startActivity(new Intent(this, UnityPlayerActivity.class));
    }
}
