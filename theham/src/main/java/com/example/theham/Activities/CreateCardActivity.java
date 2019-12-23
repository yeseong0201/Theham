package com.example.theham.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.theham.Fragments.MakingFragment;
import com.example.theham.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import yuku.ambilwarna.AmbilWarnaDialog;

public class CreateCardActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn, set_text_change, chooseColor, chooseLogo, saveBtn;
    public ConstraintLayout capture_layout;

    Bitmap captureview;

    int tColor, n = 0;
    int cnt = 0;
    private final int CAMERA_CODE = 1111;
    private final int GALLERY_CODE = 1112;

    private Uri photoUri;
    private String currentPhotoPath;//실제 사진 파일 경로
    String mImageCaptureName;//이미지 이름

    TextView company, name, tel, email, division, address;
    EditText company_edt, name_edt, tel_edt, email_edt, division_edt, address_edt;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);




        btn = findViewById(R.id.cap_btn);
        capture_layout = findViewById(R.id.capture_layout);

        chooseColor = findViewById(R.id.chooseColor);
        chooseLogo = findViewById(R.id.chooseLogo);
        saveBtn = findViewById(R.id.save_btn);

        company = findViewById(R.id.self_name);
        name = findViewById(R.id.company_name);
        tel = findViewById(R.id.self_tel);
        email = findViewById(R.id.self_email);
        division = findViewById(R.id.self_division);
        address = findViewById(R.id.self_address);
        logo = findViewById(R.id.logo);

        company_edt = findViewById(R.id.company_name_edt);
        name_edt = findViewById(R.id.self_name_edt);
        tel_edt = findViewById(R.id.self_tel_edt);
        email_edt = findViewById(R.id.self_email_edt);
        division_edt = findViewById(R.id.self_division_edt);
        address_edt = findViewById(R.id.self_address_edt);

        set_text_change = findViewById(R.id.set_text_change);


        typingText();

        openColorPicker();

        selectGallery();

        savePicture();

        btn.setOnClickListener(this);

    }

    public void openColorPicker() {
        chooseColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(CreateCardActivity.this, tColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        tColor = color;

                        capture_layout.setBackgroundColor(tColor);
                    }
                });
                colorPicker.show();
            }
        });

    }

    // 값 저장하기
    private void savePreferences() {

        String image = BitmapToString(captureview);
        SharedPreferences pref = getSharedPreferences("card", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = pref.edit();
        editor.putString("cardString", image);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case GALLERY_CODE:
                    sendPicture(data.getData()); //갤러리에서 가져오기
                    break;
                case CAMERA_CODE:
                    getPictureForPhoto(); //카메라에서 가져오기
                    break;

                default:
                    break;
            }

        }
    }

    private void sendPicture(Uri imgUri) {

        String imagePath = getRealPathFromURI(imgUri); // path 경로
        ExifInterface exif = null;

        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
        logo.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기

    }

    private File createImageFile() throws IOException {
        File dir = new File(Environment.getExternalStorageDirectory() + "/path/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mImageCaptureName = timeStamp + ".png";

        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/path/"

                + mImageCaptureName);
        currentPhotoPath = storageDir.getAbsolutePath();

        return storageDir;

    }

    private void getPictureForPhoto() {
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(currentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = 0;
        int exifDegree = 0;

        if (exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        } else {
            exifDegree = 0;
        }
        logo.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
    }

    private void selectGallery() {

        chooseLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_CODE);
            }
        });


    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap src, float degree) {

        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }


    public void typingText() {
        set_text_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                company_edt.setSelection(company_edt.length());
                email_edt.setSelection(email_edt.length());
                division_edt.setSelection(division_edt.length());
                address_edt.setSelection(address_edt.length());
                name_edt.setSelection(name_edt.length());
                tel_edt.setSelection(tel_edt.length());

                int totalTextLength = company_edt.getText().length() + name_edt.getText().length()
                        + tel_edt.getText().length() + email_edt.getText().length() + division_edt.getText().length() + address.getText().length();


                company.setText("");
                company.setText(company_edt.getText().toString());

                name.setText(name_edt.getText().toString());
                tel.setText(tel_edt.getText().toString());
                email.setText(email_edt.getText().toString());
                division.setText(division_edt.getText().toString());
                address.setText(address_edt.getText().toString());

                capture_layout.buildDrawingCache();
                captureview = capture_layout.getDrawingCache();

                // 여기
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                captureview.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                MainActivity.byteArray = stream.toByteArray();

                savePreferences();

            }
        });
    }

    public void savePicture() {

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/더 함";
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                    Toast.makeText(CreateCardActivity.this, "폴더가 생성되었습니다.", Toast.LENGTH_SHORT).show();
                }
                SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date();
                capture_layout.buildDrawingCache();
                captureview = capture_layout.getDrawingCache();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(path + "/Capture" + day.format(date) + ".jpeg");
                    captureview.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path +
                            "/Capture" + day.format(date) + ".JPEG")));
                    Toast.makeText(CreateCardActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    fos.flush();
                    fos.close();
                    capture_layout.destroyDrawingCache();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (company_edt.getText().length() <= 0 && name_edt.getText().length() <= 0 && tel_edt.getText().length() <= 0 && email_edt.getText().length() <= 0 &&
                division_edt.getText().length() <= 0 && name_edt.getText().length() <= 0) {
            Toast.makeText(CreateCardActivity.this, "정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {

            //  savePreferences();
            MakingFragment.getBitmapCardView();
            MakingFragment.getPreferences();

            finish();
        }

    }


}
