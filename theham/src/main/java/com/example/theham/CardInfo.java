package com.example.theham;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CardInfo extends AppCompatActivity {
    Toolbar info_toolbar;
    TextView card_info_name, card_info_email, card_info_tel, card_info_division;


    public static ImageView card_info_image;
    public static Bitmap bitmap;
    public static byte[] byteArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageBtnClicked();
        customActionBars();
        getStrings();
        //    getScannedImage();


    }


    public void customActionBars() {

        info_toolbar = findViewById(R.id.cd_info_toolbar);
        setSupportActionBar(info_toolbar);
        ActionBar actionBar = getSupportActionBar();

        info_toolbar.setTitle("명함 정보");
        info_toolbar.setTitleTextColor(Color.parseColor("#ffffff"));


        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

    }

    public void getStrings() {

        card_info_name = findViewById(R.id.card_info_name);
        card_info_email = findViewById(R.id.card_info_email);
        card_info_tel = findViewById(R.id.card_info_tel);
        card_info_division = findViewById(R.id.card_info_division);

        Intent intent = getIntent();

        String user_name = intent.getStringExtra("putName");
        String user_email = intent.getStringExtra("putEmail");
        String user_tel = intent.getStringExtra("putTel");
        String user_division = intent.getStringExtra("putDivision");

        card_info_name.setText(user_name);
        card_info_email.setText(user_email);
        card_info_tel.setText(user_tel);
        card_info_division.setText(user_division);

        String info_name = intent.getStringExtra("putName");
        String info_email = intent.getStringExtra("putEmail");
        String info_tel = intent.getStringExtra("putTel");
        String info_division = intent.getStringExtra("putDivision");

        card_info_name.setText(info_name);
        card_info_email.setText(info_email);
        card_info_tel.setText(info_tel);
        card_info_division.setText(info_division);

    }

    public void imageBtnClicked() {
        card_info_image = findViewById(R.id.card_info_image);

        card_info_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CardInfo.this, ImageScan.class));

            }
        });
    }

    public static void getScannedImage() {
        // ImageScan에서 받아오기

        //  byteArray = CardInfo.byteArray;
        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        card_info_image.setImageBitmap(bitmap);
    }

    public static void putScannedImage() {
        // RecyclerFragment로 뿌려주기
        getScannedImage();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.card_info_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:


//                RecyclerFragment fm = new RecyclerFragment();
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("bitmap", bitmap);
//                fm.setArguments(bundle);

//                Intent intent = new Intent(CardInfo.this, RecyclerView.class);
//                intent.putExtra("bitmap", bitmap);

                finish();

        }
        return true;
    }


}
