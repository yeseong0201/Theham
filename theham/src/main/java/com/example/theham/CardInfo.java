package com.example.theham;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CardInfo extends AppCompatActivity {
    Toolbar info_toolbar;
    TextView card_info_name, card_info_email, card_info_tel, card_info_division;
    public static int degree = 0;

    public static ImageView card_info_image;
    public static Bitmap bitmap;
    public static byte[] byteArray;
    public static SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        card_info_name = findViewById(R.id.card_info_name);
        card_info_email = findViewById(R.id.card_info_email);
        card_info_tel = findViewById(R.id.card_info_tel);
        card_info_division = findViewById(R.id.card_info_division);

        imageBtnClicked();
        customActionBars();
        getStrings();
        getPreferences();

    }

    // 값 저장하기
    private void savePreferences() {
        // String image = BitmapToString(bitmap);
        String image = BitmapToString(bitmap);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("image", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = pref.edit();
        editor.putString("imageString", image);
        editor.commit();
    }

    // bitmap 문자열로 저장하기
    public String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    // 값 불러오기
    private void getPreferences() {
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("image", MODE_PRIVATE);
        String image = pref1.getString("imageString", "");
        Bitmap bitmap = StringtoBitmap(image);

        if (bitmap != null) {
            card_info_image.setImageBitmap(bitmap);
        }

        bitmap = null;
    }

    // String 값을 Bitmap으로 전환하기
    public Bitmap StringtoBitmap(String encodedString) {
        try {
            byte[] encodedByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodedByte, 0, encodedByte.length);
            return bitmap;

        } catch (Exception e) {
            e.getMessage();
            return null;

        }

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

        Intent intent = getIntent();

        String user_name = intent.getStringExtra("putName");
        String user_email = intent.getStringExtra("putEmail");
        String user_tel = intent.getStringExtra("putTel");
        String user_division = intent.getStringExtra("putDivision");

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

    public static void getScannedImage() {
        // ImageScan에서 받아오기

        //  byteArray = CardInfo.byteArray;

        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        try {
            card_info_image.setImageBitmap(getRotatedBitmap(bitmap, degree));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getOrientationOfImage(String filepath) {
        ExifInterface exif = null;

        try {
            exif = new ExifInterface(filepath);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);

        if (orientation != -1) {
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
            }
        }

        return 0;
    }

    public static Bitmap getRotatedBitmap(Bitmap bitmap, int degrees) throws Exception {
        if (bitmap == null) return null;
        if (degrees == 0) return bitmap;

        Matrix m = new Matrix();
        m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
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

                if (bitmap != null) {
                    savePreferences();
                    finish();


                }
                else {
                    Toast.makeText(this, "명함을 스캔해주세요.", Toast.LENGTH_SHORT).show();
                }


        }
        return true;
    }


}
