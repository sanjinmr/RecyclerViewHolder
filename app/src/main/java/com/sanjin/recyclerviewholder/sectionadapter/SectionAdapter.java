package com.sanjin.recyclerviewholder.sectionadapter;

import android.content.Context;
import com.sanjin.recyclerviewholder.multiadapter.MultiItemTypeSupport;

import java.util.List;

/**
 * Created by dongmingxin on 2018/1/8.
 * Des:
 */
public abstract class SectionAdapter<T> extends SectionAdapterBase<T> {

    public SectionAdapter(
            Context context,
            int layoutId,
            List<T> datas,
            SectionSupport<T> sectionSupport) {
        super(context, layoutId, datas, sectionSupport);
    }

    @Override
    protected MultiItemTypeSupport<T> headerItemSupport() {
        return new MultiItemTypeSupport<T>() {
            @Override
            public int getLayoutId(int itemType) {
                if (itemType == TYPE_SECTION) {
                    return sectionSupport.sectionHeaderLayoutId();
                }
                return mLayoutId;
            }

            @Override
            public int getItemViewType(int position, T t) {
                return mSections.values().contains(position) ? TYPE_SECTION : 1;
            }
        };
    }
}

