package com.example.theham.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.theham.Activities.CreateCardActivity;
import com.example.theham.Activities.MainActivity;
import com.example.theham.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MakingFragment extends Fragment {
    FloatingActionButton fab;
    public static ImageView personalCard;
    TextView asdf;
    public static byte[] byteimage;
    public static Bitmap bitmap;

//    public MakingFragment() {
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        // View v = inflater.inflate(R.layout.fragment_making, container, false);
        View v = inflater.inflate(R.layout.fragment_making, null);
        fab = v.findViewById(R.id.fab_mk);
        personalCard = v.findViewById(R.id.PersonalCard);

        asdf = v.findViewById(R.id.textcard);

        fabClick();

        return v;
    }

    // 여기
    public static void getBitmapCardView() {
        byteimage = MainActivity.byteArray;
        bitmap = BitmapFactory.decodeByteArray(byteimage, 0, byteimage.length);
        personalCard.setImageBitmap(bitmap);

        Log.e("byte length", byteimage.length + "");
    }

    private void fabClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), CreateCardActivity.class));
                //  mOnCaptureClick(v);
            }
        });
    }

//    public void mOnCaptureClick(View v){
//        //전체화면
//        View rootView = getWindow().getDecorView();
//
//        File screenShot = ScreenShot(rootView);
//        if(screenShot!=null){
//            //갤러리에 추가
//            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(screenShot)));
//        }
//    }
//
//    //화면 캡쳐하기
//    public File ScreenShot(View view){
//        view.setDrawingCacheEnabled(true);  //화면에 뿌릴때 캐시를 사용하게 한다
//
//        Bitmap screenBitmap = view.getDrawingCache();   //캐시를 비트맵으로 변환
//
//        String filename = "screenshot.png";
//        File file = new File(Environment.getExternalStorageDirectory()+"/Pictures", filename);  //Pictures폴더 screenshot.png 파일
//        FileOutputStream os = null;
//        try{
//            os = new FileOutputStream(file);
//            screenBitmap.compress(Bitmap.CompressFormat.PNG, 90, os);   //비트맵을 PNG파일로 변환
//            os.close();
//        }catch (IOException e){
//            e.printStackTrace();
//            return null;
//        }
//
//        view.setDrawingCacheEnabled(false);
//        return file;
//    }


}