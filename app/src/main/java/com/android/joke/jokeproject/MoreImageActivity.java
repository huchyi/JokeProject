package com.android.joke.jokeproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.joke.jokeproject.adapter.ImageMoreListAdapter;
import com.android.joke.jokeproject.common.BaseBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/29.
 *
 */
public class MoreImageActivity  extends Activity{
    ArrayList<BaseBean> beanList;

    private ListView listView;

    private TextView textView;

    private ImageMoreListAdapter imageMoreListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_image);
        getData(getIntent().getExtras());
        listView = (ListView) findViewById(R.id.image_more_list);
        textView = (TextView) findViewById(R.id.image_more_list_title);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(beanList != null){
            imageMoreListAdapter = new ImageMoreListAdapter(MoreImageActivity.this,beanList);
            listView.setAdapter(imageMoreListAdapter);
        }else{
            Toast.makeText(MoreImageActivity.this,"获取为空",Toast.LENGTH_LONG).show();
        }
    }

    private void getData(Bundle bundle){
        try{
            beanList = (ArrayList<BaseBean>) bundle.getSerializable("data");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            finish();
        }
        return false;
    }

}
