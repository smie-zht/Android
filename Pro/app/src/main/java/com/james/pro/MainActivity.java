package com.james.pro;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//import com.litsoft.entity.General;


import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;


public class MainActivity extends Activity {
    private int DBid ; //记录数据新增对象可用id
    private PeopleCrud DB; //数据库
    private ListView lvGenerals;//listView对象
    private ArrayList<People_info> generals;//将军的集合
    private ArrayList<People_info> back_generals; //将军的备份
    private GeneralAdapter generalAdaper;//适配器
    private FloatingActionButton  Goal;
    private SearchView mSearchView;
    private static final int VIEW_DETAIL = 0;//查看详情
    private static final int VIEW_DETELE = 1;//删除操作
    private static final int VIEW_ADD= 2;//添加操作
    private static final int VIEW_UPDATE = 3;//修改操作
    private static final int ACTION_UPDATE =4;
    private static final int ACTION_ADD =5;
    private ImageView musicbtn;
    private ObjectAnimator rotate; // 唱片旋转动画
    private boolean pp;
    private IBinder mBinder;//代理人岗位
    private ServiceConnection serviceConnect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinder = iBinder;
            Log.v("代理人：","已持证上岗！");
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceConnect = null;
            Log.v("服务连接：","已中断！");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setSubmitButtonEnabled(true);
        musicbtn = (ImageView) findViewById(R.id.music);
        lvGenerals = (ListView) findViewById(R.id.lvGenerals);
        //configure animator
        rotate =ObjectAnimator.ofFloat(musicbtn,"rotation",0,360);
        rotate.setDuration(30000);
        rotate.setInterpolator(new LinearInterpolator());//补间器：线性补间
        rotate.setRepeatCount(-1);//表示不限次数
        rotate.setRepeatMode(ValueAnimator.RESTART);//设定重启模式
        pp = false;
        //this.deleteDatabase("sqlitesanguo.db");
        initDB();
        initView();
        setListener();
        bindService();
        musicbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pp) {
                    pp = false;
                    rotate.pause();
                }
                else {
                    pp = true;
                    if(rotate.isPaused()){
                        rotate.resume();
                    }
                    else rotate.start();
                }

                if(mBinder!=null){
                    try{
                        int code = 1;
                        Parcel data = Parcel.obtain();
                        Parcel reply = Parcel.obtain();
                        mBinder.transact(code,data,reply,0);
                    }catch (RemoteException e){
                        e.printStackTrace();
                    }
                }

            }
        });
        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(TextUtils.isEmpty(query)||DB.getPeopleByName(query).getId()==0){
                    Toast.makeText(MainActivity.this,"查无此人",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,query,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HeroDetial.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name",query);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1);
                }
                return false;
            }
            // 当搜索内容改变时触发该方法(String---->ListView的filter)
            @Override
            public boolean onQueryTextChange(String newText) {

                if (!TextUtils.isEmpty(newText)){
                    if(generals != null){
                        generals = null;
                    }
                    generals = new ArrayList<>();
                    ArrayList<HashMap<String,String>> tem_generals = DB.getpeopleList();
                    Log.v("当前数据量: ",String.valueOf(tem_generals.size()));
                    int i = 1;
                    for(HashMap<String,String> hash:tem_generals){
                        Log.v("匹配搜索 "+hash.get("name"),String.valueOf(i));
                        People_info tem_general = DB.getPeopleByName(hash.get("name"));
                        //如果name包含搜索关键字，则加载到List View
                        if(tem_general.name.contains(newText))
                            generals.add(tem_general);

                        generalAdaper = new GeneralAdapter(MainActivity.this,generals);
                        lvGenerals.setAdapter(generalAdaper);
                    }


                }else{
                    //使用back—generals填充List view
                    generalAdaper = new GeneralAdapter(MainActivity.this,generals);
                    lvGenerals.setAdapter(generalAdaper);
                }
                return true;
            }
        });

    }
    //music
    private void bindService(){
        Intent intent = new Intent (MainActivity.this,MyService.class);
        startService(intent);//开启服务
        bindService(intent,serviceConnect, Context.BIND_AUTO_CREATE);//绑定服务
    }

    private void initDB(){
            DB = new PeopleCrud(MainActivity.this);
            String [] names = getResources().getStringArray(R.array.name);//姓名资源
            String [] infos = getResources().getStringArray(R.array.info);//info
            String [] forces = getResources().getStringArray(R.array.force); //势力
            int [] images = new int [] {
            R.drawable.liubei,R.drawable.guanyu,R.drawable.zhangfei,
            R.drawable.caocao,R.drawable.menghuo,R.drawable.zhuge,
            R.drawable.huangzhong,R.drawable.lvbu,R.drawable.zhaoyun,
            R.drawable.dongzhuo,
            };//图片资源
            String[] mapjs = getResources().getStringArray(R.array.mapj);
            String[] mapws = getResources().getStringArray(R.array.mapw);
            Log.v("输入数据库 mapj1",mapjs[0]);

        //初始化的10个英雄，按照内置资源其id依次为：1—10
        for(int i=0;i<10;i++){
            People_info hero = new People_info();
            hero.complete(i+1,names[i],forces[i],images[i],infos[i],mapjs[i],mapws[i]);
            DB.insert(hero);
        }
        DBid  = 11;
    }

    private void initView() {
        if(generals != null){
            generals = null;
        }
        generals = new ArrayList<>();
        ArrayList<HashMap<String,String>> tem_generals = DB.getpeopleList();
        Log.v("当前数据量: ",String.valueOf(tem_generals.size()));

        int i = 1;
        for(HashMap<String,String> hash:tem_generals){
            Log.v("搜索 "+hash.get("name"),String.valueOf(i));
            People_info tem_general = DB.getPeopleByName(hash.get("name"));
            generals.add(tem_general);
            Log.v("ListView中第i个item - id: ",String.valueOf(i++)+" "+String.valueOf(tem_general.id));
        }
//        back_generals = generals;  //数据备份
        generalAdaper = new GeneralAdapter(this,generals);
        lvGenerals.setAdapter(generalAdaper);
//        lvGenerals.setTextFilterEnabled(true);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("RESULT_ok:",String.valueOf(RESULT_OK));
        Log.v("RESULT_CANCELED",String.valueOf(RESULT_CANCELED));
        Log.v("收到intent：req,res: ",String .valueOf(requestCode)+"  "+String.valueOf(resultCode));

        //删除hore
        if (requestCode == 1 && resultCode == RESULT_CANCELED) {
            initView();
        }
        //新建hore
        else if(requestCode == 0 && resultCode == RESULT_OK){
            initView();
            DBid++;
        }
        //修改or not
        else if(requestCode == 1 && resultCode == RESULT_OK){
            initView();
        }
        else{
            Log.v("无效操作：","nothing serious");
            initView();
        }

    }
   private void setListener() {
       this.lvGenerals.setOnItemClickListener(new OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view,
                                   final int position, long id) {

               Intent intent = new Intent(MainActivity.this, HeroDetial.class);
               People_info info2 = generals.get(position);
               Log.v("position",String.valueOf(position)+"name:  "+generals.get(position).getName());
               Bundle bundle = new Bundle();
               bundle.putString("name", generals.get(position).getName());
               intent.putExtras(bundle);
               startActivityForResult(intent, 1);
           }
       });




////
////
////                    private void showDetail(int position) {
////                                    // TODO Auto-generated method stub
////                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
////                        General general = generals.get(position);
////                        builder.setTitle(general.getName()).setMessage(general.getDetail())
////                                .setPositiveButton("返回", null);
////                        AlertDialog dialog = builder.create();
////                        dialog.show();
////                    }
////                });
////                AlertDialog dialog = builder.create();
////                dialog.show();
////                return true;
////            }
//        });
//
        Goal=(FloatingActionButton) findViewById(R.id.floatingbutton);
        Goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("你将进行添加操作！").setItems(new String[] {"增加操作"}, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(MainActivity.this,update.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("opreation","new");
                                bundle.putString("name","new");
                                bundle.putString("force","new");
                                bundle.putString("info","new");
                                bundle.putInt("id",DBid);
                                bundle.putInt("pimage",R.drawable.wujiang);
                                intent1.putExtras(bundle);
                                startActivityForResult(intent1,0);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
//    }
//
//

    }

}
