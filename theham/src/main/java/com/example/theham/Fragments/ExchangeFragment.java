package com.example.theham.Fragments;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.theham.Activities.MainActivity;
import com.example.theham.R;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class ExchangeFragment extends Fragment {

    MainActivity mainActivity = (MainActivity) getActivity();
    ImageButton button;
    Path path;


    public ExchangeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exchange, container, false);
        button = v.findViewById(R.id.btn);

        // imageSend();
        sendShare();
        // shareImage();

        return v;

    }

    private void sendShare() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");

                List<ResolveInfo> resInfo = getContext().getPackageManager().queryIntentActivities(intent, 0);
                if (resInfo.isEmpty()) {
                    return;
                }

                List<Intent> shareIntentList = new ArrayList<Intent>();

                for (ResolveInfo info : resInfo) {
                    Intent shareIntent = (Intent) intent.clone();

                    File dirName = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Download");
                    String file = "Capture20191220015905.JPEG";
                  //  File files = new File(dirName, file);
                   // Uri uri = FileProvider.getUriForFile(mainActivity.getApplicationContext(), "com.example.theham", files);
                    shareIntent.setType("image/*");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "더 함");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "구글 http://www.google.com #");
                    //shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///" + mImagePath));
                  //  shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
                    shareIntent.setPackage(info.activityInfo.packageName);
                    shareIntentList.add(shareIntent);
                }

                Intent chooserIntent = Intent.createChooser(shareIntentList.remove(0), "명함 보내기");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, shareIntentList.toArray(new Parcelable[]{}));
                startActivity(chooserIntent);
            }
        });
    }

    public void shareImage() { //공유 이미지 함수
        // 애셋매니저
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssetManager am = getResources().getAssets();
                InputStream is = null;
                File dirName = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Download");  //디렉토리를 지정합니다.
                String file = "call_24_px.png"; //공유할 이미지 파일 명

                File files = new File(dirName, file); //image 파일의 경로를 설정합니다.
                Uri uri = FileProvider.getUriForFile(getContext(), "com.example.theham.ExchangeFragment", files);

                // Uri mSaveImageUri = Uri.fromFile(files); //file의 경로를 uri로 변경합니다.
                Intent intent = new Intent(Intent.ACTION_SEND); //전송 메소드를 호출합니다. Intent.ACTION_SEND
                intent.setType("image/png"); //jpg 이미지를 공유 하기 위해 Type을 정의합니다.
                intent.putExtra(Intent.EXTRA_STREAM, uri); //사진의 Uri를 가지고 옵니다.
                startActivity(Intent.createChooser(intent, "Choose")); //Activity를 이용하여 호출 합니다.
            }
        });
    }


    public void imageSend() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);

                intent.setType("image/*");

                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/더 함";
                Uri photoUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", new File(path));
                //File dirName = new File(path);  //디렉토리를 지정합니다.

                // String file = path; //공유할 이미지 파일 명
                // File files = new File(dirName, file); //image 파일의 경로를 설정합니다.
                //  Uri uri = FileProvider.getUriForFile(getContext(), "com.example.theham.MainActivity", dirName);
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///" + photoUri));

                // intent.putExtra(Intent.EXTRA_STREAM, uri); //사진의 Uri를 가지고 옵니다.
                Intent chooser = Intent.createChooser(intent, "명함 전송하기");
                startActivity(chooser);
            }
        });

    }

}