package com.james.pro;

/**
 * Created by ER on 2017/11/13.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import static android.R.attr.animation;

/**
 * Created by qqq on 2017/11/13.
 */

public class WelcomeActivity extends Activity {

    boolean isFirstIn = false;
    private Intent intent;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        final View view = View.inflate(this, R.layout.main, null);
        setContentView(view);

        sharedPreferences = this.getSharedPreferences("data",MODE_PRIVATE);
        isFirstIn = sharedPreferences.getBoolean("isFirstIn",true);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("isFirstIn",true);
        editor.commit();
        AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
        aa.setDuration(2000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationEnd(Animation arg0) {
                run();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationStart(Animation animation) {}

        });

    }
    public void run() {
        if (isFirstIn) {
//                    Toast.makeText(TransitionActivity.this, "First log", Toast.LENGTH_SHORT).show();
            intent = new Intent(WelcomeActivity.this, TransitionActivity.class);
            WelcomeActivity.this.startActivity(intent);
            WelcomeActivity.this.finish();

        }
        else {
            intent = new Intent(WelcomeActivity.this,MainActivity.class);
            WelcomeActivity.this.startActivity(intent);
            WelcomeActivity.this.finish();
        }
    }
}

