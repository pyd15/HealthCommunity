package com.example.health_community.view;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.health_community.view.interf.onMoveAndSwipedListener;

/**
 * Created by zhang on 2016.08.21.
 */
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private onMoveAndSwipedListener moveAndSwipedListener;
    private Context context;

    public ItemTouchHelperCallback(onMoveAndSwipedListener listener, Context context) {
        this.moveAndSwipedListener = listener;
        this.context = context;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            // for recyclerView with gridLayoutManager, support drag all directions, not support swipe
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);

        } else {
            // for recyclerView with linearLayoutManager, support drag up and down, and swipe lift and right
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // If the 2 items are not the same type, no dragging
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        moveAndSwipedListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//        NormalUtil.showExitDialog(context, "确认取消该预约记录？");
//        new android.support.v7.app.AlertDialog.Builder(context).setTitle("确认取消该预约记录？")
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        ActivityCollector.finishAll();
//                        moveAndSwipedListener.onItemDismiss(viewHolder.getAdapterPosition());
//                        Intent intent = new Intent(Constant.APPOINT_REFRESH);
////                        intent.putExtra(Constant.APPOINT_NUM, appointMents.size());
//                        context.sendBroadcast(intent);
//                    }
//                })
//                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(Constant.APPOINT_REFRESH);
//                        //                        intent.putExtra(Constant.APPOINT_NUM, appointMents.size());
//                        context.sendBroadcast(intent);
//                    }
//                })
//                .show();
        moveAndSwipedListener.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
