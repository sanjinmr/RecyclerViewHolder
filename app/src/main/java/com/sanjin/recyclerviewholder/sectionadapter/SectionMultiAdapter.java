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
public abstract class SectionMultiAdapter<T> extends SectionAdapterBase<T> {

    private MultiItemTypeSupport<T> multiItemTypeSupport1;

    public SectionMultiAdapter(
            Context context,
            int layoutId,
            List<T> datas,
            SectionSupport<T> sectionSupport,
            MultiItemTypeSupport<T> multiItemTypeSupport1) {
        super(context, layoutId, datas, sectionSupport);
        this.multiItemTypeSupport1 = multiItemTypeSupport1;
    }

    @Override
    protected MultiItemTypeSupport<T> headerItemSupport() {
        return new MultiItemTypeSupport<T>() {
            @Override
            public int getLayoutId(int itemType) {
                if (itemType == TYPE_SECTION) {
                    return sectionSupport.sectionHeaderLayoutId();
                }
                return multiItemTypeSupport1.getLayoutId(itemType);
            }

            @Override
            public int getItemViewType(int position, T t) {
                return mSections.values().contains(position) ?
                        TYPE_SECTION : multiItemTypeSupport.getItemViewType(position, t);
            }
        };
    }
}

