package com.test.login.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * 사용방법
 *
 * ProgressBarShow.getProgressBarShowSingleton(getContext()).show(뷰그룹);
 *
 * 로 ProgressBar 보이게
 *
 * ProgressBarShow.getProgressBarShowSingleton(getContext()).remove(뷰그룹);
 *
 * 로 ProgressBar 안보이게
 */

public class ProgressBarShow {
    private static ProgressBarShow progressBarShow;
    private static ProgressBar progressBar;
    private Context context;

    private ProgressBarShow(Context context){
        this.context = context;
        progressBar = new ProgressBar(context, null);
        createProgressBar();
    }

    private void createProgressBar(){
        ViewGroup.LayoutParams edittext_layout_params =
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        progressBar.setLayoutParams(edittext_layout_params);
    }

    public static ProgressBarShow getProgressBarShowSingleton(Context context){
        if(progressBarShow == null){
            progressBarShow = new ProgressBarShow(context);
        }
        return progressBarShow;
    }

    public void show(ViewGroup vg){
        vg.addView(progressBar);
    }

    public void remove(ViewGroup vg){
        vg.removeView(progressBar);
    }
}
