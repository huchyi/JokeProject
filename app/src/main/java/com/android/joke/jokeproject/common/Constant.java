package com.android.joke.jokeproject.common;

import android.os.Environment;

/**
 * Created by zhanghang on 2015/1/17.
 */
public class Constant {
    public static final String CACHEDIR = Environment
            .getExternalStorageDirectory().getPath() + "/joke.project";
    public static final String CACHEDIR_IMG = CACHEDIR + "/imgs/";

    public static final String get_all_data = "111";
    public static final String get_text_data = "112";
    public static final String get_image_data = "113";

}
