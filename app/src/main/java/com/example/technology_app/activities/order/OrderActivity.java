package com.example.technology_app.activities.order;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technology_app.R;
import com.example.technology_app.adapters.OrderAdapter;
import com.example.technology_app.models.EventBus.OrderEvent;
import com.example.technology_app.models.GetOrder.GetOrderModel;
import com.example.technology_app.models.GetOrder.Order;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Api api;
    RecyclerView recyclerViewOrder;
    String userId, accessToken, status;
    GetOrderModel getOrderModelView;
    Toolbar toolbar;
    Order order;
    int statusOrder;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        initView();
        initToolBar();
        initControl();
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initControl() {
        getOrder(userId, accessToken, status);
    }

    private void getOrder(String userId, String accessToken, String status) {
        compositeDisposable.add(api.getOrderUser(userId, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        getOrderModel -> {
                            if (getOrderModel.getStatus() == 200) {
                                Log.d("Success", "get order success" + getOrderModel.getMetadata().getOrders().size());
                                getOrderModelView = getOrderModel;
                                OrderAdapter orderAdapter = new OrderAdapter(getOrderModelView.getMetadata().getOrders(), this);
                                recyclerViewOrder.setAdapter(orderAdapter);
                            } else {
                                Log.d("Fail", "get order failed with status: " + getOrderModel.getStatus());
                            }
                        },
                        throwable -> {
                            if (throwable instanceof retrofit2.HttpException) {
                                retrofit2.HttpException httpException = (retrofit2.HttpException) throwable;
                                int statusCode = httpException.code();
                                String errorBody = "";
                                try {
                                    errorBody = Objects.requireNonNull(Objects.requireNonNull(httpException.response()).errorBody()).string();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Log.e("Error", "HTTP Error: " + statusCode + " " + errorBody);
                            } else {
                                Log.e("Error", "get order error: " + throwable.getMessage());
                                Log.e("Error", Log.getStackTraceString(throwable));
                            }
                            Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    private void initView() {
        Paper.init(this);
        userId = Paper.book().read("userId");
        accessToken = Paper.book().read("accessToken");
        status = "pending";
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
        recyclerViewOrder = findViewById(R.id.recycleOrder);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerViewOrder.setLayoutManager(manager);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void evenOrder(OrderEvent orderEvent){
        if(orderEvent != null){
            order = orderEvent.getOrder();
            showOrderDialog();
        }
    }

    private void showOrderDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_order, null);
        Spinner spinner = view.findViewById(R.id.spinner_dialog);
        AppCompatButton btnConfirm = view.findViewById(R.id.btnConfirm_dialog);

        List<String> list = new ArrayList<>();
        list.add("pending");
        list.add("confirmed");
        list.add("cancelled");
        list.add("delivered");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusOrder = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatusOrder(userId, accessToken, order.getId(), list.get(statusOrder));
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void updateStatusOrder(String userId, String accessToken, String orderId, String status) {
        compositeDisposable.add(api.updateStatusOrder(userId, accessToken, orderId, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        getOrderModel -> {
                            if (getOrderModel.getStatus() == 200) {
                                Log.d("Success", "update order success");
//                                getOrderModelView = getOrderModel;
//                                OrderAdapter orderAdapter = new OrderAdapter(getOrderModelView.getMetadata().getOrders(), this);
//                                recyclerViewOrder.setAdapter(orderAdapter);
                                getOrder(userId, accessToken, status);
                                dialog.dismiss();
                            } else {
                                Log.d("Fail", "update order failed with status: " + getOrderModel.getStatus());
                            }
                        },
                        throwable -> {
                            if (throwable instanceof retrofit2.HttpException) {
                                retrofit2.HttpException httpException = (retrofit2.HttpException) throwable;
                                int statusCode = httpException.code();
                                String errorBody = "";
                                try {
                                    errorBody = Objects.requireNonNull(Objects.requireNonNull(httpException.response()).errorBody()).string();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Log.e("Error", "HTTP Error: " + statusCode + " " + errorBody);
                            } else {
                                Log.e("Error", "update order error: " + throwable.getMessage());
                                Log.e("Error", Log.getStackTraceString(throwable));
                            }
                            Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}