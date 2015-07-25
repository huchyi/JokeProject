package com.android.joke.jokeproject;

import android.app.Fragment;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.joke.jokeproject.adapter.ListBaseAdapter;
import com.android.joke.jokeproject.common.BaseBean;
import com.android.joke.jokeproject.common.Constant;
import com.android.joke.jokeproject.common.NetworkUtils;
import com.android.joke.jokeproject.http.HttpUtils;

import java.util.ArrayList;


public class MainFragment extends Fragment implements View.OnClickListener {

    private View fragmentView;

    private ListView listview;
    private ImageView loadImg;
    private LinearLayout refreshll;
    private ListBaseAdapter baseAdapter;
    private ArrayList<BaseBean> listData;

    private static int mCount = 1;
    private boolean isRequest = true;
    private boolean isFirstLoad = true;

    private AnimationDrawable anim;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        listview = (ListView) fragmentView.findViewById(R.id.main_list);
        refreshll = (LinearLayout) fragmentView.findViewById(R.id.refresh);
        refreshll.setOnClickListener(this);
        loadImg = (ImageView) fragmentView.findViewById(R.id.loadingImageView);
        loadImg.setBackgroundResource(R.drawable.progress_round);

        listData = new ArrayList<>();
        requestAudioListFromNet();

        return fragmentView;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.refresh:
                refreshll.setVisibility(View.GONE);
                requestAudioListFromNet();
                break;
            default:
                break;
        }
    }


    /**
     * 数据的请求
     * */
    private void requestAudioListFromNet(){
        // 是否联网
        if (!NetworkUtils.isConnected(getActivity())) {
            Toast.makeText(getActivity(),"请检查网络连接",Toast.LENGTH_SHORT).show();
            return;
        }
        //参数的拼装和请求
        LoadStart();
        HttpUtils.getIntences().MainGetData(getActivity(), Constant.get_text_data,mCount, new HttpUtils.IbackData() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                LoadStop();
                mCount++;
                isRequest = true;
                isFirstLoad = false;
                //得到结果
                ArrayList<BaseBean> data = (ArrayList<BaseBean>) baseBean.get("items");
                listData.addAll(data);
                //显示结果
                if (baseAdapter == null) {
                    baseAdapter = new ListBaseAdapter(getActivity(), listData);
                    listview.setAdapter(baseAdapter);
                    // 设置监听器
                    listview.setOnScrollListener(new AbsListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView view, int scrollState) {
                        }

                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                            if (firstVisibleItem + visibleItemCount == totalItemCount) {// 判断是否滑至底部
                                //滑到底部后，再次请求
                                if (isRequest) {
                                    isRequest = false;
                                    requestAudioListFromNet();
                                }
                            }
                        }
                    });
                } else {
                    baseAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String str) {
                LoadStop();
                isRequest = true;
                if(isFirstLoad){
                    refreshll.setVisibility(View.VISIBLE);
                }
                Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LoadStart(){
        loadImg.setVisibility(View.VISIBLE);
        if(anim == null){
            anim = (AnimationDrawable) loadImg.getBackground();
            anim.start();
        }else if(anim != null && !anim.isRunning()){
            anim.start();
        }

    }

    private void LoadStop(){
        loadImg.setVisibility(View.GONE);
        if(anim.isRunning()){
            anim.stop();
        }

    }


}
