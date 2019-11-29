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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CardInfo extends AppCompatActivity {
    Toolbar info_toolbar;
    TextView card_info_name, card_info_email, card_info_tel, card_info_division;
    ImageView card_info_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);

        imageBtnClicked();
        customActionBars();
        getStrings();
//        getScannedImage();


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

        String user_name = intent.getStringExtra("info_name");
        String user_email = intent.getStringExtra("info_email");
        String user_tel = intent.getStringExtra("info_tel");
        String user_division = intent.getStringExtra("info_division");

        card_info_name.setText(user_name);
        card_info_email.setText(user_email);
        card_info_tel.setText(user_tel);
        card_info_division.setText(user_division);


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

    public void getScannedImage() {
        Intent intent = getIntent();

        byte[] arr = intent.getByteArrayExtra("Scanning image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        card_info_image.setImageBitmap(bitmap);


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

                finish();


        }
        return true;
    }
}
