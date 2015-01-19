package com.android.joke.jokeproject.dailog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.joke.jokeproject.R;
import com.android.joke.jokeproject.adapter.ImageMoreListAdapter;
import com.android.joke.jokeproject.common.BaseBean;

import java.util.ArrayList;

public class CustomDialog  extends Dialog{


    private ListView listView;

    private TextView textView;

    private ImageMoreListAdapter imageMoreListAdapter;

    private ArrayList<BaseBean> mbean;

    private Context mContext;

    private ClickListenerInterface clickListenerInterface;
    public interface ClickListenerInterface {
        public void doDismissDialog();
    }
    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

/*    public CustomDialog(Context context) {
        super(context);
    }*/

    public CustomDialog(Context context, int theme,ArrayList<BaseBean> bean) {
        super(context, theme);
        this.mContext = context;
        this.mbean = bean;
    }

  /*  protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_image_load_more);
        listView = (ListView) findViewById(R.id.image_more_list);
        textView = (TextView) findViewById(R.id.image_more_list_title);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListenerInterface.doDismissDialog();
            }
        });

        imageMoreListAdapter = new ImageMoreListAdapter(mContext,mbean);
        listView.setAdapter(imageMoreListAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            clickListenerInterface.doDismissDialog();
        }
        return false;
    }

}
