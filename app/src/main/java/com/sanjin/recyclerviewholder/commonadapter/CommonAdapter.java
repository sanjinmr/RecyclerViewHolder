package com.sanjin.recyclerviewholder.commonadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by dongmingxin on 2018/1/8.
 * Des: 通用的Adapter
 * 我们的每次使用过程中，针对的数据类型Bean肯定是不同的，
 * 那么这里肯定要引入泛型代表我们的Bean，内部通过一个List代表我们的数据
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    public Context mContext;

    private LayoutInflater mInflater;

    /**
     *  item显示的layId
     */
    public int mLayoutId;

    /**
     * 待展示的数据
     */
    public List<T> mDatas;

    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    /**
     * onCreateViewHolder时，通过layoutId即可利用我们的通用的ViewHolder生成实例
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    /**
     * onBindViewHolder这里主要用于数据、事件绑定，我们这里直接抽象出去，让用户去操作
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 设置ViewHolder当前所属的position
        holder.updatePosition(position);
        // 将数据和ViewHolder绑定在一起（即显示）
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract void convert(ViewHolder holder, T t);
}
