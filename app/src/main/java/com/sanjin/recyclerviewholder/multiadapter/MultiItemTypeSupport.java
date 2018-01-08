package com.sanjin.recyclerviewholder.multiadapter;

/**
 * Created by dongmingxin on 2018/1/8.
 * Des:
 */
public interface MultiItemTypeSupport<T> {
    int getLayoutId(int itemType);
    int getItemViewType(int position, T t);
}
