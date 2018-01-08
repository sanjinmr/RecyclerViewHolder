package com.sanjin.recyclerviewholder.sectionadapter;

/**
 * Created by dongmingxin on 2018/1/8.
 * Des:
 */
public interface SectionSupport<T> {

    int sectionHeaderLayoutId();

    int sectionTitleTextViewId();

    String getTitle(T t);

}
