package com.example.health_community.fragment;



//import com.example.btf.databinding.PreviewBinding;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.health_community.model.Medicine;
import com.example.health_community.R;
import com.example.health_community.view.viewpager.DFAdapter;
import com.example.health_community.view.viewpager.GuardViewPager;
import com.example.health_community.view.viewpager.ViewPagerIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends DialogFragment {

    public static final String EXTRA_IMAGE_PATH = "PHOTO_FROM_INFO";

    private ImageView imageView;
    private TextView nameText;
    private TextView latinNameText;
    private GuardViewPager vp;
    private DFAdapter dfAdapter;
    private LinearLayout ll;
//    private PreviewBinding binding;

    private static String[] images;

    private static Context context;
    private static Medicine medicine;

    public ImageFragment() {
        // Required empty public constructor
    }

    public static ImageFragment newInstance(Context co, String imagePath, String[] imgs) {
//        medicine = med;
        context = co;
        images = imgs;
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_IMAGE_PATH, imagePath);
        ImageFragment imageFragment = new ImageFragment();
        imageFragment.setArguments(bundle);
        imageFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        return imageFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x4c000000));
        final View view = inflater.inflate(R.layout.fragment_image, container, false);

        vp = (GuardViewPager) view.findViewById(R.id.vp_frag);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        dfAdapter = new DFAdapter(context,getActivity(),getDialog(), fm, images);
        vp.setAdapter(dfAdapter);
        ll = (LinearLayout) view.findViewById(R.id.ll_frag);
        if (images.length > 1) {
            vp.addOnPageChangeListener(new ViewPagerIndicator(context, vp, ll, images.length));
        } else {
            vp.toggleSlide(false);//若该类药品只有一张图片则不进行切换显示
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

//        nameText = (TextView) view.findViewById(R.id.name_frag);
//        latinNameText = (TextView) view.findViewById(R.id.latinName_frag);
//        String imagePath = (String) getArguments().getSerializable(EXTRA_IMAGE_PATH);
//        nameText.setText(medicine.getName());
//        latinNameText.setText(medicine.getType());

        return view;
    }

        @Override
        public void onStart()
        {
            super.onStart();
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
            getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
        }
}
