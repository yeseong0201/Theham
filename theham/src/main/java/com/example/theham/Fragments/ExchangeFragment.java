package com.example.theham.Fragments;

import android.content.Intent;
import android.content.pm.ResolveInfo;
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
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.network.storage.ImageUploadResponse;
import com.kakao.util.helper.log.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        // sendShare();
        shareImage();

        return v;

    }

    private void sendShare() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream fos = null;
                SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");

                List<ResolveInfo> resInfo = getContext().getPackageManager().queryIntentActivities(intent, 0);
                if (resInfo.isEmpty()) {
                    return;
                }

                List<Intent> shareIntentList = new ArrayList<Intent>();

                try {
                    fos = new FileOutputStream(path + "/Capture" + day.format(date) + ".jpeg");
                    // captureview.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path + "/Capture" + day.format(date) + ".JPEG")));
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (ResolveInfo info : resInfo) {
                    Intent shareIntent = (Intent) intent.clone();

                    // File dirName = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Download");
                    File dirName = new File(path + "/Capture" + day.format(date) + ".jpeg");
                    String file = path + "/Capture" + day.format(date) + ".jpeg";
                    File files = new File(dirName, file);
                    Uri uri = FileProvider.getUriForFile(mainActivity.getApplicationContext(), "com.example.theham", files);
                    shareIntent.setType("image/*");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "더 함");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "구글 http://www.google.com #");

                    shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + path + "/Capture" + day.format(date) + ".JPEG"));
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

//                try {
//                    KakaoLink link = KakaoLink.getKakaoLink(getActivity().getApplicationContext());
//                    KakaoTalkLinkMessageBuilder builder = link.createKakaoTalkLinkMessageBuilder();
//
//                    builder.addText("명함 보내기");
//                    builder.addAppButton("앱 실행하기");
//                    link.sendMessage(builder, getContext());
//
//                } catch (KakaoParameterException e) {
//                    e.printStackTrace();
//                }

//                TextTemplate params = TextTemplate.newBuilder("더 함", LinkObject.newBuilder().setWebUrl("https://developers.kakao.com").setMobileWebUrl("https://developers.kakao.com").build()).setButtonTitle("This is button").build();
//
//                Map<String, String> serverCallbackArgs = new HashMap<String, String>();
//                serverCallbackArgs.put("user_id", "${current_user_id}");
//                serverCallbackArgs.put("product_id", "${shared_product_id}");
//
//                KakaoLinkService.getInstance().sendDefault(getContext(), params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
//                    @Override
//                    public void onFailure(ErrorResult errorResult) {
//                        Logger.e(errorResult.toString());
//                    }
//
//                    @Override
//                    public void onSuccess(KakaoLinkResponse result) {
//                        // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
//                    }
//                });
                File imageFile = new File("path/of/image/file");

                KakaoLinkService.getInstance().uploadImage(getContext(), false, new File("gs://theham-3a483.appspot.com/logo.png"), new ResponseCallback<ImageUploadResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Logger.e(errorResult.toString());
                    }

                    @Override
                    public void onSuccess(ImageUploadResponse result) {
                        Logger.d(result.getOriginal().getUrl());
                    }
                });


//                FileOutputStream fos = null;
//                SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
//                Date date = new Date();
//                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/더 함";
//                File dirName = new File(path);  //디렉토리를 지정합니다.
//                // String file = path + "/Capture" + day.format(date) + ".jpeg"; //공유할 이미지 파일 명
//                String file = path + "/test1.jpeg"; //공유할 이미지 파일 명
//                File files = new File(dirName, file); //image 파일의 경로를 설정합니다.
//                Uri uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID, files);
//
//                // Uri mSaveImageUri = Uri.fromFile(files); //file의 경로를 uri로 변경합니다.
//                Intent intent = new Intent(Intent.ACTION_SEND); //전송 메소드를 호출합니다. Intent.ACTION_SEND
//                intent.setType("image/*"); //jpg 이미지를 공유 하기 위해 Type을 정의합니다.
//                intent.putExtra(Intent.EXTRA_STREAM, R.drawable.card_image); //사진의 Uri를 가지고 옵니다.
//                startActivity(Intent.createChooser(intent, "명함 보내기 ")); //Activity를 이용하여 호출 합니다.
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