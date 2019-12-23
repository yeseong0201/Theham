package com.example.theham.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.theham.Activities.CreateCardActivity;
import com.example.theham.Activities.MainActivity;
import com.example.theham.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.content.Context.MODE_PRIVATE;

public class MakingFragment extends Fragment {
    FloatingActionButton fab;
    public static ImageView personalCard;
    TextView asdf;
    public static byte[] byteimage;
    public static Bitmap bitmap;
    ConstraintLayout pc_layout;

    // public Context context = getActivity();

    public static Context context;

    public static SharedPreferences pref1;
    public SharedPreferences.Editor editor;

//    public MakingFragment() {
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MakingFragment.context = getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_making, null);
        fab = v.findViewById(R.id.fab_mk);
        personalCard = v.findViewById(R.id.PersonalCard);
        pc_layout = v.findViewById(R.id.pc_layout);

        asdf = v.findViewById(R.id.textcard);

        fabClick();
        getPreferences();

        return v;

    }

    // 여기
    public static void getBitmapCardView() {
        byteimage = MainActivity.byteArray;
        bitmap = BitmapFactory.decodeByteArray(byteimage, 0, byteimage.length);
        personalCard.setImageBitmap(bitmap);


    }

    private void fabClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), CreateCardActivity.class));

            }
        });
    }

    // 값 불러오기
    public static void getPreferences() {
        pref1 = context.getSharedPreferences("card", MODE_PRIVATE);
        String image = pref1.getString("cardString", "");
        Bitmap bitmap = StringtoBitmap(image);
        if (bitmap != null) {
            personalCard.setImageBitmap(bitmap);
        }
    }
    public static Bitmap StringtoBitmap(String encodedString) {
        try {
            byte[] encodedByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodedByte, 0, encodedByte.length);
            return bitmap;

        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


}