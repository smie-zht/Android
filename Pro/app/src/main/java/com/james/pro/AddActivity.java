package com.james.pro;

/**
 * Created by qqq on 2017/11/9.
 */

//import com.litsoft.entity.General;


import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;


public class AddActivity extends ActionBarActivity {


    private EditText etaddname,etadddetail;
    private General general = null;
    private int mImage = R.drawable.hanxin;//测试写成固定的了
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        setlistenser();
    }
    private void setlistenser() {

        findViewById(R.id.addcancleBt).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
            }
        });

        findViewById(R.id.addBt).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = etaddname.getText().toString();
                String detail = etadddetail.getText().toString();
                General general = new General(mImage,name,detail);
                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                intent.putExtra("general", general);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    private void initView() {

        etaddname = (EditText) findViewById(R.id.etaddname);
        etadddetail = (EditText) findViewById(R.id.etadddetail);
    }




}