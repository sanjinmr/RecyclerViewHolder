package com.sanjin.recyclerviewholder.commonadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by dongmingxin on 2018/1/8.
 * Des: 通用的ViewHolder
 * 设计思路：
 * 1、ViewHolder继承于RecyclerView.ViewHolder，内部通过SparseArray来缓存我们itemView内部的子View。
 * 2、每次需要创建ViewHolder，只需要将layoutId传进来即可
 */
public class ViewHolder extends RecyclerView.ViewHolder  {

    /**
     * 缓存itemview内部的子view
     */
    private SparseArray<View> mViews;
    /**
     * 整个item的lay对象
     */
    private View mConvertView;

    private Context mContext;

    /**
     * 当前item的位置
     */
    private int position;

    public ViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }

    /**
     * 获取ViewHolder实例
     * @param context
     * @param parent
     * @param layoutId
     * @return
     */
    public static ViewHolder get(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder  holder = new ViewHolder(context, itemView, parent);
        return holder;
    }

    /**
     * 获取itemView内部的子View
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (null == null) {
            view = mConvertView.findViewById(viewId);
            // 将view缓存在SparseArray中，会造车内存的过分开销吗？
            // 适合那些获取控件后，只把对象设为局部变量的情况
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 更新当前item的位置
     * @param position
     */
    public void updatePosition(int position) {
        this.position = position;
    }

    /**
     * 设置textView显示content
     * @param viewId
     * @param content
     * @return
     */
    public ViewHolder setText(int viewId, String content) {
        TextView textView = getView(viewId);
        textView.setText(content);;
        return this;
    }

    /**
     * 设置imageview显示resId
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolder setImageResourse(int viewId, int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    /**
     * 为view注册点击事件监听器
     * @param viewId
     * @param listener
     * @return
     */
    public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public View getConvertView() {
        return mConvertView;
    }


}
