package com.tencent.qcloud.ugckit.module.record.interfaces;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

/**
 * 定制化"多模式录制的录制按钮"
 */

public interface IRecordButton {

    /**
     * 设置录制按钮监听器
     *
     * @param listener
     */
    void setOnRecordButtonListener(OnRecordButtonListener listener);

    interface OnRecordButtonListener {
        /**
         * 多段录制点击开始
         */
        void onRecordStart();

        /**
         * 多段录制点击暂停
         */
        void onRecordPause();

        /**
         * 拍照
         */
        void onTakePhoto();

        /**
         * 删除
         */
        void onDeleteParts(int partsSize, long duration);
    }

    /**
     * "按住拍摄"外圈颜色
     */
    void setTouchRecordOutterColor(@ColorRes int color);

    /**
     * "按住拍摄"内圈颜色
     */
    void setTouchRecordInnerColor(@ColorRes int color);

}
