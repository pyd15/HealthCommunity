package com.example.health_community.util;

import android.util.Log;

import com.example.health_community.adapter.mutiLevelList.TreePoint;

import java.util.HashMap;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/08
 * desc:
 */
public class TreeUtils {
    //第一级别为0
    public static int getLevel(TreePoint treePoint, HashMap<String,TreePoint> map){
        if("0".equals(treePoint.getPARENT_ID())){
            return 0;
        }else{
            return 1+getLevel(getTreePoint(treePoint.getPARENT_ID(),map),map);
        }
    }



    public static TreePoint getTreePoint(String ID, HashMap<String,TreePoint> map){
        if(map.containsKey(ID)){
            return map.get(ID);
        }
        Log.e("xlc","ID:" + ID);
        return null;
    }
}
