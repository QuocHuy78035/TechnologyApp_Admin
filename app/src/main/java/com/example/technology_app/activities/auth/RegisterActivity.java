package com.example.technology_app.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.technology_app.R;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    EditText edtEmail, edtUserName, edtPass, edtPassConfirm;
    boolean isShowPass = true;
    ImageView iconPass, iconPassConfirm;
    boolean isShowPassConfirm = true;

    TextView txtNavigatorLogin;

    Button btnRegister;
    Api api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        initView();
        initControl();
    }

    private void initControl() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        iconPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShowPass){
                    isShowPass = false;
                    edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iconPass.setImageResource(R.drawable.ic_pass_hide);
                }else{
                    isShowPass = true;
                    edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iconPass.setImageResource(R.drawable.ic_pass_show);
                }
                edtPass.setSelection(edtPass.length());
            }
        });

        txtNavigatorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        iconPassConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShowPassConfirm){
                    isShowPassConfirm = false;
                    edtPassConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iconPassConfirm.setImageResource(R.drawable.ic_pass_hide);
                }else{
                    isShowPassConfirm = true;
                    edtPassConfirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iconPassConfirm.setImageResource(R.drawable.ic_pass_show);
                }
                edtPassConfirm.setSelection(edtPassConfirm.length());
            }
        });
    }

    private void register() {
        String email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        String passConfirm = edtPassConfirm.getText().toString().trim();
        String userName = edtUserName.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(passConfirm)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập user name", Toast.LENGTH_SHORT).show();
        } else {
            if (pass.equals(passConfirm)) {
                //post data
                compositeDisposable.add(api.signup(userName, email, pass, passConfirm)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                signUp -> {
                                    if (signUp.getStatus() == 201) {
                                        Paper.book().write("password", pass);
                                        Intent intent = new Intent(getApplicationContext(), VerifySignUpActivity.class);
                                        intent.putExtra("email", email);
                                        intent.putExtra("pass", pass);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Fail: " + signUp.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        )
                );

            } else {
                Toast.makeText(getApplicationContext(), "Mật khẩu và mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        edtEmail = findViewById(R.id.inputEmailRegister);
        edtUserName = findViewById(R.id.inputUserNameRegister);
        edtPass = findViewById(R.id.inputPassRegister);
        edtPassConfirm = findViewById(R.id.inputPassConfirmRegister);
        btnRegister = findViewById(R.id.btnRegister);
        txtNavigatorLogin = findViewById(R.id.txtNavigatorLogin);
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
        iconPass = findViewById(R.id.passIconRegister);
        iconPassConfirm = findViewById(R.id.passIconPassConfirmRegister);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}