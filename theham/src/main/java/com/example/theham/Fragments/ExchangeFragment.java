package com.example.theham.Fragments;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.theham.Activities.MainActivity;
import com.example.theham.R;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.TextTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        //shareImage();

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

                    File dirName = new File(path + "/Capture" + day.format(date) + ".jpeg");
                    String file = path + "/Capture" + day.format(date) + ".jpeg";
                    File files = new File(dirName, file);
//                    Uri uri = FileProvider.getUriForFile(mainActivity.getApplicationContext(), "com.example.theham", files);
                    shareIntent.setType("image/*");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "더 함");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "https://firebasestorage.googleapis.com/v0/b/theham-3a483.appspot.com/o/%E1%84%86%E1%85%A7%E1%86%BC%E1%84%92%E1%85%A1%E1%86%B7%E1%84%89%E1%85%A1%E1%84%8C%E1%85%B5%E1%86%AB1.jpeg?alt=media&token=053df6ad-27c6-4cdb-b75c-b3cf3534d48b");

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

                TextTemplate params = TextTemplate.newBuilder("더 함", LinkObject.newBuilder().setWebUrl("https://https://firebasestorage.googleapis.com/v0/b/theham-3a483.appspot.com/o/%E1%84%86%E1%85%A7%E1%86%BC%E1%84%92%E1%85%A1%E1%86%B7%E1%84%89%E1%85%A1%E1%84%8C%E1%85%B5%E1%86%AB1.jpeg?alt=media&token=053df6ad-27c6-4cdb-b75c-b3cf3534d48b")
                        .setMobileWebUrl("https://https://firebasestorage.googleapis.com/v0/b/theham-3a483.appspot.com/o/%E1%84%86%E1%85%A7%E1%86%BC%E1%84%92%E1%85%A1%E1%86%B7%E1%84%89%E1%85%A1%E1%84%8C%E1%85%B5%E1%86%AB1.jpeg?alt=media&token=053df6ad-27c6-4cdb-b75c-b3cf3534d48b").build()).setButtonTitle("명함보").build();

                Map<String, String> serverCallbackArgs = new HashMap<String, String>();
                serverCallbackArgs.put("user_id", "${current_user_id}");
                serverCallbackArgs.put("product_id", "${shared_product_id}");

                KakaoLinkService.getInstance().sendDefault(getContext(), params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Logger.e(errorResult.toString());
                    }

                    @Override
                    public void onSuccess(KakaoLinkResponse result) {
                        // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
                    }
                });

            }
        });
    }

}