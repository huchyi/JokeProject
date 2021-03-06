package com.android.joke.jokeproject;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.joke.jokeproject.adapter.ListBaseAdapter;
import com.android.joke.jokeproject.common.BaseBean;
import com.android.joke.jokeproject.db.DBHelper;

import java.util.ArrayList;
import java.util.Collections;


public class CollectionFragment extends Fragment {

    private View fragmentView;

    private ListView listView;
    private TextView noDataTV;

    private ListBaseAdapter baseAdapter;
    private ArrayList<BaseBean> audioList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_collection, container, false);
        listView = (ListView) fragmentView.findViewById(R.id.collection_list);
        noDataTV = (TextView) fragmentView.findViewById(R.id.collection_list_no_data);
        new ListFromDBTask().execute();
        return fragmentView;
    }

    public class ListFromDBTask extends AsyncTask<Void, Void, ArrayList<BaseBean>> {
        @Override
        protected ArrayList<BaseBean> doInBackground(Void... arg0) {
            // 从数据库获取数据
            return DBHelper.getIntences(getActivity()).getDataList("0");
        }

        @Override
        protected void onPostExecute(ArrayList<BaseBean> result) {
            if (result != null && result.size() > 0) {
                if (audioList == null) {
                    audioList = new ArrayList<>();
                }
                Collections.reverse(result);
                audioList.addAll(result);
                baseAdapter = new ListBaseAdapter(getActivity(), audioList);
                listView.setAdapter(baseAdapter);
            }else{
                noDataTV.setVisibility(View.VISIBLE);
            }
            super.onPostExecute(result);
        }
    }

}
