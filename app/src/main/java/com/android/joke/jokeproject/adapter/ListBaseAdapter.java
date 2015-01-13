package com.android.joke.jokeproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.joke.jokeproject.R;
import com.android.joke.jokeproject.common.BaseBean;

import java.util.ArrayList;


public class ListBaseAdapter extends BaseAdapter {

    private ArrayList<BaseBean> mList ;
    private Context mContext;

    public ListBaseAdapter(Context context , ArrayList<BaseBean> list){
        this.mList = list;
        this.mContext = context;

    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.fragment_main_listadpter, parent, false);
            holder.cotentTv = (TextView) convertView.findViewById(R.id.list_tv);
            holder.timeTv = (TextView) convertView.findViewById(R.id.list_time_tv);
            convertView.setTag(holder);
            // 拉伸出来的button
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final BaseBean bean = mList.get(position);
        String contentStr = bean.getStr("");
        String timeStr = bean.getStr("");
        if(contentStr != null && timeStr != null){
            holder.cotentTv.setText(contentStr);
            holder.timeTv.setText(timeStr);
        }
        return convertView;
    }


    private class ViewHolder {
        TextView cotentTv;//内容
        TextView timeTv;//时间
    }


}
