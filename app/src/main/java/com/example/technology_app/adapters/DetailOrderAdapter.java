package com.example.technology_app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.technology_app.R;
import com.example.technology_app.models.GetOrder.Order;
import com.example.technology_app.models.GetOrder.products.Products;

import java.util.List;

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.MyViewHolder> {
    List<Products> productsList;
    Order order;
    Context context;
    public DetailOrderAdapter(List<Products> productsList, Context context, Order order) {
        this.productsList = productsList;
        this.context = context;
        this.order = order;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_order, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Products product = productsList.get(position);
        holder.txtProductQuantity.setText("Quantity: "+String.valueOf(product.getQuantity()) + "item.");
        holder.txtProductName.setText(product.getProduct().getName());
        holder.txtProductStatusPayment.setText("Payment status: " + order.getPaymentStatus());
        //holder.txtDeliveredDate.setText("Delivered Date: " + order.getDeliveredDate());
        Glide.with(context).load(order.getUser().getAvatar()).into(holder.img);
        holder.txtUserName.setText(order.getUser().getName());
        holder.txtAddress.setText(order.getShipping_address());
        holder.txtPrice.setText(product.getProduct().getSalePrice());
        holder.txtStatusOrder.setText("Order status: " + order.getStatus());
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView txtProductName, txtProductQuantity, txtStatusOrder, txtProductStatusPayment, txtUserName, txtAddress, txtPrice;

        public ImageView getImg() {
            return img;
        }

        public void setImg(ImageView img) {
            this.img = img;
        }

        public TextView getTxtProductName() {
            return txtProductName;
        }

        public void setTxtProductName(TextView txtProductName) {
            this.txtProductName = txtProductName;
        }

        public TextView getTxtProductQuantity() {
            return txtProductQuantity;
        }

        public void setTxtProductQuantity(TextView txtProductQuantity) {
            this.txtProductQuantity = txtProductQuantity;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgDetailItem);
            txtProductName = itemView.findViewById(R.id.idItemOrderName);
            txtProductQuantity = itemView.findViewById(R.id.idItemOrderQuantity);
            txtProductStatusPayment = itemView.findViewById(R.id.idItemOrderStatusPayment);
            txtUserName = itemView.findViewById(R.id.userName);
            txtAddress = itemView.findViewById(R.id.address);
            txtPrice = itemView.findViewById(R.id.itemOrderPrice);
            txtStatusOrder = itemView.findViewById(R.id.statusOrder);
        }
    }
}
