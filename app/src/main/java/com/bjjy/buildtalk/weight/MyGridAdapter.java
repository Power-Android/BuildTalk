package com.bjjy.buildtalk.weight;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * @author power
 * @date 2019/5/23 3:29 PM
 * @project BuildTalk
 * @description:
 */
public class MyGridAdapter extends BaseAdapter {
    private List<Integer> list;
//    int imageWidth;

    public MyGridAdapter(List<Integer> imgList) {
        this.list = imgList;
//        imageWidth = (UIUtil.getScreenWidth(mContext) - UIUtil.dip2px(mContext, 38)) / 3;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_grid_view_layout, null);
            holder.iv = convertView.findViewById(R.id.image);
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.iv.getLayoutParams();
//            params.width = imageWidth;
//            params.height = imageWidth;
//            holder.iv.setLayoutParams(params);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();

        holder.iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(App.getContext()).load(list.get(position)).into(holder.iv);
//        if (position >= 2 && list.size() > 3) {
//            holder.tv.setVisibility(View.VISIBLE);
//            holder.tv.setText("+" + (list.size() - 3));
//        } else {
//            holder.tv.setVisibility(View.GONE);
//        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv;
//        TextView tv;
    }
}
