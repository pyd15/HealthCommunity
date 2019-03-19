package com.example.health_community.adapter.searchMedicines;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.health_community.R;
import com.example.health_community.activity.MedInfoActivity;
import com.example.health_community.model.Medicine_Copy;
import com.example.health_community.util.Constant;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dr.P on 2017/11/10.
 */
public class SearchMedicineAdapter extends RecyclerView.Adapter<SearchMedicineInfoHolder> {
    Context context;
    private List<Medicine_Copy> medicines;

    public SearchMedicineAdapter(List<Medicine_Copy> medicineList) {
        this.medicines = medicineList;
    }

    public SearchMedicineAdapter setDatas(List<Medicine_Copy> datas) {
        medicines = datas;
        return this;
    }

    @Override
    public SearchMedicineInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context==null) {
            context=parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item, parent, false);
        final SearchMedicineInfoHolder holder = new SearchMedicineInfoHolder(context, view);
        holder.butterflyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int postiton=holder.getAdapterPosition();
                Medicine_Copy medicine = medicines.get(postiton);
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
                intent.putExtra(Constant.MEDINCINE_INFO, medicine);
                context.startActivity(intent);
                Activity activity = (Activity) context;
                activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchMedicineInfoHolder holder, int position) {
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

    public void setFilter(List<Medicine_Copy> medicines) {
//        this.medicines = new ArrayList<>();
        this.medicines.clear();
        this.medicines.addAll(medicines);
        notifyDataSetChanged();
    }

    public void animateTo(List<Medicine_Copy> medicines) {
        applyAndAnimateRemovals(medicines);
        applyAndAnimateAdditions(medicines);
        applyAndAnimateMovedItems(medicines);
    }

    private void applyAndAnimateRemovals(List<Medicine_Copy> medicines) {
        for (int i = this.medicines.size() - 1; i >= 0; i--) {
            final Medicine_Copy butterflyInfo = this.medicines.get(i);
            if (!medicines.contains(butterflyInfo)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Medicine_Copy> medicines) {
        for (int i = 0, count = medicines.size(); i < count; i++) {
            final Medicine_Copy butterflyInfo = medicines.get(i);
            if (!medicines.contains(butterflyInfo)) {
                addItem(i, butterflyInfo);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Medicine_Copy> medicines) {
        for (int toPosition = medicines.size() - 1; toPosition >= 0; toPosition--) {
            final Medicine_Copy people = medicines.get(toPosition);
            final int fromPosition = medicines.indexOf(people);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Medicine_Copy removeItem(int position) {
        final Medicine_Copy people = medicines.remove(position);
        notifyItemRemoved(position);
        return people;
    }

    public void addItem(int position, Medicine_Copy people) {
        medicines.add(position, people);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Medicine_Copy people = medicines.remove(fromPosition);
        medicines.add(toPosition, people);
        notifyItemMoved(fromPosition, toPosition);
    }
}
