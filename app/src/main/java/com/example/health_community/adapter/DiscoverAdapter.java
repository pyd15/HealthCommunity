package com.example.health_community.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.github.hymanme.tagflowlayout.bean.TagBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/17
 * desc:
 */
public  class DiscoverAdapter extends RecyclerView.Adapter {
    private static final int TYPE_SEARCH_HEADER = 0;
    private static final int TYPE_HOT_SEARCH = 1;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
//        if (viewType == TYPE_SEARCH_HEADER) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_search, parent, false);
//            return new SearchHeaderHolder(view);
//        } else {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_search, parent, false);
//            return new HotSearchHolder(view);
//        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof HotSearchHolder) {
            //设置监听(单击和长按事件)
//            ((HotSearchHolder) holder).tagFlowLayout.setTagListener(new OnTagClickListener() {
//                @Override
//                public void onClick(TagFlowLayout parent, View view, int position) {
//                    Toast.makeText(UIUtils.getContext(), "click==" + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onLongClick(TagFlowLayout parent, View view, int position) {
//                    Toast.makeText(UIUtils.getContext(), "long click==" + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
//                }
//            });
//            TagAdapter<TagBean> tagAdapter = new TagAdapter<TagBean>() {
//
//                @Override
//                public View getView(int position, View convertView, ViewGroup parent) {
//                    //定制tag的样式，包括背景颜色，点击时背景颜色，背景形状等
//                    DefaultTagView textView = new ColorfulTagView(UIUtils.getContext());
//                    textView.setText(((TagBean) getItem(position)).getName());
//                    return textView;
//                }
//            };
//            //设置adapter
//            ((HotSearchHolder) holder).tagFlowLayout.setTagAdapter(tagAdapter);


            //给adapter绑定数据
            List<TagBean> tagBeans = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                tagBeans.add(new TagBean(i, "tags" + i));
            }
//            tagAdapter.addAllTags(tagBeans);
//        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_SEARCH_HEADER;
        } else {
            return TYPE_HOT_SEARCH;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
