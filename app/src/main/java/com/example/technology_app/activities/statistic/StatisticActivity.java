package com.example.technology_app.activities.statistic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.technology_app.R;
import com.example.technology_app.activities.main.MainActivity;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StatisticActivity extends AppCompatActivity {
    PieChart pieChart;
    Toolbar toolbar;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String userId, accessToken;
    Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistic);
        initView();
        actionToolBar();
        getDataChart();
    }

    private void getDataChart() {
        List<PieEntry> listData = new ArrayList<>();
        compositeDisposable.add(api.getStatistic(userId, accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        getStatisticModel -> {
                            if (getStatisticModel.getStatus() == 200) {
                                for(int i = 0; i < getStatisticModel.getMetadata().getStatistics().size(); i++){
                                    if(getStatisticModel.getMetadata().getStatistics().get(i).getSold() != 0){
                                        String productName = getStatisticModel.getMetadata().getStatistics().get(i).getProduct().substring(0, 20);
                                        int sold = getStatisticModel.getMetadata().getStatistics().get(i).getSold();
                                        listData.add(new PieEntry(sold, productName));
                                    }
                                }

                                PieDataSet pieDataSet = new PieDataSet(listData, "Statistic");
                                PieData data = new PieData();
                                data.setDataSet(pieDataSet);
                                data.setValueTextSize(12f);
                                data.setValueFormatter (new PercentFormatter(pieChart));
                                pieDataSet.setColors (ColorTemplate.MATERIAL_COLORS);
                                pieChart.setData(data);
                                pieChart.animateXY(2000, 2000);
                                pieChart.setUsePercentValues(true); pieChart.getDescription().setEnabled(false);
                                pieChart.invalidate();
                            }else{
                                Log.d("Fail", "get statistic Fail");
                            }
                        },
                        throwable -> {
                            Log.d("SuccessFail", "" + throwable.getMessage());
                            Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    private void initView() {
        Paper.init(this);
        userId = Paper.book().read("userId");
        accessToken = Paper.book().read("accessToken");
        toolbar = findViewById(R.id.toolbar_statisticScreen);
        pieChart = findViewById(R.id.pieChart);
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
    }


    private void actionToolBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}