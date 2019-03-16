package com.example.health_community.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.health_community.Medicine;
import com.example.health_community.R;
import com.example.health_community.activity.MedInfoActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dr.P on 2017/11/10.
 */
public class SearchMedicineAdapter extends RecyclerView.Adapter<SearchButterflyInfoHolder> {
    Context context;
    private List<Medicine> medicines;

    public SearchMedicineAdapter(List<Medicine> butterflyList) {
        this.medicines = butterflyList;
    }

    public SearchMedicineAdapter setDatas(List<Medicine> datas) {
        medicines = datas;
        return this;
    }

    @Override
    public SearchButterflyInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context==null) {
            context=parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item, parent, false);
        final SearchButterflyInfoHolder holder = new SearchButterflyInfoHolder(context, view);
        holder.butterflyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int postiton=holder.getAdapterPosition();
                Medicine medicine = medicines.get(postiton);
//                String[] images = butterflyInfo.getImagePath().split(",");
//                String[] img_urls = butterflyInfo.getpageUrl().split(",");
//                ArrayList<String> imageList = new ArrayList<String>();
//                for (int i = abc; i < images.length; i++) {
//                for (int i = 0; i < img_urls.length; i++) {
//                    imageList.add(img_urls[i]);
//                    imageList.add(images[i]);
//                }
                Intent intent = new Intent(context, MedInfoActivity.class);
//                intent.putStringArrayListExtra(Constants.IMAGE_LIST, imageList);
//                intent.putExtra(Constants.BTF_ID, butterflyInfo.getId());
                intent.putExtra("selected_med", medicine);
                context.startActivity(intent);
                Activity activity = (Activity) context;
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchButterflyInfoHolder holder, int position) {
        try {
            holder.bind(medicines.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public void setFilter(List<Medicine> butterflyInfos) {
        medicines = new ArrayList<>();
        medicines.addAll(butterflyInfos);
        notifyDataSetChanged();
    }

    public void animateTo(List<Medicine> butterflyInfos) {
        applyAndAnimateRemovals(butterflyInfos);
        applyAndAnimateAdditions(butterflyInfos);
        applyAndAnimateMovedItems(butterflyInfos);
    }

    private void applyAndAnimateRemovals(List<Medicine> butterflyInfos) {
        for (int i = medicines.size() - 1; i >= 0; i--) {
            final Medicine butterflyInfo = medicines.get(i);
            if (!butterflyInfos.contains(butterflyInfo)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Medicine> butterflyInfos) {
        for (int i = 0, count = butterflyInfos.size(); i < count; i++) {
            final Medicine butterflyInfo = medicines.get(i);
            if (!medicines.contains(butterflyInfo)) {
                addItem(i, butterflyInfo);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Medicine> butterflyInfos) {
        for (int toPosition = butterflyInfos.size() - 1; toPosition >= 0; toPosition--) {
            final Medicine people = butterflyInfos.get(toPosition);
            final int fromPosition = medicines.indexOf(people);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Medicine removeItem(int position) {
        final Medicine people = medicines.remove(position);
        notifyItemRemoved(position);
        return people;
    }

    public void addItem(int position, Medicine people) {
        medicines.add(position, people);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Medicine people = medicines.remove(fromPosition);
        medicines.add(toPosition, people);
        notifyItemMoved(fromPosition, toPosition);
    }
}
