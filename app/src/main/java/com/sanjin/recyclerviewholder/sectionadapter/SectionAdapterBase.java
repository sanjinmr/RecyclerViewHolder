package com.sanjin.recyclerviewholder.sectionadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.sanjin.recyclerviewholder.commonadapter.ViewHolder;
import com.sanjin.recyclerviewholder.multiadapter.MultiItemCommonAdapter;
import com.sanjin.recyclerviewholder.multiadapter.MultiItemTypeSupport;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dongmingxin on 2018/1/8.
 * Des:
 */
public abstract class SectionAdapterBase<T> extends MultiItemCommonAdapter<T> {

    protected final static int TYPE_SECTION = 0;
    protected SectionSupport<T> sectionSupport;
    protected LinkedHashMap<String, Integer> mSections;

    public SectionAdapterBase(Context context, int layoutId, List<T> datas, SectionSupport<T> sectionSupport) {
        super(context, datas, null);
        this.mLayoutId = layoutId;
        this.sectionSupport = sectionSupport;
        this.multiItemTypeSupport = headerItemSupport();
        this.mSections = new LinkedHashMap<>();
        findSections();
        registerAdapterDataObserver(observer);
    }

    protected abstract MultiItemTypeSupport<T> headerItemSupport();

    @Override
    public int getItemViewType(int position) {
        return multiItemTypeSupport.getItemViewType(position, null);
    }

    /**
     * getItemCount()方法，我们多了几个title肯定总数会增加，所以需要复写。
     * @return
     */
    @Override
    public int getItemCount() {
        // 在原先的数据基础上加上分类标题的数量
        return super.getItemCount() + mSections.size();
    }

    /**
     * 在onBindViewHolder中我们有一行重置position的代码，
     * 因为我们的position变大了，所以在实际上绑定我们数据时，
     * 这个position需要还原，代码逻辑见getIndexForPosition(position)。
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        position = getIndexForPosition(position);
        if (holder.getItemViewType() == TYPE_SECTION) {
            // 设置显示分类标题
            holder.setText(sectionSupport.sectionTitleTextViewId(), sectionSupport.getTitle(mDatas.get(position)));
            return;
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    protected boolean isEnabled(int viewType) {
        if (viewType == TYPE_SECTION)
            return false;
        return super.isEnabled(viewType);
    }

    /**
     * 每当我们的数据发生变化，我们的title集合，即mSections就可能会发生变化，所以需要重新生成，
     * notifyDataSetChanged这个方法是final的，于是利用了registerAdapterDataObserver(observer),
     * 在数据发生变化回调中重新生成，记得在onDetachedFromRecyclerView里面对注册的observer进行解注册。
     * @param recyclerView
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        unregisterAdapterDataObserver(observer);
    }

    // -------- help function -------------------------------------

    final RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            findSections();
        }
    };

    /**
     * 储存分类标题和对应的位置
     * 主要为了存储我们的title和对应的position，通过一个MapmSections来存储。
     */
    public void findSections() {
        int n = mDatas.size();
        int nSections = 0;
        mSections.clear();
        for (int i = 0; i < n; i++) {
            String sectionName = sectionSupport.getTitle(mDatas.get(i));
            // 前面存了，后面的标题在前面没存过才把心的标题放进去
            if (!mSections.containsKey(sectionName)) {
                // 标题的位置为标题的【消息的数量】 + 【已存标题的数量】
                mSections.put(sectionName, i + nSections);
                // 记录标题的数量
                nSections++;
            }
        }
    }

    /**
     * 获取item真实的position（除去标题）
     * @param position
     * @return
     */
    public int getIndexForPosition(int position) {
        // 初始化标题的数量为0
        int nSections = 0;
        Set<Map.Entry<String, Integer>> entrySet = mSections.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            if (entry.getValue() < position) {
                // 记录标题的数量
                nSections++;
            }
        }
        // 真实的position为当前的position-前面有的标题的数量
        return position - nSections;
    }
}

