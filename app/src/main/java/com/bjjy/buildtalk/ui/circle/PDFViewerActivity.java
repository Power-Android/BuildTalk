package com.bjjy.buildtalk.ui.circle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.base.activity.BaseActivity;
import com.bjjy.buildtalk.entity.IEntity;
import com.bjjy.buildtalk.entity.PdfInfoEntity;
import com.bjjy.buildtalk.utils.DialogUtils;
import com.bjjy.buildtalk.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class PDFViewerActivity extends BaseActivity<PDFViewerPresenter> implements PDFViewerContract.View,
        OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {

    @BindView(R.id.toolbar_left_back)
    ImageView mToolbarLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_right_collect)
    ImageView mToolbarRightCollect;
    @BindView(R.id.toolbar_right_share)
    ImageView mToolbarRightShare;
    @BindView(R.id.pdf_view)
    PDFView mPdfView;
    @BindView(R.id.include_toolbar)
    RelativeLayout mIncludeToolbar;
    private PdfInfoEntity mPdfInfoEntity;
    private int pageNumber = 0;
    private String mTheme;
    private boolean mIsCollect;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pdfviewer;
    }

    @Override
    protected void initView() {
        mPdfInfoEntity = getIntent().getParcelableExtra("data");
        mTheme = getIntent().getStringExtra("theme_id");
        mIsCollect = getIntent().getBooleanExtra("isCollect", false);
        if (!TextUtils.isEmpty(mTheme)) {
            mToolbarRightCollect.setVisibility(View.VISIBLE);
            changeCollectIv(mIsCollect);
            mToolbarRightShare.setVisibility(View.VISIBLE);
        }
//        else {
//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mToolbarRightCollect.getLayoutParams();
//            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
//            mToolbarRightCollect.setLayoutParams(layoutParams);
//            mToolbarRightCollect.setVisibility(View.VISIBLE);
//        }
        mToolbarTitle.setText(mPdfInfoEntity.getName());
        mPdfView.setBackgroundColor(Color.LTGRAY);
    }

    private void changeCollectIv(boolean isCollect) {
        if (!isCollect) {
            Glide.with(this).load(R.drawable.collect_def_icon).into(mToolbarRightCollect);
        } else {
            Glide.with(this).load(R.drawable.collect_sel_icon).into(mToolbarRightCollect);
        }
    }

    @Override
    protected void initEventAndData() {
        if (!TextUtils.isEmpty(mTheme)) {
            showLoading();
            mPresenter.downloadPDF(mPdfInfoEntity.getUrl(), mPdfInfoEntity.getName());
        } else {
            displayFromUri(mPdfInfoEntity.getUri());
        }
    }

    @Override
    public void handlerFile(File file) {
        mPdfView.fromFile(file)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .load();
        hideLoading();
    }

    private void displayFromUri(Uri uri) {
        mPdfView.fromUri(uri)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = mPdfView.getDocumentMeta();
        LogUtils.e(meta.toString());
    }

    @Override
    public void onPageError(int page, Throwable t) {
        LogUtils.e("Cannot load page " + page);
    }

    @OnClick({R.id.toolbar_left_back, R.id.toolbar_right_collect, R.id.toolbar_right_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_back:
                finish();
                break;
            case R.id.toolbar_right_collect:
                mPresenter.collectTheme(mTheme);
                break;
            case R.id.toolbar_right_share:
                DialogUtils.shareWebUrl1(mPdfInfoEntity.getUrl(), mPdfInfoEntity.getName(),
                        drawableToBitmap(getResources().getDrawable(R.drawable.pdf_icon)), "PDF", this, SHARE_MEDIA.WEIXIN);
                break;
        }
    }

    @Override
    public void handlerCollectSuccess(IEntity iEntity) {
        mIsCollect = !mIsCollect;
        changeCollectIv(mIsCollect);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}
