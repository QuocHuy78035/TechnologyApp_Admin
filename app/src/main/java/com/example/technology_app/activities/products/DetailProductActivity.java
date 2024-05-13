package com.example.technology_app.activities.products;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.technology_app.R;
import com.example.technology_app.activities.cart.CartActivity;
import com.example.technology_app.models.CartModel;
import com.example.technology_app.models.Products.Laptop.ProductDetail;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailProductActivity extends AppCompatActivity {
    TextView txtProductName, txtProductPrice, txtProductDes;
    Button btnAddToCart, btnWatchVid;
    ImageView imgProduct;
    ProductDetail product;
    Spinner spinner;
    Toolbar toolbar;
    NotificationBadge notificationBadge;
    FrameLayout frameLayout;
    Api api;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int quantity;

    //CartModel cartModelView;
    CartModel cartModelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_product);
        Paper.init(this);
        initView();
        initControl();
        initData();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        product = (ProductDetail) getIntent().getSerializableExtra("detailProduct");
        assert product != null;
        Log.d("Test123123123", product.getName());
        assert product != null;
        txtProductName.setText(product.getName());
        txtProductDes.setText(product.getInformation());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        //txtProductPrice.setText(decimalFormat.format(product.getPrice()) +"đ");
        txtProductPrice.setText((product.getPrice()));
        Glide.with(getApplicationContext()).load(product.getImages().get(0)).into(imgProduct);
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterSpin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, so);
        spinner.setAdapter(adapterSpin);
    }

    private void initControl() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                quantity = Integer.parseInt(selectedItem);
                Log.d("SelectedItem", selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnWatchVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VidDemoProductActivity.class);
                startActivity(intent);
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = Paper.book().read("userId");
                String accessToken = Paper.book().read("accessToken");

                if(userId != null && accessToken != null){
                    compositeDisposable.add(api.addToCart(userId, accessToken, product.get_id(), quantity)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    cartModel -> {
                                        if (cartModel.getStatus() == 200) {
                                            Log.d("Success", "add to cart Success");
                                            getCartUser();
                                        }else{
                                            Log.d("Fail", "add to cart Fail");
                                        }
                                    },
                                    throwable -> {
                                        Log.d("SuccessFail", "" + throwable.getMessage());
                                        Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            )
                    );
                }else{
                    Toast.makeText(getApplicationContext(), "Token or UserId is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        txtProductName = findViewById(R.id.txt_detailProductName);
        txtProductPrice = findViewById(R.id.txt_detailProductPrice);
        imgProduct = findViewById(R.id.img_detailProduct);
        txtProductDes = findViewById(R.id.txtDetailProductDes);
        frameLayout = findViewById(R.id.frameCartDetailProduct);
        notificationBadge = findViewById(R.id.menu_quantity);
        spinner = findViewById(R.id.spinner);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        toolbar = findViewById(R.id.toolbar_detailProduct);
        btnWatchVid = findViewById(R.id.btnWatchVid);
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
        getCartUser();


        //frameLayout = findViewById(R.id.frameGioHang);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(cart);
            }
        });

//        if(Utils.mangGioHang != null){
//            int totalItem = 0;
//            for(int i =0; i < Utils.mangGioHang.size(); i++){
//                totalItem += Utils.mangGioHang.get(i).getSoluongl();
//            }
//            notificationBadge.setText(String.valueOf(totalItem));
//        }
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
                                Log.d("Size1" , "" + cartModelView.getMetadata().getCart().getItems().size());
                                Log.d("Success", "get cart Success");

                                if(cartModelView != null){
                                    Log.d("Size2", "" + cartModelView.getMetadata().getCart().getItems().size());
                                    notificationBadge.setText(String.valueOf(cartModelView.getMetadata().getCart().getItems().size()));
                                }
                            }else{
                                Log.d("Fail", "get cart Fail");
                            }
                        },
                        throwable -> {

                            Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}