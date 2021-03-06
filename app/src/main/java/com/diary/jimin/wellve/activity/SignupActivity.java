package com.diary.jimin.wellve.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diary.jimin.wellve.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    private EditText emailEditText;
    private EditText pwEditText;
    private EditText pwCheckEditText;
    private EditText nicknameEditText;
    private Button signUpButton;
    private Button backButton;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = FirebaseFirestore.getInstance();

        init();


        signUpButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNameCheck();
            }
        });

//        signUpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SignupActivity.this, TypeCheckActivity.class);
//                startActivity(intent);
//            }
//        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    void init() {
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        pwEditText = (EditText)findViewById(R.id.pwEditText);
        pwCheckEditText = (EditText)findViewById(R.id.pwCheckEditText);
        nicknameEditText = (EditText)findViewById(R.id.nicknameEditText);
        signUpButton = (Button)findViewById(R.id.signUpButton);
        backButton = (Button)findViewById(R.id.signUpBackButton);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void isNameCheck() {
        final String nickName = nicknameEditText.getText().toString();

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            boolean check = false;
                            for(QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("nameCheck", document.getData().get("name").toString());
                                if(nickName.equals(document.getData().get("nickname"))) {
                                    check = true;
                                    Toast.makeText(SignupActivity.this, "이미 사용중인 닉네임입니다.", Toast.LENGTH_SHORT).show();
                                }

                            }
                            if(!check) {
                                signUp();
                            }
                        }
                    }
                });

    }

    private void signUp() {
        String email = emailEditText.getText().toString();
        String password = pwEditText.getText().toString();
        String passwordCheck = pwCheckEditText.getText().toString();
        final String nickName = nicknameEditText.getText().toString();

        if(email.length()>0 && password.length()>0 && passwordCheck.length()>0 && nickName.length()>0) {
            if(password.equals(passwordCheck)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
//                                    Toast.makeText(SignupActivity.this, "회원가입에 성공했습니다.",
//                                            Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(SignupActivity.this, TypeCheckActivity.class);
                                    intent.putExtra("nickName",nickName);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                } else {

                                    /*********************** To Do List ********************/

                                    /** 여기서 password의 길이가 6이상이여야 함. exception 처리 하기 **/

                                    /*******************************************************/

                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignupActivity.this, "이미 사용중인 이메일입니다.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            } else {
                Toast.makeText(SignupActivity.this, "비밀번호가 일치하지 않습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SignupActivity.this, "빈칸을 입력해주세요.",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
