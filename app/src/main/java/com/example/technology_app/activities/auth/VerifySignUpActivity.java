package com.example.technology_app.activities.auth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class VerifySignUpActivity extends AppCompatActivity {

    TextView txtResendCode, txtEmailVerifySignup;
    EditText otp1, otp2, otp3, otp4;
    Button btnVerify;
    Api api;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean resendEnabled = false;
    private int resendTime = 60 * 15;
    private int selectETPosition = 0;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_sign_up);
        initView();
        initControl();
    }

    private void initControl() {
        txtResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resendEnabled) {
                    startCountDownTime();
                }
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String otp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString();
                if(otp.length() == 4){
                    verify(otp);
                }
            }
        });
    }

    private void verify(String otp) {

        if (TextUtils.isEmpty(otp)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập OTP", Toast.LENGTH_SHORT).show();
        }else{
            compositeDisposable.add(api.verifyCodeForSignUp(email, otp)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                Log.d("CheckStatusCode", "" + userModel.getStatusCode());
                                if (userModel.statusCode == 201) {
                                    Log.d("userId", userModel.getMetadata().getUser().get_id());
                                    Log.d("Email", userModel.getMetadata().getUser().getEmail());
                                    Log.d("AccessToken", userModel.getMetadata().getTokens().getAccessToken());
                                    Log.d("refreshToken", userModel.getMetadata().getTokens().getRefreshToken());
                                    Toast.makeText(getApplicationContext(), "Success: " + userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    Paper.book().write("userId", userModel.getMetadata().getUser().get_id());
                                    Paper.book().write("email", userModel.getMetadata().getUser().getEmail());
                                    Paper.book().write("accessToken", userModel.getMetadata().getTokens().getAccessToken());
                                    Paper.book().write("refreshToken", userModel.getMetadata().getTokens().getRefreshToken());

                                    String pass = Paper.book().read("password");
                                    if(pass != null){
                                        Log.d("PassIs", pass);
                                    }else{
                                        Log.d("NoPass", "Pass is null");
                                    }
                                    assert pass != null;
                                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        // Sign in success, update UI with the signed-in user's information
                                                        Log.d("TAG", "createUserWithEmail:success");
                                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                        startActivity(intent);
                                                        FirebaseUser user = firebaseAuth.getCurrentUser();

                                                        finish();
//                                                        updateUI(user);
                                                    } else {
                                                        // If sign in fails, display a message to the user.
                                                        Log.d("TAG", "createUserWithEmail:failure", task.getException());
                                                        Toast.makeText(VerifySignUpActivity.this, "Authentication failed.",
                                                                Toast.LENGTH_SHORT).show();
                                                        //updateUI(null);
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(getApplicationContext(), "Fail: " + userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Log.d("Log", "123" + throwable.getMessage());
                                Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    )
            );
        }
    }

    private void startCountDownTime() {
        resendEnabled = false;
        txtResendCode.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendTime * 1000L, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                txtResendCode.setText("Resend Code (" + (millisUntilFinished / 1000) + ")");
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                resendEnabled = true;
                txtResendCode.setText("Resend Code");
                txtResendCode.setTextColor(getResources().getColor(R.color.primaryColor));
            }
        }.start();
    }

    private void initView() {
        Paper.init(this);
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
        firebaseAuth = FirebaseAuth.getInstance();
        btnVerify = findViewById(R.id.btnVerifyRegister);
        otp1 = findViewById(R.id.otpRegister1);
        otp2 = findViewById(R.id.otpRegister2);
        otp3 = findViewById(R.id.otpRegister3);
        otp4 = findViewById(R.id.otpRegister4);
        txtResendCode = findViewById(R.id.txtResendCodeRegister);
        txtEmailVerifySignup = findViewById(R.id.txtEmailVerifySignup);

        email = getIntent().getStringExtra("email");
        txtEmailVerifySignup.setText(email);

        otp1.addTextChangedListener(textWatcher);
        otp2.addTextChangedListener(textWatcher);
        otp3.addTextChangedListener(textWatcher);
        otp4.addTextChangedListener(textWatcher);

        showKeyBoard(otp1);
    }

    private void showKeyBoard(EditText otp) {
        otp.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otp, InputMethodManager.SHOW_IMPLICIT);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() > 0){
                if(selectETPosition == 0){
                    selectETPosition = 1;
                    showKeyBoard(otp2);
                }else if(selectETPosition == 1){
                    selectETPosition = 2;
                    showKeyBoard(otp3);
                } else if (selectETPosition == 2) {
                    selectETPosition = 3;
                    showKeyBoard(otp4);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_DEL){
            if(selectETPosition == 3){
                selectETPosition = 2;
                showKeyBoard(otp3);
            }else if(selectETPosition == 2){
                selectETPosition = 1;
                showKeyBoard(otp2);
            }else if(selectETPosition == 1){
                selectETPosition = 0;
                showKeyBoard(otp1);
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
