package com.android.joke.jokeproject;

import android.os.Bundle;
import android.view.View;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener
{
    private RelativeLayout mTabMainButton;
    private RelativeLayout mTabCollectionButton;

    private MainFragment mainFragment;
    private CollectionFragment collectionFragment;

    private FragmentManager fm;
    private  FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // 初始化控件和声明事件
        mTabMainButton = (RelativeLayout) findViewById(R.id.bleow_two_btn_rl_main);
        mTabCollectionButton = (RelativeLayout) findViewById(R.id.bleow_two_btn_rl_collection);
        mTabMainButton.setOnClickListener(this);
        mTabCollectionButton.setOnClickListener(this);

        // 设置默认的Fragment
        setDefaultFragment();
    }

    /**
     * 主界面
     *
     * */
    private void setDefaultFragment(){
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        if (mainFragment == null){
            mainFragment = new MainFragment();
        }
        transaction.replace(R.id.id_fragment_main, mainFragment);
        // transaction.addToBackStack();
        // 事务提交
        transaction.commitAllowingStateLoss();
        mTabMainButton.setBackgroundColor(getResources().getColor(R.color.bottom_bg));
        mTabCollectionButton.setBackgroundColor(getResources().getColor(R.color.bottom_bg_while));
    }


    /**
     * 次界面
     * **/
    private void setOtherFragment(){
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        if (collectionFragment == null){
            collectionFragment = new CollectionFragment();
        }
        transaction.replace(R.id.id_fragment_collection, collectionFragment);
        // transaction.addToBackStack();
        // 事务提交
        transaction.commitAllowingStateLoss();
        mTabMainButton.setBackgroundColor(getResources().getColor(R.color.bottom_bg_while));
        mTabCollectionButton.setBackgroundColor(getResources().getColor(R.color.bottom_bg));
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.bleow_two_btn_rl_main:
                // 使用当前Fragment的布局替代id_content的控件
                setDefaultFragment();

                break;
            case R.id.bleow_two_btn_rl_collection:
                setOtherFragment();

                break;
        }
    }

}
