package com.android.joke.jokeproject.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.joke.jokeproject.R;
import com.android.joke.jokeproject.common.BaseBean;
import com.android.joke.jokeproject.common.StringUtils;
import com.android.joke.jokeproject.utils.ImageUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class ImageMoreListAdapter extends BaseAdapter {
    private ArrayList<BaseBean> mList;
    private Context mContext;
    private DisplayImageOptions posterAudioImgOptions;

    public ImageMoreListAdapter(Context context, ArrayList<BaseBean> list) {
        this.mList = list;
        this.mContext = context;
        posterAudioImgOptions = ImageUtils.getDisplayImageOptions(true, R.drawable.loadimage_can_loading_success,
                R.drawable.loadimage_can_loading_error, R.drawable.loadimage_can_loading_error, -1, -1);
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
                    R.layout.fragment_image_load_more_adapter, parent, false);
            holder.mSimpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.fragment_image_more_list_iv);
            holder.textView = (TextView) convertView.findViewById(R.id.fragment_image_more_list_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final BaseBean bean = mList.get(position);
        final String urlStr = bean.getStr("purl");
        final String title = bean.getStr("ptitle");
        if(!StringUtils.isNullOrNullStr(title)){
            holder.textView.setVisibility(View.VISIBLE);
            holder.textView.setText(title);
        }else{
            holder.textView.setVisibility(View.GONE);
        }
        //加载图片
        //ImageLoader.getInstance().displayImage(urlStr, holder.mSimpleDraweeView, posterAudioImgOptions);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(urlStr)).build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .build();
        holder.mSimpleDraweeView.setController(controller);
        return convertView;
    }

    private class ViewHolder {
        TextView textView;
        SimpleDraweeView mSimpleDraweeView;
    }
}