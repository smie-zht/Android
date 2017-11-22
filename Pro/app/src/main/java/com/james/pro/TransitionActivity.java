package com.james.pro;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.Button;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.james.pro.MainActivity;
import com.james.pro.R;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by qqq on 2017/11/13.
 */


public class TransitionActivity extends Activity {
    private View view1, view2, view3;
    private ViewPager viewPager;  //对应的viewPager
    private Button btnstarthome;
    private List<View> viewList;//view数组
    private Button btnstarthome1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        LayoutInflater inflater=getLayoutInflater();
        view1 = inflater.inflate(R.layout.layout1, null);
        view2 = inflater.inflate(R.layout.layout2,null);
        view3 = inflater.inflate(R.layout.layout3, null);
        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        btnstarthome= (Button)viewList.get(2).findViewById(R.id.enter);
        btnstarthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到主界面并杀死导航页面
                Intent intent=new Intent(TransitionActivity.this,MainActivity.class);
                TransitionActivity.this.startActivity(intent);
                SharedPreferences sharedPreferences = TransitionActivity.this.getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isFirstIn",false);
                editor.clear();
                editor.commit();
                TransitionActivity.this.finish();
            }
        });
        btnstarthome1= (Button)viewList.get(0).findViewById(R.id.enter1);
        btnstarthome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到主界面并杀死导航页面
                Intent intent=new Intent(TransitionActivity.this,MainActivity.class);
                TransitionActivity.this.startActivity(intent);
                SharedPreferences sharedPreferences = TransitionActivity.this.getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isFirstIn",false);
                editor.clear();
                editor.commit();
                TransitionActivity.this.finish();
            }
        });
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }
            @Override
            public int getCount() {
                return viewList.size();
            }
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(viewList.get(position));
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };
        viewPager.setAdapter(pagerAdapter);
    }
}