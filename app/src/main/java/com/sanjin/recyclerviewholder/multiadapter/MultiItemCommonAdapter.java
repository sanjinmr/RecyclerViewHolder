package com.sanjin.recyclerviewholder.multiadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.sanjin.recyclerviewholder.commonadapter.CommonAdapter;
import com.sanjin.recyclerviewholder.commonadapter.ViewHolder;

import java.util.List;

/**
 * Created by dongmingxin on 2018/1/8.
 * Des:
 */
public abstract class MultiItemCommonAdapter<T> extends CommonAdapter<T> {

    public MultiItemTypeSupport<T> multiItemTypeSupport;

    protected OnItemClickListener mOnItemClickListener;

    public MultiItemCommonAdapter(Context context, List<T> datas, MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, -1, datas);
        this.multiItemTypeSupport = multiItemTypeSupport;
    }

    /**
     * 根据position对应的数据返回一个itemType
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (null != multiItemTypeSupport) {
            return multiItemTypeSupport.getItemViewType(position,  mDatas.get(position));
        }
        return super.getItemViewType(position);
    }

    /**
     * 根据ViewType创建ViewHolder实例
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null != multiItemTypeSupport) {
            int layoutId = multiItemTypeSupport.getLayoutId(viewType);
            ViewHolder viewHolder = ViewHolder.get(mContext, parent, layoutId);
            onViewHolderCreated(viewHolder, viewHolder.getConvertView());
            setListener(parent, viewHolder, viewType);
            return viewHolder;
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    // -------- help function ---------------------------------------

    public void onViewHolderCreated(ViewHolder holder,View itemView){

    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    /**
     * 注册点击事件和长按事件的监听器
     * @param parent
     * @param viewHolder
     * @param viewType
     */
    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder , position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder,  int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
