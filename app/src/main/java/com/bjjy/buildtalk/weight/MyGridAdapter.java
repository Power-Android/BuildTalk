package com.bjjy.buildtalk.weight;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bjjy.buildtalk.R;
import com.bjjy.buildtalk.app.App;
import com.bjjy.buildtalk.entity.ThemeImageBean;
import com.bjjy.buildtalk.utils.SizeUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * @author power
 * @date 2019/5/23 3:29 PM
 * @project BuildTalk
 * @description:
 */
public class MyGridAdapter extends BaseAdapter {
    private List<ThemeImageBean> list;
    int imageWidth;
    private boolean isDel;
    private DeleteCallBackListener mDeleteCallBackListener;

    public MyGridAdapter(List<ThemeImageBean> imgList, boolean isDel) {
        this.list = imgList;
        this.isDel = isDel;
        if (isDel){
            imageWidth = (SizeUtils.getScreenWidth() - SizeUtils.dp2px(30)) / 3;
        }else {
            float dimension = App.getContext().getResources().getDimension(R.dimen.dp_50);
            imageWidth = (SizeUtils.getScreenWidth() - (int)dimension) / 3;
        }
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
            holder.delete_iv = convertView.findViewById(R.id.delete_iv);
            if (isDel){
                holder.delete_iv.setVisibility(View.VISIBLE);
            }else {
                holder.delete_iv.setVisibility(View.GONE);
            }
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.iv.getLayoutParams();
            params.width = imageWidth;
            params.height = imageWidth;
            holder.iv.setLayoutParams(params);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();

        holder.iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(App.getContext()).load(list.get(position).getPic_url()).into(holder.iv);
        holder.delete_iv.setOnClickListener(v -> mDeleteCallBackListener.deleteCallBack(position));
        return convertView;
    }

    public interface DeleteCallBackListener {
        void deleteCallBack(int position);
    }

    public void setDeleteCallBack(DeleteCallBackListener deleteCallBackListener){
        this.mDeleteCallBackListener = deleteCallBackListener;
    }

    class ViewHolder {
        ImageView iv, delete_iv;
    }
}
