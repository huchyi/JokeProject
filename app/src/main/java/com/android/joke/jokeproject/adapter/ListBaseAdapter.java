package com.android.joke.jokeproject.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.joke.jokeproject.R;
import com.android.joke.jokeproject.common.BaseBean;
import com.android.joke.jokeproject.common.StringUtils;
import com.android.joke.jokeproject.db.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
            holder.collection = (ImageView) convertView.findViewById(R.id.list_item_collection_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final BaseBean bean = mList.get(position);
        final String contentStr = bean.getStr("intor");
        String timeStr = bean.getStr("ptime");
        final String id = bean.getStr("hid");
        if(contentStr != null && timeStr != null){
            holder.cotentTv.setText(contentStr);
            holder.timeTv.setText(timeStr);
        }
        if(DBHelper.getIntences(mContext).isExits(id)){
            holder.collection.setImageResource(R.drawable.collection_img);
        }else{
            holder.collection.setImageResource(R.drawable.uncollection_img);
        }
        holder.collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DBHelper.getIntences(mContext).isExits(id)){
                    //如果存在，则删除，
                    DBHelper.getIntences(mContext).del(id);
                    holder.collection.setImageResource(R.drawable.uncollection_img);
                    Toast.makeText(mContext, "取消收藏", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext, "已收藏", Toast.LENGTH_SHORT).show();
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("name_id", id);
                    map.put("time", StringUtils.getNowData());
                    map.put("name", contentStr);
                    DBHelper.getIntences(mContext).insert(map);
                    holder.collection.setImageResource(R.drawable.collection_img);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView cotentTv;//内容
        TextView timeTv;//时间
        ImageView collection;
    }

}
