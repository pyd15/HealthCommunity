package com.example.health_community.adapter;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.mapapi.utils.OpenClientUtil;
import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.health_community.R;
import com.example.health_community.model.Hospital;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/20
 * desc:
 */
public class HospitalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Hospital> mItems;
    private int color = 0;
    private View parentView;
    //是否显示单选框,默认false
    private boolean isshowBox = false;
    private final int TYPE_NORMAL = 1;
    private final int TYPE_FOOTER = 2;
    private final String FOOTER = "footer";

    public HospitalAdapter(Context context) {
        this.context = context;
        mItems = new ArrayList<>();
    }

    public void setItems(List<Hospital> data) {
        mItems.addAll(data);
        notifyDataSetChanged();
    }

    public void addItem(int position, Hospital insertData) {
        mItems.add(position, insertData);
        notifyItemInserted(position);
    }

    public void addItems(List<Hospital> data) {
        mItems.addAll(data);
        notifyItemInserted(mItems.size() - 1);
    }

    public void clearItems() {
        mItems.clear();
    }

    public void addFooter() {
        Hospital hospital = new Hospital();
        hospital.setHos_id("as6df435v1asdf1fs53af1s3af");
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hosptial_item_recycler_view, parent, false);
            return new HospitalAdapter.RecyclerViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hosptial_item_recycler_view, parent, false);
            return new HospitalAdapter.FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HospitalAdapter.RecyclerViewHolder) {
            final HospitalAdapter.RecyclerViewHolder recyclerViewHolder = (HospitalAdapter.RecyclerViewHolder) holder;
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
                    PoiParaOption para = new PoiParaOption().uid(mItems.get(position).getHos_province()); // 天安门
                    Log.e("uid", mItems.get(position).getHos_province());
                    try {
                        BaiduMapPoiSearch.openBaiduMapPoiDetialsPage(para, context);
                    } catch (Exception e) {
                        e.printStackTrace();
                        showDialog();
                    }
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
        Hospital hospital = mItems.get(position);
        if (hospital.getHos_id().equals("as6df435v1asdf1fs53af1s3af")) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        //        @BindView(R.id.hos_img)
        ImageView hos_img;
        //        @BindView(R.id.tv_recycler_item_1)
        TextView hos_name;
        //        @BindView(R.id.tv_recycler_item_2)
        TextView hos_address;
        TextView hos_phone;
        TextView hos_distance;

        private RecyclerViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            hos_img = itemView.findViewById(R.id.hos_img);
            hos_name = itemView.findViewById(R.id.hos_name);
            hos_address = itemView.findViewById(R.id.hos_address);
            hos_phone = itemView.findViewById(R.id.hos_phone);
            hos_distance = itemView.findViewById(R.id.hos_distance);
            //            ButterKnife.bind(this, itemView);
        }

        public void bind(Hospital hospital) throws IOException {

            hos_name.setText(hospital.getHos_name().replace("\\n",""));
            hos_address.setText(hospital.getHos_address());
            if (hospital.getHos_phone()!=null|!hospital.equals(""))
            hos_phone.setText(hospital.getHos_phone());
            else hos_phone.setText("暂无");
            double dis = 0;
            long dist = Long.parseLong(hospital.getHos_id());
            dis = Math.round(dist/100d)/10d;
            if (dis<1)
                hos_distance.setText("1公里内");
            else
                hos_distance.setText(dis+"公里");
//            hos_distance.setText(hospital.getHos_id());
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.fail);
            //                    .dontTransform();
            Glide.with(context).load(R.drawable.ic_hospital).apply(options).into(hos_img);
//            String desc=news.getHospital_desc().substring(0, (int) (news.getHospital_desc().length()*0.8))+"...";
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progress_bar_load_more;

        private FooterViewHolder(View itemView) {
            super(itemView);
            progress_bar_load_more = itemView.findViewById(R.id.progress_bar_load_more);
        }
    }

    /**
     * 提示未安装百度地图app或app版本过低
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(context);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

}
