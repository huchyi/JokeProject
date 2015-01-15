package com.android.joke.jokeproject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.joke.jokeproject.adapter.ListBaseAdapter;
import com.android.joke.jokeproject.common.BaseBean;
import com.android.joke.jokeproject.db.DBHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class CollectionFragment extends Fragment {

    private View fragmentView;

    private ListView listView;

    private ListBaseAdapter baseAdapter;
    private ProgressDialog progressDialog;
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
        progressDialog = ProgressDialog.show(getActivity(), null, null, true, false);
        new ListFromDBTask().execute();
        return fragmentView;
    }

    public class ListFromDBTask extends AsyncTask<Void, Void, ArrayList<BaseBean>> {
        @Override
        protected ArrayList<BaseBean> doInBackground(Void... arg0) {
            // 从数据库获取数据
            return DBHelper.getIntences(getActivity()).getDataList();
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
            }
            progressDialog.dismiss();
            super.onPostExecute(result);
        }
    }

}
