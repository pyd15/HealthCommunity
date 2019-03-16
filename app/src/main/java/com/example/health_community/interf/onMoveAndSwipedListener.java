package com.example.health_community.interf;

/**
 * Created by pyd on 2019.02.25.
 */
public interface onMoveAndSwipedListener {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);

}
