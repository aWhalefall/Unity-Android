package com.daimaqiankun;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimaqiankun.mydemo.UnityPlayerActivity;
import com.unity3d.player.UnityPlayer;

import java.util.Locale;

/**
 * 注意事项
 * 7.在调用非MainActivity的类对象里的函数时，由于有UI的处理和回调。所以必须在Unity发Android的函数里，
 * 参数存在变量里，然后mHandler.sendMessage(),最后在handler里调用。
 */

public class UntiyActivity extends UnityPlayerActivity {

    private LinearLayout u3dLayout;
    private Button zoomInBtn;
    private Button zoomOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_untiy);
        u3dLayout = (LinearLayout) findViewById(R.id.u3d_layout);
        u3dLayout.addView(mUnityPlayer);
        mUnityPlayer.requestFocus();
        zoomInBtn = (Button) findViewById(R.id.zoom_in_btn);
        zoomOutBtn = (Button) findViewById(R.id.zoom_out_btn);
        zoomInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnityPlayer.UnitySendMessage("Canvas", "requestLogin", "");
            }
        });
        zoomOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnityPlayer.UnitySendMessage("Canvas", "requestLogin", "");
            }
        });
    }

    public void ExitGame() {
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    /***
     *  unity发消息给app
     */
    public void toAppForFinishOutfit() {
        toast("toAppForFinishOutfit");
    }

    //1.8、unity 版本号接口
    public void toAppForUnityVersion() {
        toast("toAppForUnityVersion");
    }

    //unity可以开始游戏
    public String toAppForStartGame(String gameId) {
        toast("toAppForStartGame");
        return "开始游戏";
    }

    //正常退出游戏
    public void toAppForQuitGame() {
        toast("toAppForQuitGame");
    }


    //参数1:GameObject名称，参数2：函数名，参数3:字符串参数
    public void requestLogin() {
        UnityPlayer.UnitySendMessage("", "requestLogin", "123456");
    }


    private void toast(String message, Object... args) {
        final Toast toast = Toast.makeText(this,
                (args.length == 0) ? message : String.format(Locale.US, message, args),
                Toast.LENGTH_SHORT);
        if (Looper.myLooper() != Looper.getMainLooper()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast.show();
                }
            });
        } else {
            toast.show();
        }
    }

    /**
     * 按键点击事件
     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            onDestroy();
//        }
//        return true;
//    }

    @Override protected void onNewIntent(Intent intent)
    {
        // To support deep linking, we need to make sure that the client can get access to
        // the last sent intent. The clients access this through a JNI api that allows them
        // to get the intent set on launch. To update that after launch we have to manually
        // replace the intent with the one caught here.
        setIntent(intent);
    }

    // Quit Unity
    @Override protected void onDestroy ()
    {
        mUnityPlayer.destroy();
        super.onDestroy();
    }

    // Pause Unity
    @Override protected void onPause()
    {
        super.onPause();
        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override protected void onResume()
    {
        super.onResume();
        mUnityPlayer.resume();
    }

    @Override protected void onStart()
    {
        super.onStart();
        mUnityPlayer.start();
    }

    @Override protected void onStop()
    {
        super.onStop();
        mUnityPlayer.stop();
    }

    // Low Memory Unity
    @Override public void onLowMemory()
    {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL)
        {
            mUnityPlayer.lowMemory();
        }
    }

    // This ensures the layout will be correct.
    @Override public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
    /*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}
