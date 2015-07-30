package com.android.joke.jokeproject.dailog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.android.joke.jokeproject.R;
import com.android.joke.jokeproject.imageview.GestureImageView;
import com.android.joke.jokeproject.utils.ImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by Administrator on 2015/7/25.
 *
 */
public class ImageScanDialog extends Dialog {

    private Context mContext;
    private String mUrl;
    private GestureImageView  gestureImageView;
    private ImageView mLoadingIV;
    private DisplayImageOptions posterAudioImgOptions;

    private ClickListenerInterface clickListenerInterface;
    public interface ClickListenerInterface {
        void doDismissDialog();
    }
    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    public ImageScanDialog(Context context, int theme,String url) {
        super(context, theme);
        this.mUrl = url;
        this.mContext = context;
        posterAudioImgOptions = ImageUtils.getDisplayImageOptions(true,false, R.drawable.loadimage_can_loading_success,
                R.drawable.loadimage_can_loading_error, R.drawable.loadimage_can_loading_error, -1, -1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_scan_dialog_layout);
        mLoadingIV = (ImageView) findViewById(R.id.loadingImageView);
        gestureImageView = (GestureImageView) findViewById(R.id.image);
        if(mUrl.contains("pengfu.cn/middle")){
            mUrl = mUrl.replace("pengfu.cn/middle","pengfu.cn/big");
        }
        ImageView imageView = new ImageView(mContext);
        ImageLoader.getInstance().displayImage(mUrl, imageView, posterAudioImgOptions, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                mLoadingIV.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                mLoadingIV.setVisibility(View.GONE);
                gestureImageView.setImageResource(R.drawable.loadimage_can_loading_error);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                mLoadingIV.setVisibility(View.GONE);
                if (bitmap != null) {
                    gestureImageView.setImageBitmap(bitmap);
                } else {
                    gestureImageView.setImageResource(R.drawable.loadimage_can_loading_error);
                }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                ImageView imageView1 = new ImageView(mContext);
                if(mUrl.contains("pengfu.cn/big")){
                    mUrl = mUrl.replace("pengfu.cn/big","pengfu.cn/middle");
                }
                ImageLoader.getInstance().displayImage(mUrl, imageView1, posterAudioImgOptions, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                        mLoadingIV.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        mLoadingIV.setVisibility(View.GONE);
                        gestureImageView.setImageResource(R.drawable.loadimage_can_loading_error);
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        mLoadingIV.setVisibility(View.GONE);
                        if (bitmap != null) {
                            gestureImageView.setImageBitmap(bitmap);
                        } else {
                            gestureImageView.setImageResource(R.drawable.loadimage_can_loading_error);
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                        mLoadingIV.setVisibility(View.GONE);
                        gestureImageView.setImageResource(R.drawable.loadimage_can_loading_error);
                    }
                });
            }
        });

        gestureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListenerInterface.doDismissDialog();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            clickListenerInterface.doDismissDialog();
        }
        return false;
    }

}
