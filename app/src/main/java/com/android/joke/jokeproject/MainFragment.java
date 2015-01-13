package com.android.joke.jokeproject;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.joke.jokeproject.adapter.ListBaseAdapter;
import com.android.joke.jokeproject.common.BaseBean;
import com.android.joke.jokeproject.common.NetworkUtils;
import com.android.joke.jokeproject.http.HttpUtils;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    private View fragmentView;

    private ListView listview;
    private ListBaseAdapter baseAdapter;
    private ArrayList<BaseBean> listData;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        listview = (ListView) getActivity().findViewById(R.id.main_list);

        requestAudioListFromNet();

        return fragmentView;
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

        HttpUtils.getIntences().MainGetData(new HttpUtils.IbackData() {
            @Override
            public void onSuccess(String str) {

            }

            @Override
            public void onFailure(String str) {
                Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT).show();
            }
        });


        //得到结果



        //显示结果
        baseAdapter = new ListBaseAdapter(getActivity(),listData);
        listview.setAdapter(baseAdapter);
        // 设置监听器
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {// 判断是否滑至底部

                    //滑到底部后，第二次请求


                }
            }
        });

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



}
