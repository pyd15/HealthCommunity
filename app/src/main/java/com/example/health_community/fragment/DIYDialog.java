package com.example.health_community.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.request.RequestOptions;
import com.example.health_community.R;

import org.litepal.LitePal;

/**
 * Created by Dr.P on 2017/12/23.
 * runas /user:Dr.P "cmd /k"
 */

public class DIYDialog extends BaseDialog {

    private String clipImagePath;
    private Context context;
    private int id = 1;

    private OnClickListener mListener;

    public DIYDialog(Context context, String clipImagePath, int id) {
        super(context);
        this.clipImagePath = clipImagePath;
        this.id = id;
        mCreateView = initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置Dialog没有标题。需在setContentView之前设置，在之后设置会报错
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置Dialog背景透明效果，必须设置一个背景，否则会有系统的Dialog样式：外部白框
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(mCreateView);//添加视图布局

        MyOnClickListener listener = new MyOnClickListener(0);

        LitePal.initialize(mContext);
//        ImageView imageView = (ImageView) findViewById(R.id.picture);
//        TextView name = (TextView) findViewById(R.id.Name);
//        TextView latinName = (TextView) findViewById(R.id.latinname);
//        TextView kind = (TextView) findViewById(R.id.Kind);
//        TextView detail = (TextView) findViewById(R.id.detail);
//        Button checkcompare = (Button) findViewById(R.id.checkcompare);
//        TextView home = (TextView) findViewById(R.id.homepage);

//        List<InfoDetail> infoDetail = DataSupport.where("b_id=?",String.valueOf(id)).find(InfoDetail.class);
        RequestOptions options = new RequestOptions().
                placeholder(R.drawable.loading).error(R.drawable.fail);
//        Glide.with(mContext).load(Constant._URL+ NormalUtil.getImagePaths(infoDetail.get(0)).get(0)).apply(options).into(imageView);
//        Glide.with(mContext).load(getImagePaths(infoDetail.get(0)).get(0)).apply(options).into(imageView);

//        name.setText(infoDetail.get(0).getName());
//        latinName.setText(infoDetail.get(0).getLatinName());
//        kind.setText(infoDetail.get(0).getType());
//        detail.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        home.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        detail.setText("查看详情");
//        home.setText("返回首页");
//        checkcompare.setOnClickListener(listener);
//        detail.setOnClickListener(listener);//设置点击监听器
//        home.setOnClickListener(listener);
        setLayout();
    }

    private View initView() {
        View view = getLayoutInflater().inflate(R.layout.activity_self_appointment, null);
        return view;
    }

    public class MyOnClickListener implements View.OnClickListener {
        private int mPosition;

        public MyOnClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            Intent intent;
//            switch (v.getId()) {
//                case R.id.checkcompare:
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.CHINA);
//                    Uri screenShot;
//                    String fname = mContext.getExternalCacheDir().getAbsolutePath()+"/"+sdf.format(new Date())+ ".jpg";
//                    View view = v.getRootView();
//                    view.setDrawingCacheEnabled(true);
//                    view.buildDrawingCache();
//                    Bitmap bitmap = view.getDrawingCache();
//                    File file = new File(fname);
//                    if (file.exists())
//                        file.delete();
//                    try {
//                        file.createNewFile();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    if(bitmap!= null){
////                        Log.e("file_screenshot","bitmap got!");
//                        try{
//                            FileOutputStream out = new FileOutputStream(file);
//                            bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
////                            Log.e("file", fname + "outputdone.");
////                            Log.e("file_path", Uri.fromFile(file).toString());
//                        }catch(Exception e) {
//                            e.printStackTrace();
//                        }
//                    }else{
//                        Log.e("file_error","bitmap is NULL!");
//                    }
//                    if (Build.VERSION.SDK_INT >= 24) {
//                        //别忘了注册FileProvider内容提供器
//                        screenShot = FileProvider.getUriForFile(mContext, "com.example.btf.fileProvider", file);
////                        Log.e("imageUri_sdk>24", screenShot.toString());
//                    } else {
//                        screenShot = Uri.fromFile(file);
////                        Log.e("imageUri_sdk<24", screenShot.toString());
//                    }
//                    intent = new Intent(Intent.ACTION_SEND);
//                    intent.setType("image/*");
//                    intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
//                    //分享图片
//                    intent.putExtra(Intent.EXTRA_STREAM, screenShot);
//                    //分享文本
//                    intent.putExtra(Intent.EXTRA_TEXT, "识别图片中蝴蝶的结果");
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Activity activity = (Activity) mContext;
//                    mContext.startActivity(Intent.createChooser(intent, activity.getTitle()));
//                    break;
//                case R.id.detail:
//                    intent = new Intent(getContext(), BtfInfoActivity.class);
//                    intent.putExtra(Constants.BTF_ID, id);
//                    intent.putExtra("activity", BtfInfoActivity.class.getSimpleName());
////                    InfoDetail infoDetail = DataSupport.find(InfoDetail.class, id);
//                    List<InfoDetail> infoDetail = DataSupport.where("b_id=?",String.valueOf(id)).find(InfoDetail.class);
//                    //                    String[] images = infoDetail.getImagePath().split(",");
//                    //                    ArrayList<String> imageList = new ArrayList<String>();
//                    //                    for (int i = 0; i < images.length; i++) {
//                    //                        imageList.add(images[i]);
//                    //                    }
//                    intent.putExtra(Constants.IMAGE_LIST, NormalUtil.getImagePaths(infoDetail.get(0)));
//                    mContext.startActivity(intent);
//
//                    break;
//                case R.id.homepage:
////                    Intent intent1 = new Intent(getContext(), MainActivity.class);
////                    mContext.startActivity(intent1);
//                    Activity activity1=(Activity)mContext;
//                    activity1.finish();
//                    break;
//                default:
//                    break;
//            }
        }
    }

    public interface OnClickListener {
        void OnClick(View v, int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }

    private void setLayout() {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = mScreenWidth;
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        //水平居中、底部
        this.getWindow().setAttributes(params);
    }

}
