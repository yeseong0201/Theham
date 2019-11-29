package com.example.theham.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.theham.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button sign_up_btn, sign_in_btn;
    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    // 이메일과 비밀번호
    EditText editTextEmail;
    EditText editTextPassword;

    public String email;
    public String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sign_up_btn = findViewById(R.id.btn_signUp);
        sign_in_btn = findViewById(R.id.btn_signIn);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            Toast.makeText(this, "자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
            finish();

        }

        sign_in_btn.setOnClickListener(this);
        sign_up_btn.setOnClickListener(this);


        editTextEmail = findViewById(R.id.et_eamil);
        editTextPassword = findViewById(R.id.et_password);

        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

    }


    @Override
    public void onClick(View v) {
        if (v == sign_up_btn) {
            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));

        }
        if (v == sign_in_btn && editTextEmail.getText().toString().length() != 0 && editTextPassword.getText().toString().length() != 0) {
            firebaseAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String email = editTextEmail.getText().toString();
                            String password = editTextPassword.getText().toString();
                            if (task.isSuccessful()) {
                                // 로그인 성공
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                Toast.makeText(getApplicationContext(), R.string.success_login, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                // 로그인 실패
                                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isEmpty() == true) {
                                    Toast.makeText(LoginActivity.this, "이메일 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                                } else if (password.length() < 6) {
                                    Toast.makeText(LoginActivity.this, "비밀번호는 6자리 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                }
                            }
                        }
                    });
        }
    }
}