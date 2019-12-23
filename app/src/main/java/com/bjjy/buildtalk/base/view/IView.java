package com.bjjy.buildtalk.base.view;

/**
 * @author power
 * @date 2019/4/25 9:05 AM
 * @project BuildTalk
 * @description: view接口
 */
public interface IView {
    /**
     * @param errorMsg
     */
    void showErrorMsg(String errorMsg);

    void showLoading();

    void hideLoading();

    void showError();

    void showNoNetwork();

    void showEmpty();

    void showNormal();

    void showPlayer(String songId);

    void hidePlayer();
}
