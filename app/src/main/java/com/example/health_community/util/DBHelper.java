package com.example.health_community.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/16
 * desc:
 */
public class DBHelper extends SQLiteOpenHelper {

    private Context mContext;

    //    private static final String CREATE_MED = "CREATE TABLE medicine(" +
    //            "med_id int NOT NULL," +
    //            "med_name char(255) NOT NULL," +
    //            "med_en_name varchar(255) NULL DEFAULT NULL," +
    //            "med_pre_img char(255) NULL DEFAULT NULL," +
    //            "med_desc_img varchar(255) NULL DEFAULT NULL," +
    //            "med_desc char(255) NULL DEFAULT NULL," +
    //            "med_company char(255) NOT NULL," +
    //            "med_type char(255) NULL DEFAULT NULL," +
    //            "med_rates char(255) NULL DEFAULT NULL," +
    //            "med_pre_tag char(255) NULL DEFAULT NULL," +
    //            "med_desc_tag char(255) NULL DEFAULT NULL," +
    //            "med_related_tips varchar(255) NULL DEFAULT NULL," +
    //            "med_component varchar(255) NULL DEFAULT NULL," +
    //            "med_useage varchar(255) NULL DEFAULT NULL," +
    //            "med_avoid char(255) NULL DEFAULT NULL," +
    //            "med_attention varchar(5555) NULL DEFAULT NULL," +
    //            "med_adverse char(255) NULL DEFAULT NULL," +
    //            "med_purpose varchar(555) NULL DEFAULT NULL," +
    //            "med_href char(255) NULL DEFAULT NULL," +
    //            "PRIMARY KEY med (med_name, med_company)" +
    //            ")";
    private static final String CREATE_MED= "CREATE TABLE medicine ("+
            " med_id INT (11) NOT NULL,"+
            "    med_name         CHAR (255)     NOT NULL,"+
            "    med_en_name      VARCHAR (255),"+
            "    med_pre_img      CHAR (255),"+
            "    med_desc_img     VARCHAR (255),"+
            "    med_desc         CHAR (255),"+
            "    med_company      CHAR (255)     NOT NULL,"+
            "    med_type         CHAR (255),"+
            "    med_rates        CHAR (255),"+
            "    med_pre_tag      CHAR (255),"+
            "    med_desc_tag     CHAR (255),"+
            "    med_related_tips VARCHAR (255),"+
            "    med_component    VARCHAR (255),"+
            "    med_useage       VARCHAR (255),"+
            "    med_avoid        CHAR (255),"+
            "    med_attention    VARCHAR (5555),"+
            "    med_adverse      CHAR (255),"+
            "    med_purpose      VARCHAR (555),"+
            "    med_href         CHAR (255),"+
            "    PRIMARY KEY ("+
            "        med_name,"+
            "        med_company"+
            "    )"+
            ")";


    //    "CREATE TABLE medicine(" +
    //            "med_id int(11) NOT NULL," +
    //            "med_name char(255) NOT NULL," +
    //            "med_en_name varchar(255) NULL DEFAULT NULL," +
    //            "med_pre_img char(255) NULL DEFAULT NULL," +
    //            "med_desc_img varchar(255) NULL DEFAULT NULL," +
    //            "med_desc char(255) NULL DEFAULT NULL," +
    //            "med_company char(255) NOT NULL," +
    //            "med_type char(255) NULL DEFAULT NULL," +
    //            "med_rates char(255) NULL DEFAULT NULL," +
    //            "med_pre_tag char(255) NULL DEFAULT NULL," +
    //            "med_desc_tag char(255) NULL DEFAULT NULL," +
    //            "med_related_tips varchar(255) NULL DEFAULT NULL," +
    //            "med_component varchar(255) NULL DEFAULT NULL," +
    //            "med_useage varchar(255) NULL DEFAULT NULL," +
    //            "med_avoid char(255) NULL DEFAULT NULL," +
    //            "med_attention varchar(5555) NULL DEFAULT NULL," +
    //            "med_adverse char(255) NULL DEFAULT NULL," +
    //            "med_purpose varchar(555) NULL DEFAULT NULL," +
    //            "med_href char(255) NULL DEFAULT NULL," +
    //            "PRIMARY KEY med (med_name, med_company)" +
    //            ")";

    public DBHelper(Context context, String databaseName,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, databaseName, factory, version);
        mContext = context;
    }


    /**
     * 数据库第一次创建时调用
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //        if (!tabIsExist("test", db)) {
//        db.execSQL(CREATE_MED);
        executeAssetsSQL(db, "1.sql");
        // db.execSQL(sql);
        //System.out.println("创建表");
        //        }
    }

    /**
     * 数据库升级时调用
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 数据库不升级
        if (newVersion <= oldVersion) {
            return;
        }

        int changeCnt = newVersion - oldVersion;
        for (int i = 0; i < changeCnt; i++) {
            // 依次执行updatei_i+1文件 由1更新到2 [1-2]，2更新到3 [2-3]
            String schemaName = "update" + (oldVersion + i) + "_"
                    + (oldVersion + i + 1) + ".sql";
            executeAssetsSQL(db, schemaName);
        }
    }

    /**
     * 读取数据库文件（.sql），并执行sql语句
     */
    private void executeAssetsSQL(SQLiteDatabase db, String schemaName) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(mContext.getAssets()
                    //"data/data/com.example.health_community/databases" + "/" +
                    .open(schemaName)));

            //System.out.println("路径:" + Configuration.DB_PATH + "/" + schemaName);
            String line;
            String buffer = "";
            while ((line = in.readLine()) != null) {
                buffer += line;
                if (line.trim().endsWith(";")) {
                    db.execSQL(buffer.replace(";", ""));
                    buffer = "";
                }
            }
        } catch (IOException e) {
            Log.e("db-error", e.toString());
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                Log.e("db-error", e.toString());
            }
        }
    }

    //    public List selectAllCities(SQLiteDatabase db) {
    //        List areas = new ArrayList();
    //        Area area;
    //        String sql = "select * from test where area_level=?";
    //        Cursor cursor = db.rawQuery(sql, new String[] { "" + 0 });
    //
    //        while(cursor.moveToNext()){
    //            area = new Area();
    //            area.setId(cursor.getInt(0));
    //            area.setArea_name(cursor.getString(2));
    //            areas.add(area);
    //            area = null;
    //        }
    //        cursor.close();
    //
    //        return areas;
    //    }

    //    public List selectAllAreas(SQLiteDatabase db, int parent_id) {
    //        List areas = new ArrayList();
    //        Area area;
    //        String sql = "select * from test where parent_id=?";
    //        Cursor cursor = db.rawQuery(sql, new String[] { "" + parent_id });
    //
    //        while(cursor.moveToNext()){
    //            area = new Area();
    //            area.setId(cursor.getInt(0));
    //            area.setArea_name(cursor.getString(2));
    //            areas.add(area);
    //            area = null;
    //        }
    //        cursor.close();
    //
    //        return areas;
    //    }

    /**
     * 判断是否存在某一张表
     *
     * @param tabName
     * @param db
     * @return
     */
    public boolean tabIsExist(String tabName, SQLiteDatabase db) {
        boolean result = false;
        if (tabName == null) {
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + tabName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
        }
        return result;
    }

}
