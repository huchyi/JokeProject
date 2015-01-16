package com.android.joke.jokeproject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;

import com.android.joke.jokeproject.db.DBHelper;

public class MainActivity extends Activity implements OnClickListener{
    private RelativeLayout mTabMainButton;
    private RelativeLayout mTabCollectionButton;

    private MainFragment mainFragment;
    private CollectionFragment collectionFragment;

    private FragmentManager fm;
    private  FragmentTransaction transaction;
    private Fragment mContent;

    private boolean isFirstAdd = true;

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
        fm = getFragmentManager();
        // 设置默认的Fragment
        setDefaultFragment();
    }

    public void switchContent(Fragment from, Fragment to) {
        if (mContent != to) {
            mContent = to;
            transaction = fm.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.id_fragment_main , to).commit(); // 隐藏当前的fragment，add下一个到fragment中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    /**
     * 主界面
     *
     * */
    private void setDefaultFragment(){
        if (mainFragment == null){
            mainFragment = new MainFragment();
        }
        if (collectionFragment == null){
            collectionFragment = new CollectionFragment();
        }
        if(isFirstAdd){
            isFirstAdd = false;
            mContent = new CollectionFragment();
            transaction = fm.beginTransaction();
            transaction.add(R.id.id_fragment_main , collectionFragment).commit(); // 隐藏当前的fragment，add下一个到fragment中
        }
        switchContent(collectionFragment,mainFragment);
        mTabMainButton.setBackgroundColor(getResources().getColor(R.color.bottom_bg));
        mTabCollectionButton.setBackgroundColor(getResources().getColor(R.color.bottom_bg_while));
    }


    /**
     * 次界面
     * **/
    private void setOtherFragment(){
        if (mainFragment == null){
            mainFragment = new MainFragment();
        }
        if (collectionFragment == null){
            collectionFragment = new CollectionFragment();
        }
        switchContent(mainFragment,collectionFragment);
        mTabMainButton.setBackgroundColor(getResources().getColor(R.color.bottom_bg_while));
        mTabCollectionButton.setBackgroundColor(getResources().getColor(R.color.bottom_bg));
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.bleow_two_btn_rl_main:
                // 使用当前Fragment的布局替代bleow_two_btn_rl_collection的控件
                setDefaultFragment();
                break;
            case R.id.bleow_two_btn_rl_collection:
                // 使用当前Fragment的布局替代bleow_two_btn_rl_main的控件
                setOtherFragment();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            // isExit.setTitle("提示");
            // 设置对话框消息
            isExit.setMessage("确定要退出吗?");
            // 添加选择按钮并注册监听
            isExit.setButton(AlertDialog.BUTTON_POSITIVE, "确定", listener);
            isExit.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", listener);
            // 显示对话框
            isExit.show();
        }
        return false;
    }

    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBHelper.getIntences(this).close();
        System.exit(0);//直接结束程序
        //或者下面这种方式
        //android.os.Process.killProcess(android.os.Process.myPid());
    }
}
