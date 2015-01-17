package com.android.joke.jokeproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.joke.jokeproject.R;
import com.android.joke.jokeproject.common.BaseBean;
import com.android.joke.jokeproject.common.StringUtils;
import com.android.joke.jokeproject.db.DBHelper;
import com.android.joke.jokeproject.utils.ImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImageListAdapter extends BaseAdapter{
    private ArrayList<BaseBean> mList ;
    private Context mContext;
    private DisplayImageOptions posterAudioImgOptions;

    public ImageListAdapter(Context context , ArrayList<BaseBean> list){
        this.mList = list;
        this.mContext = context;
        posterAudioImgOptions = ImageUtils.getDisplayImageOptions(true, R.drawable.refresh_image,
                R.drawable.refresh_image, R.drawable.refresh_image, -1, -1);
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
                    R.layout.fragment_image_list_adapter, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.list_tv_title);
            holder.cotentTv = (TextView) convertView.findViewById(R.id.list_tv);
            holder.contenImage = (ImageView) convertView.findViewById(R.id.fragment_list_image_content);
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
        final String title = bean.getStr("htitle");
        final String type = bean.getStr("ispic");
        final ArrayList<BaseBean> listImg = (ArrayList<BaseBean>) bean.get("image");
        if(title != null){
            holder.title.setText(title);
        }else{
            holder.title.setText("无标题");
        }
        if(contentStr != null && timeStr != null){
            holder.cotentTv.setText(contentStr);
            holder.timeTv.setText(timeStr);
        }
        //加载图片
        String urlStr;
        if(listImg !=null && listImg.size()>0){
            urlStr = listImg.get(0).getStr("purl");
            if(urlStr != null){
                ImageLoader.getInstance().displayImage(urlStr,holder.contenImage,posterAudioImgOptions);
            }
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
                    Map<String,String> map = new HashMap<>();
                    map.put("hid", id);
                    map.put("htitle", title);
                    map.put("ispic", type);
                    map.put("ptime", StringUtils.getNowData());
                    map.put("intor", contentStr);
                    StringBuilder purl = new StringBuilder();
                    StringBuilder ptitle = new StringBuilder();
                    for (int i=0;i< listImg.size();i++){
                        BaseBean beanl = listImg.get(i);
                        purl.append(beanl.getStr("purl")).append(";;");
                        ptitle.append(beanl.getStr("ptitle")).append(";;");
                    }
                    purl.delete(purl.length()-2,purl.length());
                    ptitle.delete(ptitle.length()-2,ptitle.length());
                    map.put("purl", purl.toString());
                    map.put("ptitle", ptitle.toString());
                    DBHelper.getIntences(mContext).insert(map);
                    holder.collection.setImageResource(R.drawable.collection_img);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView title;//标题
        TextView cotentTv;//内容
        ImageView contenImage;
        TextView timeTv;//时间
        ImageView collection;
    }
}
