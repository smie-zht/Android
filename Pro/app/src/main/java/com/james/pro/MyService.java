package com.james.pro;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.Toast;

public class MyService extends Service {
    public MediaPlayer player = null;
    public IBinder mBinder = new MyBinder();
//    public int isStop = 0;

    @Override
    public void onCreate(){ super.onCreate();}
    public MyService() {
        player = new MediaPlayer();
        try{
            //获取内置sd卡的路径
            String sd = Environment.getExternalStorageDirectory().toString();
            player.setDataSource(sd+"/Music/melt.mp3");
            player.prepare();//准备
            player.seekTo(0);//从0开始
            player.setLooping(true);//循环播放
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    //定义代理人
    public class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flag)
                throws RemoteException {
            switch (code){
                case 1:
                    playPause();
                    break;
            }
            return super.onTransact(code,data,reply,flag);
        }
    }
    public  void playPause(){
//        if(player==null){
//            player = new MediaPlayer();
//            Toast.makeText(MyService.this,"音乐加载中。。。",Toast.LENGTH_SHORT).show();
//            try{
//                //获取内置sd卡的路径
//                String sd = Environment.getExternalStorageDirectory().toString();
//                player.setDataSource(sd+"/Music/melt.mp3");
//                player.prepare();//准备
//                player.seekTo(0);//从0开始
//                player.setLooping(true);//循环播放
//                player.start();//开始播放
//                Toast.makeText(MyService.this,"即将播放",Toast.LENGTH_LONG).show();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        else{
            if(player.isPlaying()){
                Toast.makeText(MyService.this,"暂停播放",Toast.LENGTH_LONG).show();
                player.pause();
            }
            else{
                player.start();
                Toast.makeText(MyService.this,"开始播放",Toast.LENGTH_LONG).show();
            }
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player != null) {
            player.stop();
            player.reset();
            player.release();
            player = null;
        }
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}

