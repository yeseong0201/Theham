package com.example.theham.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.theham.R;

public class SplashActivity extends AppCompatActivity {

    public static int MESSAGE_WHAT_TIMER = 1;

    TextView text1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        textDesign();

        handlerDelayStart(SplashActivity.MESSAGE_WHAT_TIMER, 1500);


    }


    Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {

            // 타이머 진행
            if (msg.what == MESSAGE_WHAT_TIMER) {

                Log.d("MESSAGE_WHAT_TIMER", " : MESSAGE_WHAT_TIMER ");

                Intent intentHome = new Intent(getApplicationContext(), LoginActivity.class);

                // 이동시 중간에 있는 액티비티 삭제
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // 액티비티가 이미 있으면 재사용, 이거없으면 재생성
                intentHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentHome);
                finish();

            }


        }
    };

    public void textDesign() {
        text1 = findViewById(R.id.text1);

        // '더'랑 '함'에 글자색 입히기
        Spannable spannable = (Spannable) text1.getText();
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#1a237e")), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#1a237e")), 11, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    }

    public void handlerDelayStart(int what, int time) {

        Log.d("handlerDelayStart", " : handlerDelayStart ");
        mHandler.sendEmptyMessageDelayed(what, time);
    }
}
