package com.example.health_community.adapter.mutiLevelList;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/08
 * desc:
 */
public class TreePoint {
    private String ID;//账号id
    private String N_NAME; //原因名称
    private String PARENT_ID;//父id     0表示父节点
    private String IS_LEAF;//0,            //是否是叶子节点   1为叶子节点
    private int DISPLAY_ORDER;//同一个级别的显示顺序
    private boolean isExpand = false;  //是否展开了
    private boolean isSelected = false; //是否选中了


    public TreePoint(String ID, String NNAME, String PARENT_ID, String ISLEAF, int DISPLAY_ORDER) {
        this.ID = ID;
        this.N_NAME = NNAME;
        this.PARENT_ID = PARENT_ID;
        this.IS_LEAF = ISLEAF;
        this.DISPLAY_ORDER = DISPLAY_ORDER;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getN_NAME() {
        return N_NAME;
    }

    public void setNAME(String NAME) {
        this.N_NAME = N_NAME;
    }

    public String getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(String PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }

    public String getIS_LEAF() {
        return IS_LEAF;
    }

    public void setIS_LEAF(String IS_LEAF) {
        this.IS_LEAF = IS_LEAF;
    }

    public int getDISPLAY_ORDER() {
        return DISPLAY_ORDER;
    }

    public void setDISPLAY_ORDER(int DISPLAY_ORDER) {
        this.DISPLAY_ORDER = DISPLAY_ORDER;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}