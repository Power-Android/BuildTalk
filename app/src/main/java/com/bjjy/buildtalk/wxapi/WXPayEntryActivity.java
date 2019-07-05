package com.bjjy.buildtalk.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.Constants;
import com.bjjy.buildtalk.core.event.PayEvent;
import com.bjjy.buildtalk.utils.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private final String TAG = "WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //WeiXinConstants.APP_ID
        api = WXAPIFactory.createWXAPI(this, "wx24a51a57c203d22a");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.i(TAG, "errCode = " + resp.errCode);
        //最好依赖于商户后台的查询结果
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0){
                EventBus.getDefault().post(new PayEvent(Constants.PAY_SUCCESS));
            }else if (resp.errCode == -1){
                ToastUtils.showShort("支付失败");
            }else if (resp.errCode == -2){
                ToastUtils.showShort("支付取消");
            }else {
                ToastUtils.showShort(resp.errStr);
            }
            finish();
        }
    }
}