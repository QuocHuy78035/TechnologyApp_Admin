package com.example.technology_app.activities.cart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technology_app.R;
import com.example.technology_app.activities.payment.PaymentActivity;
import com.example.technology_app.adapters.CartAdapter;
import com.example.technology_app.models.CartModel;
import com.example.technology_app.models.EventBus.CaculatorSumEvent;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartActivity extends AppCompatActivity {
    TextView emptyCart, totalPrice;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnBuy;
    CartAdapter cartAdapter;
    List<CartModel.Item> cartList;
    CartModel cartModelView;

    int totalPriceProduct;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        initView();
        initControl();
        //calculatorSumPrice();
    }

    private void calculatorSumPrice() {
        totalPriceProduct = 0;
        for(int i = 0; i < cartList.size(); i++){
            String value = cartList.get(i).getProduct().getSale_price().replaceAll("[^\\d]", "");
            totalPriceProduct += Integer.parseInt(value) * cartList.get(i).getQuantity();
        }
        //DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        totalPrice.setText(totalPriceProduct +"đ");
    }

    void getCartUser(){
        String userId = Paper.book().read("userId");
        String accessToken = Paper.book().read("accessToken");
        compositeDisposable.add(api.getCartUser(userId, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        cartModel -> {
                            if (cartModel.getStatus() == 200) {
                                cartModelView = cartModel;
                                cartList = cartModel.getMetadata().getCart().getItems();
                                Log.d("SizeCartList", "" + cartList.size());

                                // Set up RecyclerView and adapter here
                                if (cartList == null || cartList.isEmpty()) {
                                    Log.d("CheckLengCartList", "Empty Cart");
                                    emptyCart.setVisibility(View.VISIBLE);
                                } else {
                                    Log.d("CheckLengCartList", "" + cartList.size());
                                    cartAdapter = new CartAdapter(getApplicationContext(), cartList);
                                    recyclerView.setAdapter(cartAdapter);
                                }
                            } else {
                                Log.d("Fail", "get cart Fail");
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }


    private void initView() {
        emptyCart = findViewById(R.id.txtEmptyCart);
        recyclerView = findViewById(R.id.recycleviewCart);
        toolbar = findViewById(R.id.toolbarCart);
        totalPrice = findViewById(R.id.txtTotalPrice);
        btnBuy = findViewById(R.id.btnBuy);
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
        getCartUser();
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (cartList == null || cartList.isEmpty()) {
            Log.d("CheckLengCartList", "Empty Cart");
            emptyCart.setVisibility(View.VISIBLE);
        } else {
            Log.d("CheckLengCartList", "" + cartList.size());
            cartAdapter = new CartAdapter(getApplicationContext(), cartList);
            recyclerView.setAdapter(cartAdapter);
        }
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra("totalPrice", totalPriceProduct);
                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    protected void onStop() {
//        EventBus.getDefault().unregister(this);
//        super.onStop();
//    }

//    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
//    public  void eventTinhTien(CaculatorSumEvent event){
//        if(event != null){
//            calculatorSumPrice();
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}