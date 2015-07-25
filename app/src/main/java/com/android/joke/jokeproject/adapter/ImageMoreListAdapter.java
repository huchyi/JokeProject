package com.android.joke.jokeproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.joke.jokeproject.R;
import com.android.joke.jokeproject.common.BaseBean;
import com.android.joke.jokeproject.common.StringUtils;
import com.android.joke.jokeproject.dailog.ImageScanDialog;
import com.android.joke.jokeproject.utils.ImageUtils;
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
    public BaseBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.fragment_image_load_more_adapter, parent, false);
            holder.mSimpleDraweeView = (ImageView) convertView.findViewById(R.id.fragment_image_more_list_iv);
            holder.textView = (TextView) convertView.findViewById(R.id.fragment_image_more_list_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final BaseBean bean = mList.get(position);
        String urlStr = bean.getStr("purl");
        if(urlStr.contains("pengfu.cn/middle")){
            urlStr = urlStr.replace("pengfu.cn/middle","pengfu.cn/big");
        }
        final String title = bean.getStr("ptitle");
        if(!StringUtils.isNullOrNullStr(title)){
            holder.textView.setVisibility(View.VISIBLE);
            holder.textView.setText(title);
        }else{
            holder.textView.setVisibility(View.GONE);
        }
        //加载图片
        ImageLoader.getInstance().displayImage(urlStr, holder.mSimpleDraweeView, posterAudioImgOptions);
//       ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(urlStr)).build();
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setImageRequest(request)
//                .setAutoPlayAnimations(true)
//                .build();
//        holder.mSimpleDraweeView.setController(controller);
        holder.mSimpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toViewBigImage(position);
            }
        });

        return convertView;
    }


    private void toViewBigImage(int position){
        BaseBean bean = getItem(position);
        String url = bean.getStr("purl");
        final ImageScanDialog mCustomDialog = new ImageScanDialog(mContext,url);
        mCustomDialog.setClicklistener(new ImageScanDialog.ClickListenerInterface() {
            @Override
            public void doDismissDialog() {
                mCustomDialog.dismiss();
            }
        });
        mCustomDialog.show();
    }


    private class ViewHolder {
        TextView textView;
        ImageView mSimpleDraweeView;
    }
}