package com.example.theham;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class ScanFragment extends Fragment {
    ImageView image;
    Button btn, btn_confirm;



    Context context = getContext();


    public ScanFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_scan, container, false);

        image = v.findViewById(R.id.image);
        btn = v.findViewById(R.id.btn);
        btn_confirm = v.findViewById(R.id.confirm);

        return v;
    }

}



