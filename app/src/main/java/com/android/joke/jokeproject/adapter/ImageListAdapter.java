package com.android.joke.jokeproject.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
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
import com.android.joke.jokeproject.dailog.CustomDialog;
import com.android.joke.jokeproject.db.DBHelper;
import com.android.joke.jokeproject.utils.ImageUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
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
                    R.layout.fragment_image_list_adapter, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.list_tv_title);
            holder.cotentTv = (TextView) convertView.findViewById(R.id.list_tv);
            holder.mSimpleDraweeView = (SimpleDraweeView) convertView.findViewById(R.id.fragment_list_image_content);
            holder.loadMore = (TextView) convertView.findViewById(R.id.list_tv_tip);
            holder.timeTv = (TextView) convertView.findViewById(R.id.list_time_tv);
            holder.collection = (ImageView) convertView.findViewById(R.id.list_item_collection_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final BaseBean bean = mList.get(position);
        final String contentStr = bean.getStr("intor");
        String timeStr = bean.getStr("ptime");
        //final String id = bean.getStr("hid");
        final String title = bean.getStr("htitle");
        // final String type = bean.getStr("ispic");
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
            //ImageLoader.getInstance().displayImage(urlStr,holder.mSimpleDraweeView,posterAudioImgOptions);
            Log.i("hcy","url:"+urlStr);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(urlStr)).build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setAutoPlayAnimations(true)
                    .build();
            holder.mSimpleDraweeView.setController(controller);

            if(listImg.size() > 1){
                holder.loadMore.setVisibility(View.VISIBLE);
                holder.loadMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toViewMoreImage(listImg);
                    }
                });
            }else{
                holder.loadMore.setVisibility(View.GONE);
            }
        }else{
            holder.loadMore.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void toViewMoreImage(ArrayList<BaseBean> listImgBean){
        final CustomDialog mCustomDialog = new CustomDialog(mContext,R.style.Dialog_Fullscreen,listImgBean);
        mCustomDialog.setClicklistener(new CustomDialog.ClickListenerInterface() {
            @Override
            public void doDismissDialog() {
                mCustomDialog.dismiss();
            }
        });
        mCustomDialog.show();
    }

    private class ViewHolder {
        TextView title;//标题
        TextView cotentTv;//内容
        SimpleDraweeView mSimpleDraweeView;
        TextView loadMore;//内容

        TextView timeTv;//时间
        ImageView collection;
    }
}
