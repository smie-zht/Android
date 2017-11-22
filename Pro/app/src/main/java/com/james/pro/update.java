package com.james.pro;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.james.pro.R;

public class update extends AppCompatActivity {
    private  Bundle bundle=new Bundle();
    private Intent intent=new Intent();
    public   Bundle bundle2=new Bundle();
    private PeopleCrud db=new PeopleCrud(update.this);
    boolean flag=true;
    private People_info infos=new People_info();
    boolean savechange=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        intent=getIntent();
        bundle=intent.getExtras();
        final int id=bundle.getInt("id");
        final String name=bundle.getString("name");
        final String force=bundle.getString("force");
        final String info=bundle.getString("info");
        final int pimage=bundle.getInt("pimage");
        final String op=bundle.getString("opreation");
        final String mapj ;
        final String mapw;
        if(id<11){
            mapj = bundle.getString("mapj");
            mapw  = bundle.getString("mapw");
        }
        else{
            mapj = String.valueOf(113.3905684948);
            mapw = String.valueOf(23.0606712441);
        }
        final TextInputEditText textname=(TextInputEditText)findViewById(R.id.EditName);
        final TextInputEditText textforce=(TextInputEditText)findViewById(R.id.EditForce);
        final TextInputEditText textinfo=(TextInputEditText)findViewById(R.id.EditInfo);
        ImageView textimage=(ImageView)findViewById(R.id.EditImg);
        Button buttonsave=(Button)findViewById(R.id.Editsave);
        Button buttonreturn =(Button)findViewById(R.id.Editback);

        if(op.equals("update"))
        {
            flag=false;
            textname.setText(name);
            textname.setInputType(InputType.TYPE_NULL);
            //Log.v("force:  ",force);
            textforce.setText(force);
            textinfo.setText(info);
            textimage.setImageResource(pimage);
        }
        else if(op.equals("new"))
        {
            textimage.setImageResource(R.drawable.wujiang);
        }
        //edit
        final AlertDialog.Builder alterDialog = new  AlertDialog.Builder(this);
        textimage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alterDialog.setTitle("添加头像").setMessage("从手机中选择").setPositiveButton("确定",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface v,int i){
                        Toast.makeText(update.this,"s",Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("取消",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface v,int i){
                    }
                }).create();
                alterDialog.show();
            }
        });
        buttonsave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                infos.complete(id,textname.getText().toString(),textforce.getText().toString(),pimage,textinfo.getText().toString(),mapj,mapw);
                bundle2.putString("name",infos.name);
                bundle2.putString("force",infos.force);
                bundle2.putString("info",infos.info);
                bundle2.putInt("id",infos.id);
                bundle2.putInt("image",infos.pimage);
                Toast.makeText(update.this,"保存成功",Toast.LENGTH_LONG).show();
                savechange=true;
            }
        });

        buttonreturn.setOnClickListener(new View.OnClickListener(){
            Intent myintent  = new Intent();
            @Override
            public void onClick(View v){
                if(savechange==false)
                {
                    bundle2.putString("name",name);
                    bundle2.putString("force",force);
                    bundle2.putString("info",info);
                    bundle2.putInt("id",id);
                    bundle2.putInt("image",pimage);
                    infos.complete(id,name,force,pimage,info,mapj,mapw);
                }
                Log.v("update-force: ",bundle2.getString("force"));
                Log.v("update-info: ",bundle2.getString("info"));

                myintent.putExtras(bundle2);

                setResult(RESULT_OK, myintent);
                if(flag==true&&savechange==true)
                    db.insert(infos);
                else if(flag==true&&savechange==false) ;
                else
                {
                    db.update(infos);
                    Log.v("hello,",infos.info);
                }
                finish();
            }
        });
    }
}
