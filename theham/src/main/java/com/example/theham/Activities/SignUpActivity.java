package com.example.theham.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.theham.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {
    EditText email_join;
    EditText pwd_join;
    EditText check_pw;
    EditText name;
    Button btn;
    FirebaseAuth firebaseAuth;
    ImageView checkcircle1, checkcircle2, checkcircle3;


    String email = "1";
    String pwd = "21";

    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{6,16}$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.NAME);
        email_join = findViewById(R.id.sign_up_email);
        pwd_join = findViewById(R.id.sign_up_pwd);
        check_pw = findViewById(R.id.check_pw);
        btn = findViewById(R.id.sign_up_btn);

        checkcircle1 = findViewById(R.id.CheckCircle1);
        checkcircle2 = findViewById(R.id.CheckCircle2);
        checkcircle3 = findViewById(R.id.CheckCircle3);


        firebaseAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_join.getText().toString().trim();
                pwd = pwd_join.getText().toString().trim();

//                if (check_pw.getText().toString() != pwd_join.getText().toString()) {
//                    Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
//                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isEmpty() == true) {
                    Toast.makeText(SignUpActivity.this, "이메일 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                } else if (pwd.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "비밀번호는 6자리 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "회원가입 완료!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        //Toast.makeText(SignUpActivity.this, "등록 에러", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
                }
            }
        });

        email_join.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkcircle1.clearColorFilter();
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (email_join.getText().toString().length() == 0) {
                    checkcircle1.clearColorFilter();

                } else {
                    checkcircle1.setColorFilter(Color.rgb(14, 168, 40));
                }
            }

        });

        pwd_join.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkcircle2.clearColorFilter();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (pwd_join.getText().toString().length() == 0) {
                    checkcircle2.clearColorFilter();
                } else {

                    checkcircle2.setColorFilter(Color.rgb(14, 168, 40));
                }

            }
        });

        check_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkcircle3.clearColorFilter();

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (check_pw.getText().toString().length() == 0) {
                    checkcircle3.clearColorFilter();
                } else {

                    checkcircle3.setColorFilter(Color.rgb(14, 168, 40));
                }

            }
        });


    }


    // 이메일 유효성 검사
    public boolean isValidEmail() {
        if (email.isEmpty()) {
            // 이메일 공백
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            return false;
        } else {
            return true;
        }
    }

    // 비밀번호 유효성 검사
    public boolean isValidPasswd() {
        if (pwd.isEmpty()) {
            // 비밀번호 공백
            return false;
        } else if (!PASSWORD_PATTERN.matcher(pwd).matches()) {
            // 비밀번호 형식 불일치
            return false;
        } else {
            return true;
        }
    }
}