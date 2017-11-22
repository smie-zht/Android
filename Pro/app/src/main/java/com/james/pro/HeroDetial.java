package com.james.pro;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class HeroDetial extends AppCompatActivity {
    public ImageView imageView;
    private ImageView heroImg;
    private TextView heroName;
    private TextView heroForce;
    private TextView heroInfo;
    private Button change;
    private Button delete;
    private  Button back;
    private PeopleCrud DB; //数据库
    public People_info hero;
    public String name;
    public String force;
    public double mapj;
    public double mapw;
    public int pimage;
    public String info;
    private Button map;
    public int id;
    private Bundle DetailBundle;
    private boolean changed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hore_detial);

        AssetManager mgr=this.getAssets();//得到AssetManager
        Typeface tf=Typeface.createFromAsset(mgr,"fonts/myfont.ttf");//根据路径得到Typeface

        DB = new PeopleCrud(HeroDetial.this);
        heroImg = (ImageView) findViewById(R.id.heroImg);
        heroName = (TextView) findViewById(R.id.heroName);
        heroName.setTypeface(tf);
        heroForce = (TextView) findViewById(R.id.heroForce);
        heroForce.setTypeface(tf);
        heroInfo = (TextView) findViewById(R.id.heroInfo);
        heroInfo.setTypeface(tf);
        map = (Button)findViewById(R.id.mapbnt);
        change = (Button) findViewById(R.id.heroChange);
        change.setTypeface(tf);
        delete = (Button) findViewById(R.id.heroDelete);
        delete.setTypeface(tf);
        back = (Button) findViewById(R.id.back);
        back.setTypeface(tf);
        imageView = (ImageView) findViewById(R.id.imageView_animation1);
        imageView.setBackgroundResource(R.drawable.animation1_drawable);
        imageView.setVisibility(View.GONE);
        heroInfo.getBackground().setAlpha(100);//0~255透明度值
        final Intent intent = getIntent();    //通过intent，获取消息！！！！
        DetailBundle = intent.getExtras();
        name = DetailBundle.getString("name");
//        Log.v("new_name",name);
        hero = DB.getPeopleByName(name);//hero complted object
        pimage = hero.getImageSrc();
        info = hero.getInfo();
        force = hero.getForce();
        id = hero.getId();
        if(id<11){
            mapj = hero.getMapj();
            Log.v("输入详情页",String.valueOf(mapj));
            mapw = hero.getMapw();
        }
        else{
            mapj = 113.3905684948;
            mapw = 23.0606712441;
        }
        heroImg.setImageResource(hero.getImageSrc());
        heroName.setText(name);
        heroForce.setText(hero.getForce());
        heroInfo.setText(hero.getInfo());
        changed = false;
        heroImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                // 获取AnimationDrawable对象
                AnimationDrawable animationDrawable = (AnimationDrawable)imageView.getBackground();
                // 动画是否正在运行
                if(animationDrawable.isRunning()){
                    //停止动画播放
                    animationDrawable.stop();
                    imageView.setVisibility(View.GONE);
                }
                else{
                    //开始或者继续动画播放
                    imageView.setVisibility(View.VISIBLE);
                    animationDrawable.start();
                }
            }
        });
        map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(HeroDetial.this,"即将前往查看该人物坐标！",Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(HeroDetial.this,Map.class);
                Bundle mBundle = new Bundle();
                mBundle.putDouble("mapj",mapj);
                mBundle.putDouble("mapw",mapw);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changed) {
                    setResult(RESULT_OK, intent);
                } else
                    setResult(RESULT_OK, intent);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HeroDetial.this);
                builder.setTitle("提示：");
                builder.setIcon(hero.getImageSrc());
                builder.setMessage("\n" + "\t你确定将此英雄删除？");
                builder.setNeutralButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DB.delete(hero.getId());
                        Toast.makeText(HeroDetial.this, name + "已被删除", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_CANCELED, intent);
                        finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(HeroDetial.this, "您选择了[取消]", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(HeroDetial.this);
                builder.setTitle("提示：");
                builder.setIcon(hero.getImageSrc());
                builder.setMessage("\n" + "\t你确定修改此英雄？");
                builder.setNeutralButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent nIntent = new Intent(HeroDetial.this, update.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("opreation", "update");
                        bundle.putString("name", name);
                        bundle.putString("force", force);
                        bundle.putString("info", info);
                        bundle.putInt("id", id);
                        bundle.putInt("pimage",pimage);
                        bundle.putString("mapj",String.valueOf(mapj));
                        bundle.putString("mapw",String.valueOf(mapw));
                        nIntent.putExtras(bundle);
                        startActivityForResult(nIntent, 0);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(HeroDetial.this, "您选择了[取消]", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }
    //这个杀千刀的 Intent data 参数
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("RESULT_ok:",String.valueOf(RESULT_OK));
        Log.v("RESULT_CANCELED",String.valueOf(RESULT_CANCELED));
        Log.v("收到intent：req,res: ",String .valueOf(requestCode)+"  "+String.valueOf(resultCode));
        if(requestCode == 0 && resultCode == RESULT_OK){
            changed = true;
            Bundle DetailBundle2= new Bundle();
            DetailBundle2 = data.getExtras();
            heroImg.setImageResource(DetailBundle2.getInt("image"));
            Log.v("force: ",DetailBundle2.getString("name"));
            Log.v("force: ",DetailBundle2.getString("force"));
            heroName.setText(DetailBundle2.getString("name"));
            heroForce.setText(DetailBundle2.getString("force"));
            heroInfo.setText(DetailBundle2.getString("info"));

        }
        else{
            Log.v("无效操作：","nothing serious");

        }

    }
}

