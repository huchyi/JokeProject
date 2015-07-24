package com.android.joke.jokeproject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2015/7/24.
 *
 */
public class CustomImageview  extends View{
    public CustomImageview(Context context) {
        super(context);
    }

    public CustomImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomImageview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
