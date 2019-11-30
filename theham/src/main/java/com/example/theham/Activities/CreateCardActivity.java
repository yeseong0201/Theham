package com.example.theham.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.theham.Fragments.MakingFragment;
import com.example.theham.R;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateCardActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn, set_text_change, chooseColor;
    ConstraintLayout capture_layout;

    ColorPickerDialog colorPickerDialog;

    TextView company, name, tel, email, division, address;
    EditText company_edt, name_edt, tel_edt, email_edt, division_edt, address_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        btn = findViewById(R.id.cap_btn);
        capture_layout = findViewById(R.id.capture_layout);

        chooseColor = findViewById(R.id.chooseColor);

        company = findViewById(R.id.company_name);
        name = findViewById(R.id.self_name);
        tel = findViewById(R.id.self_tel);
        email = findViewById(R.id.self_email);
        division = findViewById(R.id.self_division);
        address = findViewById(R.id.self_address);

        company_edt = findViewById(R.id.company_name_edt);
        name_edt = findViewById(R.id.self_name_edt);
        tel_edt = findViewById(R.id.self_tel_edt);
        email_edt = findViewById(R.id.self_email_edt);
        division_edt = findViewById(R.id.self_division_edt);
        address_edt = findViewById(R.id.self_address_edt);

        set_text_change = findViewById(R.id.set_text_change);



        typingText();


        btn.setOnClickListener(this);

    }

    public void typingText() {
        set_text_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int totalTextLength = company_edt.getText().length() + name_edt.getText().length()
                        + tel_edt.getText().length() + email_edt.getText().length() + division_edt.getText().length() + address.getText().length();

                company.setText(company_edt.getText().toString());
                name.setText(name_edt.getText().toString());
                tel.setText(tel_edt.getText().toString());
                email.setText(email_edt.getText().toString());
                division.setText(division_edt.getText().toString());
                address.setText(address_edt.getText().toString());

            }
        });
    }

    @Override
    public void onClick(View v) {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CardCapture";

        File file = new File(path);

        if (!file.exists()) {

            file.mkdirs();

            Toast.makeText(CreateCardActivity.this, "폴더가 생성되었습니다.", Toast.LENGTH_SHORT).show();

        }

        SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");

        Date date = new Date();

        capture_layout.buildDrawingCache();

        Bitmap captureview = capture_layout.getDrawingCache();


        // 여기

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        captureview.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        MainActivity.byteArray = stream.toByteArray();

        //

        FileOutputStream fos = null;

        try {

            fos = new FileOutputStream(path + "/Capture" + day.format(date) + ".jpeg");

            captureview.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path + "/Capture" + day.format(date) + ".JPEG")));

            Toast.makeText(CreateCardActivity.this, "명함이 저장되었습니다.", Toast.LENGTH_SHORT).show();

            fos.flush();

            fos.close();

            capture_layout.destroyDrawingCache();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        MakingFragment.getBitmapCardView();
        finish();

    }


}
