package com.example.technology_app.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.technology_app.R;
import com.example.technology_app.activities.main.MainActivity;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    boolean isShowPass = true;
    EditText email, pass;
    Button btnLogin;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    TextView txtNavigatorDangKi;
    ImageView iconPass;
    Api api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        initView();
        initControl();
    }

    private void initControl() {
        txtNavigatorDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailLogin = email.getText().toString().trim();
                String passLogin = pass.getText().toString().trim();
                if (TextUtils.isEmpty(emailLogin)) {
                    Toast.makeText(getApplicationContext(), "Please enter your Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(passLogin)) {
                    Toast.makeText(getApplicationContext(), "Please enter your Password", Toast.LENGTH_SHORT).show();
                } else {
                    //post data
                    if (user != null) {
                        //user da dang nhap firebase
                        login(emailLogin, passLogin);
                    } else {
                        //user da dang xuat khoi firebase => dang nhap lai
                        firebaseAuth.signInWithEmailAndPassword(emailLogin, passLogin)
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            user = firebaseAuth.getCurrentUser(); // Cập nhật user
                                            login(emailLogin, passLogin);
                                        } else {
                                            Toast.makeText(getApplicationContext(),  "Authentication failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
        iconPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShowPass){
                    isShowPass = false;
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iconPass.setImageResource(R.drawable.ic_pass_hide);
                }else{
                    isShowPass = true;
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iconPass.setImageResource(R.drawable.ic_pass_show);
                }
                pass.setSelection(pass.length());
            }
        });
    }

    private void login(String email, String pass) {
        // Make API call
        compositeDisposable.add(api.login(email, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.statusCode == 200) {
                                Toast.makeText(getApplicationContext(), "Success: " + userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                Paper.book().write("userId", userModel.getMetadata().getUser().get_id());
                                Paper.book().write("email", userModel.getMetadata().getUser().getEmail());
                                Paper.book().write("accessToken", userModel.getMetadata().getTokens().getAccessToken());
                                Paper.book().write("refreshToken", userModel.getMetadata().getTokens().getRefreshToken());
                                Paper.book().write("password", pass);
                                //Paper.book().write("tokenFirebase", userModel.getMetadata().getUser().getTokenFirebase());
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Fail: " + userModel.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    private void initView() {
        Paper.init(this);
        email = findViewById(R.id.inputEmailLogin);
        pass = findViewById(R.id.inputPassLogin);
        btnLogin = findViewById(R.id.btnLogin);
        iconPass = findViewById(R.id.passIcon);
        txtNavigatorDangKi = findViewById(R.id.txtNavigatorDangKi);
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        if (Paper.book().read("accessToken") != null) {
            email.setText(Objects.requireNonNull(Paper.book().read("email")).toString());
            pass.setText(Objects.requireNonNull(Paper.book().read("pass")).toString());
            if (Paper.book().read("isLogin") != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //dangNhap(Paper.book().read("email"), Paper.book().read("pass"));
                    }
                }, 1000);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}