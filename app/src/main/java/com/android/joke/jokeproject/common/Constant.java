package com.android.joke.jokeproject.common;

import android.os.Environment;

/**
 * Created by zhanghang on 2015/1/17.
 */
public class Constant {
    public static final String CACHEDIR = Environment
            .getExternalStorageDirectory().getPath() + "/joke.project";
    public static final String CACHEDIR_IMG = CACHEDIR + "/imgs/";
}
