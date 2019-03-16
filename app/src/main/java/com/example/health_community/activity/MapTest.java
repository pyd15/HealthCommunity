package com.example.health_community.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.health_community.R;
import com.example.health_community.util.DBHelper;
import com.example.health_community.util.MedicineCrawler;

public class MapTest extends AppCompatActivity implements View.OnClickListener {

    private Button button3;
    private Button button2;
    private Button button1;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_test2);
        button = findViewById(R.id.b_map_location);
        button1 = findViewById(R.id.b_map_search);
        button2 = findViewById(R.id.map_activity);
        button3 = findViewById(R.id.map_test_activity);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        MedicineCrawler medicineCrawler=new MedicineCrawler();
        medicineCrawler.getMedicines();
//        SQLUtil.createDatabase(getApplicationContext());
        DBHelper dbHelper = new DBHelper(this, "HealthCommunityStore.db", null, 1);
//        dbHelper.getWritableDatabase();

//        SPUtils.setPrefBoolean(Constant.DB_EXIST, readDBFlag);
//        List<Medicine> medicines = LitePal.findAll(Medicine.class);
//        Log.e("fsdaf'", medicines.size() + "-"+medicines.get(0).getMed_attention());
//        SQLiteDatabase db = new SQLiteDatabase(this, "my.db")
//                .getWritableDatabase();
//
//        try {
//
//            InputStream in = getAssets().open("garden.sql");
//
//            String sqlUpdate = null;
//            try {
//                sqlUpdate = readTextFromSDcard(in);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            String[] s = sqlUpdate.split(";");
//            for (int i = 0; i < s.length; i++) {
//                if (!TextUtils.isEmpty(s[i])) {
//                    db.execSQL(s[i]);
//                }
//            }
//            in.close();
//        } catch (SQLException e) {
//        } catch (IOException e) {
//        }
//
//    }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.b_map_location:
                intent = new Intent(this, LocationDemo.class);
                startActivity(intent);
                break;
            case R.id.b_map_search:
                intent = new Intent(this, PoiSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.map_activity:
                intent = new Intent(this, MapActivity.class);
                startActivity(intent);
                break;
            case R.id.map_test_activity:
//                intent = new Intent(this, MapTestActivity.class);
//                startActivity(intent);
                button3.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
