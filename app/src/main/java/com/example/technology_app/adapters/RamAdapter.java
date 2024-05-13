package com.example.technology_app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.technology_app.R;
import com.example.technology_app.activities.products.DetailProductActivity;
import com.example.technology_app.interfaces.IItemClickListener;
import com.example.technology_app.models.Products.Laptop.ProductDetail;

import java.text.DecimalFormat;
import java.util.List;

public class RamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<ProductDetail> array;

    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    public RamAdapter(Context context, List<ProductDetail> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA){
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            return new MyViewHolder(item);
        }else{
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(item);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  MyViewHolder){
            MyViewHolder myViewHolder =  (MyViewHolder) holder;
            ProductDetail product = array.get(position);
            if(myViewHolder.txtTen != null && product.getName() != null) {
                myViewHolder.txtTen.setText(product.getName());
            }
            if(myViewHolder.txtMota != null && product.getDescription() != null) {
                myViewHolder.txtMota.setText(product.getInformation());
            }
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            //myViewHolder.txtGia.setText("Price: "+ decimalFormat.format(product.getPrice()) +"đ");
            myViewHolder.txtGia.setText("Price: "+ product.getPrice());
            Glide.with(context).load(product.getImages().get(0)).into(myViewHolder.imgHinhAnh);
            Log.d("image", product.getImages().get(0));
            myViewHolder.setItemClickListener(new IItemClickListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClicked) {
                    if(!isLongClicked){
                        Intent intent = new Intent(context, DetailProductActivity.class);
                        intent.putExtra("detailProduct", product);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });

        }else{
            if(position == array.size() - 1 && array.get(position) == null){
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setVisibility(View.GONE);
            }
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return array != null ? array.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_DATA;
    }

    public static class LoadingViewHolder extends  RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtGia, txtTen, txtMota;
        ImageView imgHinhAnh;

        private IItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtGia = itemView.findViewById(R.id.itemproduct_price);
            txtTen = itemView.findViewById(R.id.itemproduct_name);
            txtMota = itemView.findViewById(R.id.itemproduct_des);
            imgHinhAnh = itemView.findViewById(R.id.itemproduct_image);
            itemView.setOnClickListener(this);
        }

        public IItemClickListener getItemClickListener() {
            return itemClickListener;
        }

        public void setItemClickListener(IItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}
