package com.example.health_community.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.health_community.R;
import com.example.health_community.model.AppointMent;
import com.example.health_community.view.interf.onMoveAndSwipedListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/21
 * desc:
 */
public class AppointAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements onMoveAndSwipedListener {

    private Context context;
    private List<AppointMent> mItems;
    private int color = 0;
    private View parentView;
    //是否显示单选框,默认false
    private boolean isshowBox = false;
    private final int TYPE_NORMAL = 1;
    private final int TYPE_FOOTER = 2;
    private final String FOOTER = "footer";

    public AppointAdapter(Context context) {
        this.context = context;
        mItems = new ArrayList<>();
    }

    public void setItems(List<AppointMent> data) {
        mItems.addAll(data);
        notifyDataSetChanged();
    }

    public void addItem(int position, AppointMent insertData) {
        mItems.add(position, insertData);
        notifyItemInserted(position);
    }

    public void addItems(List<AppointMent> data) {
        mItems.addAll(data);
        notifyItemInserted(mItems.size() - 1);
    }

    public void clearItems() {
        mItems.clear();
    }

    public void addFooter() {
        AppointMent hospital = new AppointMent();
        hospital.setAppoint_date("as6df435v1asdf1fs53af1s3af");
        mItems.add(hospital);
        notifyItemInserted(mItems.size() - 1);
    }

    public void removeFooter() {
        mItems.remove(mItems.size() - 1);
        notifyItemRemoved(mItems.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        parentView = parent;
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appoint_item_recycler_view, parent, false);
            return new AppointAdapter.RecyclerViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_footer, parent, false);
            return new AppointAdapter.FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AppointAdapter.RecyclerViewHolder) {
            final AppointAdapter.RecyclerViewHolder recyclerViewHolder = (AppointAdapter.RecyclerViewHolder) holder;
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
            recyclerViewHolder.mView.startAnimation(animation);
            try {
                recyclerViewHolder.bind(mItems.get(position));
            } catch (IOException e) {
                e.printStackTrace();
            }
            recyclerViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View view) {
                    new android.support.v7.app.AlertDialog.Builder(context).setTitle("确认取消该预约记录？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                    //                        ActivityCollector.finishAll();
                                            onItemDismiss(position);
//                                            Intent intent = new Intent(Constant.APPOINT_REFRESH);
                    //                        intent.putExtra(Constant.APPOINT_NUM, appointMents.size());
//                                            context.sendBroadcast(intent);
                                        }
                                    })
                                    .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
//                                            Intent intent = new Intent(Constant.APPOINT_REFRESH);
                                            //                        intent.putExtra(Constant.APPOINT_NUM, appointMents.size());
//                                            context.sendBroadcast(intent);
                                        }
                                    })
                                    .show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        AppointMent hospital = mItems.get(position);
        if (hospital.getAppoint_date().equals("as6df435v1asdf1fs53af1s3af")) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(final int position) {
        mItems.remove(position);
        notifyItemRemoved(position);

        Snackbar.make(parentView, context.getString(R.string.item_swipe_dismissed), Snackbar.LENGTH_SHORT)
                .setAction(context.getString(R.string.item_swipe_undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addItem(position, mItems.get(position));
                    }
                }).show();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        //        @BindView(R.id.hos_img)
        ImageView hos_img;
        //        @BindView(R.id.tv_recycler_item_1)
        TextView hos_name;
        //        @BindView(R.id.tv_recycler_item_2)
        TextView big_depart_name;
        TextView depart_name;
        TextView appoint_date;

        private RecyclerViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            hos_img = itemView.findViewById(R.id.hos_img);
            hos_name = itemView.findViewById(R.id.hos_name);
            big_depart_name = itemView.findViewById(R.id.big_depart_name);
            depart_name = itemView.findViewById(R.id.depart_name);
            appoint_date = itemView.findViewById(R.id.appoint_date);
            //            ButterKnife.bind(this, itemView);
        }

        public void bind(AppointMent appointMent) throws IOException {

            hos_name.setText(appointMent.getHos_name().replace("\\n", ""));
            big_depart_name.setText(appointMent.getBig_depart_name());
            depart_name.setText(appointMent.getDepart_name());
            appoint_date.setText(appointMent.getAppoint_date());
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.fail);
            //                    .dontTransform();
                       Glide.with(context).load(R.drawable.ic_appointr).apply(options).into(hos_img);
            //            String desc=news.getAppointMent_desc().substring(0, (int) (news.getAppointMent_desc().length()*0.8))+"...";
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progress_bar_load_more;

        private FooterViewHolder(View itemView) {
            super(itemView);
            progress_bar_load_more = itemView.findViewById(R.id.progress_bar_load_more);
        }
    }
}
